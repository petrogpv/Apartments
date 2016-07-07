package com.petro.apartments.dao;


import com.petro.apartments.entity.District;

import java.util.List;

public interface DistrictDao {
    void add (District district);
    void edit (District district);
    void delete (District district);
    District findOne (long id);
    District getOne (long id);
    List<District> list();

}
