package com.petro.apartments.dao;


import com.petro.apartments.entity.Booking;
import com.petro.apartments.entity.Client;
import com.petro.apartments.entity.District;
import com.petro.apartments.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void add(Order order) {
        entityManager.merge(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Order order) {
        entityManager.remove(order);

    }

    @Override
    public Order findOne (Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public Order getOne(Long id) {
        return entityManager.getReference(Order.class,id);
    }

    @Override
    public List<Order> list(Client client) {
        Query query = entityManager.createQuery("SELECT o FROM Order o WHERE o.client = :client", Order.class);
        query.setParameter("client", client);
        return (List<Order>)query.getResultList();
    }

    @Override
    public List<Order> list() {
        Query query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
        return (List<Order>)query.getResultList();
    }

    @Override
    public List<Order> list(Date monthDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthDate);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH,maxDay);
        Date dateTo = cal.getTime();
        return list(monthDate,dateTo);
    }

    @Override
    public List<Order> list(Date dateFrom, Date dateTo) {
        Query query = entityManager.createQuery("SELECT o FROM Order o WHERE  o.order_date >= :dateFrom AND o.order_date <= :dateTo ORDER BY o.order_date ASC", Order.class);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        return (List<Order>)query.getResultList();
    }
}
