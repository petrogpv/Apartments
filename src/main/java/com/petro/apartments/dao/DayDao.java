package com.petro.apartments.dao;


import com.petro.apartments.entity.Day;

import java.util.Date;
import java.util.List;

public interface DayDao {
    void add (List<Day> days);
    void add (Day day);
    List<Day> list (Date monthDate);
    List<Day> list (Date dateFrom, Date dateTo);
    Day findOne (String id);
}
