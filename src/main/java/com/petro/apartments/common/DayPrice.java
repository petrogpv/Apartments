package com.petro.apartments.common;

import com.petro.apartments.entity.Price;

import java.util.Date;


public class DayPrice implements Comparable <DayPrice>{
    private Date day;
    private Price price;

    public DayPrice(Date day, Price price) {
        this.day = day;
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    @Override
    public int compareTo(DayPrice dayPrice) {
        return this.getDay().compareTo(dayPrice.getDay());
    }
}
