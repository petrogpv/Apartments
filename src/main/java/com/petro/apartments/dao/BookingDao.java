package com.petro.apartments.dao;


import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Booking;
import com.petro.apartments.entity.Day;
import com.petro.apartments.entity.Order;

import java.util.Date;
import java.util.List;

public interface BookingDao {
//    void add(Booking booking);
//    void delete(Booking booking);
    List<Booking> list (Order order);
    List<Booking> list (Apartment apartment);
    List<Booking> list (Day day);
    List<Booking> list (Apartment apartment, Date dateFrom, Date dateTo);
    List<Booking> listMonth(Apartment apartment, Date monthDate);
    List<Booking> findMany(long [] ids);
    Booking findOne(long id);


}
