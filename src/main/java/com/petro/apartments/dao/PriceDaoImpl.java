package com.petro.apartments.dao;

import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Price;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Repository
public class PriceDaoImpl implements PriceDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Price price) {

        entityManager.merge(price);
    }

    @Override
    public void delete(Price price) {
        entityManager.remove(price);
    }

    @Override
    public void edit(Price price) {
        entityManager.merge(price);
    }

    @Override
    public List<Price> list(Apartment apartment) {
        Query query = entityManager.createQuery("SELECT p FROM Price p WHERE p.apartment = :apartment", Price.class);
        query.setParameter("apartment", apartment);
        return (List<Price>)query.getResultList();
    }
    @Override
    public List<Price> list() {
        Query query = entityManager.createQuery("SELECT p FROM Price p", Price.class);
        return (List<Price>)query.getResultList();
    }

    @Override
    public Price findOne(long id) {
        return entityManager.find(Price.class,id);
    }
//    @Override
//    public List<Price> listActual(Apartment apartment) {
//        List<Price> listMonth = listMonth(apartment);
//        if(listMonth.size()==0){
//            return listMonth;
//        }
//        Iterator<Price> iter = listMonth.iterator();
//        while(iter.hasNext()){
//            Price p = iter.next();
//            if(p.getDate_to().getTime() < Calendar.getInstance().getTime().getTime()){
//                iter.remove();
//            }
//        }
//        return listMonth;
//    }
}
