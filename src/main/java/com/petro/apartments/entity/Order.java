package com.petro.apartments.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy HH:mm:ss")
    @NotNull
    private Date order_date;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL)
    @NotNull
    private List<Booking> bookings = new ArrayList<Booking>();

    @ManyToOne
    @JoinColumn(name="client_id")
    @NotNull
    private Client client;
    @NotNull
    private String price;
    @NotNull
    private String discount;
    @Column(name = "final_price")
    @NotNull
    private  String finalPrice;
    @Column(name = "payment_type")
    @NotNull
    private String registartor;

    public Order(Date order_date, List<Booking> bookings,
                 Client client, String price, String discount,
                 String finalPrice, String paymentType, String registartor) {
        this.order_date = order_date;
        this.bookings = bookings;
        this.client = client;
        this.price = price;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.registartor = registartor;
    }

    public Date getOrder_date() {

        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getRegistartor() {
        return registartor;
    }

    public void setRegistartor(String registartor) {
        this.registartor = registartor;
    }
}
