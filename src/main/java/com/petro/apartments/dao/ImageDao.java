package com.petro.apartments.dao;

import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Image;

import java.util.List;


public interface ImageDao {
    void add (Image image);
    void delete (Image image);
    List<Image> list (Apartment apartment);
    Image findOne (long id);
    Image getOne (long id);

}
