package com.petro.apartments.dao;

import com.petro.apartments.common.Utility;
import com.petro.apartments.entity.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class DayDaoImpl implements DayDao {

    @Autowired
    Utility utility;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(List<Day> days) {
        for (Day day: days) {
            entityManager.merge(day);
        }
    }

    @Override
    public void add(Day day) {
        entityManager.merge(day);
    }

    @Override
    public List<Day> listMonth(Date monthDate) {
        Calendar cal = Calendar.getInstance();
        Date dateFrom;
        Date dateTo;

//        if (monthDate!=null){
            cal.setTime(monthDate);
            cal.set(Calendar.DAY_OF_MONTH,1);
            dateFrom = utility.getStartOfDay(cal.getTime());
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH,maxDay);
            dateTo = cal.getTime();
//        }
//        else {
//            cal.set(Calendar.DAY_OF_MONTH, 1);
//            monthDate = cal.getTime();
//            dateTo = new Date(Integer.MAX_VALUE);
//        }
        return list(dateFrom,dateTo);
    }

    @Override
    public List<Day> list(Date dateFrom, Date dateTo) {
        Query query = entityManager.createQuery("SELECT d FROM Day d WHERE d.id >= :dateFrom AND d.id <= :dateTo ORDER BY d.id ASC", Day.class);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        return (List<Day>)query.getResultList();
    }

    @Override
    public Day findOne(Date id) {
        return entityManager.find(Day.class,id);
    }
    @Override
    public Day getOne(Date id) {
        return entityManager.getReference(Day.class,id);
    }

}
