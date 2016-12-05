package com.petro.apartments.common;

import com.petro.apartments.entity.Day;
import com.petro.apartments.entity.Price;


public class DayPrice implements Comparable <DayPrice>{
    private Day day;
    private Price price;

    public DayPrice(Day day, Price price) {
        this.day = day;
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public int compareTo(DayPrice dayPrice) {
        return this.getDay().getId().compareTo(dayPrice.getDay().getId());
    }
}
