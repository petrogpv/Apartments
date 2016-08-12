package com.petro.apartments.dao;

import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Booking;
import com.petro.apartments.entity.Day;
import com.petro.apartments.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class BookingDaoImpl implements BookingDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Booking> list(Order order) {
        Query query = entityManager.createQuery("SELECT b FROM Booking b WHERE b.order = :order", Booking.class);
        query.setParameter("order", order);
        return (List<Booking>)query.getResultList();
    }

    @Override
    public List<Booking> list(Apartment apartment) {
        Query query = entityManager.createQuery("SELECT b FROM Booking b WHERE b.apartment = :apartment", Booking.class);
        query.setParameter("apartment", apartment);
        return (List<Booking>)query.getResultList();
    }

    @Override
    public List<Booking> list(Day day) {
        Query query = entityManager.createQuery("SELECT b FROM Booking b WHERE b.day = :day", Booking.class);
        query.setParameter("day", day);
        return (List<Booking>)query.getResultList();
    }

    @Override
    public List<Booking> list(Apartment apartment, Date dateFrom, Date dateTo) {
        Query query = entityManager.createQuery("SELECT DISTINCT b FROM Booking b INNER JOIN b.day d WHERE b.apartment = :apartment " +
                "AND d.date >= :dateFrom AND d.date = :dateTo ORDER BY d.date ASC", Booking.class);
        query.setParameter("apartment",apartment);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        return (List<Booking>)query.getResultList();
    }

    @Override
    public List<Booking> listMonth(Apartment apartment, Date monthDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthDate);
        cal.set(Calendar.DAY_OF_MONTH,1);
        Date dateFrom = cal.getTime();
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH,maxDay);
        Date dateTo = cal.getTime();
        return list(apartment,dateFrom,dateTo);
    }

    @Override
    public List<Booking> findMany(long[] ids) {
        List<Booking> list = new ArrayList<>();
        for (long id:ids) {
            list.add(findOne(id));
        }
        return list;
    }

    @Override
    public Booking findOne(long id) {
        return entityManager.getReference(Booking.class,id);
    }
}
