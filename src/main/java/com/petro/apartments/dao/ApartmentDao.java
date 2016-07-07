package com.petro.apartments.dao;


import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.District;

import java.util.List;

public interface ApartmentDao {
    void add (Apartment apartment);
    void delete (Apartment apartment);
    List<Apartment> list (District district);
    List<Apartment> list ();
    List<Apartment> getMany(long [] ids);
    List<Apartment> findMany(long [] ids);
    Apartment getOne(long id);
    Apartment findOne(long id);
}
