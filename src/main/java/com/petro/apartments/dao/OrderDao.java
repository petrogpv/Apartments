package com.petro.apartments.dao;


import com.petro.apartments.entity.Client;
import com.petro.apartments.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    void add (Order order);
    void delete (Order order);
    Order findOne (Long id);
    Order getOne (Long id);
    List<Order> list ();
    List<Order> list (Client client);
    List<Order> list (Date monthDate);
    List<Order> list (Date dateFrom, Date dateTo);
}
