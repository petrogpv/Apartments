package com.petro.apartments.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Days")
public class Day {

    @Id
    private Date id;

    @Column (name = "day_of_week")
    @NotNull
    private int dayOfWeek;


    @Column (name = "price_type")
    @NotNull
    private int priceType;

    @OneToMany(mappedBy="day", cascade=CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<Booking>();


    public Day() {
    }

    public Day(Date date, int priceType) {
        this.priceType = priceType;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.id = date;
        this.dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

    }

    public Date getId() {
        return id;
    }

    public void setId(Date id) {
        this.id = id;
    }


    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
