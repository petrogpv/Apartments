package com.petro.apartments.entity;


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
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name="price_id")
    private Price price;

    @ManyToOne
    @JoinColumn(name="day_id")
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
//        long hashLong = this.getId()/100000 ;
//        long hashLong = (Long.parseLong("" + apartment.getId() + day.getId().getTime()))/10000;
        int hashLong = day.getId().hashCode();
        return hashLong;
    }
    @Override
    public int compareTo(Booking booking) {
        return this.day.getId().compareTo(booking.getDay().getId());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof Apartment))
            return false;

        final Booking b = (Booking) object;

        if (day.getId() != null && b.getDay().getId() != null) {
            return day.getId().equals(b.getDay().getId());
        }
        return false;
    }

}
