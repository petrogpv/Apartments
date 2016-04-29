package com.petro.apartments.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Days")
public class Day {

    @Id
    private int id;

    @Column (name = "day_of_week")
    @NotNull
    private int dayOfWeek;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
    @NotNull
    private Date date;

    @Column (name = "price_type")
    @NotNull
    private int priceType;

    @OneToMany(mappedBy="day", cascade=CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<Booking>();


    public Day() {
    }

    public Day(Date date, int priceType) {
        this.date = date;
        this.priceType = priceType;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.id = Integer.parseInt(""+cal.get(Calendar.YEAR)+(cal.get(Calendar.MONTH)+1)+cal.get(Calendar.DAY_OF_MONTH));
        this.dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
