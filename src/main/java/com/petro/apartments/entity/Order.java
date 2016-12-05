package com.petro.apartments.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;


@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue
    @Column (name = "order_id")
    private Long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy HH:mm:ss")
//    @NotNull
    private Date order_date;
//    @NotNull
    @OneToMany(mappedBy="order", orphanRemoval = true, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
//    @Cascade({org.hibernate.annotations.CascadeType.PERSIST,
//            org.hibernate.annotations.CascadeType.MERGE,
//            org.hibernate.annotations.CascadeType.DELETE,
//            org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<Booking> bookings = new HashSet<>();

//    @ManyToMany(mappedBy="orders", fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinTable(name="order_apartment",
            joinColumns={@JoinColumn(name="order_id")},
            inverseJoinColumns={@JoinColumn(name="apartment_id")})
    private Set<Apartment> apartments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="client_id")
//    @NotNull
    private Client client;
//    @NotNull
    private Double price;
//    @NotNull
    private Integer discount;
    @Column(name = "final_price")
//    @NotNull
    private Double finalPrice;
//    @NotNull
    private String registrator;
    public Order(){

    }

    public Order(Client client, double price, String registrator) {
       this(client,registrator);
        this.price = price;
        if (discount != null) {
            this.finalPrice = price - price*discount/100;
        } else {
            this.finalPrice = price;
        }
    }
    public Order(Client client,String registrator) {
        this.order_date = Calendar.getInstance().getTime();
        this.client = client;
        this.discount = client.getDiscount();
        this.registrator = registrator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getOrder_date() {

        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

//    public void setBookings(Set<Booking> bookings) {
//        this.bookings = bookings;
//    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
        if (discount != null) {
            this.finalPrice = price - price*discount/100;
        } else {
            this.finalPrice = price;
        }
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getRegistrator() {
        return registrator;
    }

    public void setRegistrator(String registrator) {
        this.registrator = registrator;
    }

    public Set<Apartment> getApartments() {
        return apartments;
    }

//    public void setApartments(Set<Apartment> apartments) {
//        this.apartments = apartments;
//    }

    public void setApartment(Apartment apartment) {
        apartments.add(apartment);
        Set<Order> ordersSet= apartment.getOrders();
        ordersSet.add(this);
        apartment.setOrders(ordersSet);
    }
    public void removeApartment(Apartment apartment) {
        apartments.remove(apartment);
        apartment.getOrders().remove(this);
    }
    public void removeApartments() {
//        for (Apartment a: new HashSet<>(apartments) ){
//            removeApartment(a);
//        }
        Iterator<Apartment> i = apartments.iterator();
        while (i.hasNext()) {
            Apartment a = i.next();
            a.getOrders().remove(this);
            i.remove();
        }
    }
    public  void setBooking(Booking booking){
        bookings.add(booking);
        booking.setOrder(this);
    }
    public void removeBooking (Booking booking, Iterator<Booking> i){
        booking.setOrder(null);
        i.remove();
    }
    public void removeBookings() {
//        for (Booking b: new HashSet<>(bookings)) {
//            removeBooking(b);
//        }
        Iterator<Booking> i = bookings.iterator();
        while (i.hasNext()) {
            Booking b = i.next();
            b.setOrder(null);
            i.remove();
        }
    }
}
