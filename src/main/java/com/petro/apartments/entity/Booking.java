package com.petro.apartments.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    @NotNull
    private Order order;

    @ManyToOne
    @JoinColumn(name="apt_id")
    @NotNull
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name="price_id")
    @NotNull
    private Price price;

    @ManyToOne
    @JoinColumn(name="day_id")
    @NotNull
    private Day day;


    public Booking() {
    }

    public Booking(Order order, Apartment apartment, Price price, Day day) {

        this.order = order;
        this.apartment = apartment;
        this.price = price;

        this.id = Integer.parseInt("" + apartment.getId() + day.getId());

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {

        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
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
}
