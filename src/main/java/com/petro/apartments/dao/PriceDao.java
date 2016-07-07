package com.petro.apartments.dao;


import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Price;

import java.util.List;

public interface PriceDao {
    void add (Price price);
    void delete (Price price);
    void edit (Price price);
    List<Price> list (Apartment apartment);
    List<Price> list ();
    Price findOne (long id);
//    List<Price> listActual (Apartment apartment);
}
