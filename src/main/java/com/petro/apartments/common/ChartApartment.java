package com.petro.apartments.common;

import com.petro.apartments.entity.Apartment;

import java.util.*;


public class ChartApartment extends Apartment {


    public ChartApartment(Apartment apartment, List<Date> bookingDates) {
        this.setId(apartment.getId());
        this.setStreet(apartment.getStreet());
        this.setBuilding(apartment.getBuilding());
        this.setAptNumber(getAptNumber());
        this.setDistrict(apartment.getDistrict());
        this.bookingDates = bookingDates;
    }

    private List<Date> bookingDates;

    public List<Date> getBookingDates() {
        return bookingDates;
    }

    public void setBookingDates(List<Date> bookingDates) {
        this.bookingDates = bookingDates;
    }




}
