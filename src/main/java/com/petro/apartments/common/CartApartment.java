package com.petro.apartments.common;

import com.petro.apartments.entity.Apartment;

import java.util.*;


public class CartApartment extends Apartment {


    public CartApartment(Apartment apartment, Map<Date, Set<DayPrice>> monthDaysMap) {
        this(apartment);
        this.monthDaysMap = monthDaysMap;
        setTotalPrice();
    }
    public CartApartment(Apartment apartment) {
        this.setId(apartment.getId());
        this.setStreet(apartment.getStreet());
        this.setBuilding(apartment.getBuilding());
        this.setAptNumber(getAptNumber());
        this.setDistrict(apartment.getDistrict());
    }

    private Map<Date, Set<DayPrice>> monthDaysMap;
    private double totalPrice;

    public Map<Date, Set<DayPrice>> getMonthDaysMap() {
        return monthDaysMap;
    }

    public void setMonthDaysMap(Map<Date, Set<DayPrice>> monthDaysMap) {
        this.monthDaysMap = monthDaysMap;
        setTotalPrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private void setTotalPrice() {
        totalPrice = 0;
        for (Map.Entry<Date, Set<DayPrice>> entry: monthDaysMap.entrySet()) {
            for (DayPrice dp: entry.getValue()) {
                totalPrice += dp.getPrice().getPrice();
            }
        }

    }

    @Override
    public int hashCode() {
        return this.getId().intValue();
    }
}
