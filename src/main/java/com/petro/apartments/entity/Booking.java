package com.petro.apartments.entity;

//import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "Bookings")
public class Booking implements Comparable<Booking>{

    @Id
//    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="apt_id")
//    @NotNull
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name="price_id")
//    @NotNull
    private Price price;

    @ManyToOne
    @JoinColumn(name="day_id")
//    @NotNull
    private Day day;


    public Booking() {
    }

    public Booking(Apartment apartment, Price price, Day day) {

        this.apartment = apartment;
        this.price = price;
        this.id = Long.parseLong("" + apartment.getId() + day.getId().getTime());
        this.day = day;

    }

    public Long getId() {
        return id;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

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
    @Override
    public int hashCode() {
        return this.getId().intValue()/100000;
    }
    @Override
    public int compareTo(Booking booking) {
        Long thisId = this.getId();
        Long notThis = booking.getId();
        return this.getId().compareTo(booking.getId());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof Apartment))
            return false;

        final Booking b = (Booking) object;

        if (id != null && b.getId() != null) {
            return id.equals(b.getId());
        }
        return false;
    }

}
