package com.petro.apartments.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Prices")
public class Price implements Comparable<Price> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy="price", cascade={CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    @OneToMany(mappedBy="price", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<Booking>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    Apartment apartment;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy HH:mm:ss")
    private Date date_from;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy HH:mm:ss")
    private Date date_to;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy HH:mm:ss")
    private Date date_reg;

    private String registratorReg;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy HH:mm:ss")
    private Date date_exp;

    private int type;
    private double price;

    private String registratorExp;
    private String relevance;


    public Price() {
    }

    public Price(Apartment apartment, int type, double price, Date date_from, Date date_to,Date date_reg, String registratorReg) {
        this.apartment = apartment;
        this.type = type;
        this.price = price;
        this.date_from = date_from;
        this.date_to = date_to;
        this.date_reg = date_reg;
        this.registratorReg = registratorReg;
        this.relevance = "actual";
    }


    public Long getId() {
        return id;
    }

    public List<Booking> getBookings() {

        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment (Apartment apartment) {
        this.apartment = apartment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public String getRegistratorReg() {
        return registratorReg;
    }

    public void setRegistratorReg(String registatorReg){
        this.registratorReg = registatorReg;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public Date getDate_reg() {
        return date_reg;
    }

    public void setDate_reg(Date date_reg) {
        this.date_reg = date_reg;
    }

    public Date getDate_exp() {
        return date_exp;
    }

    public void setDate_exp(Date date_exp) {
        this.date_exp = date_exp;
    }

    public String getRegistratorExp() {
        return registratorExp;
    }

    public void setRegistratorExp(String registratorExp) {
        this.registratorExp = registratorExp;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public void setArchive(Date date_exp, String registatorExp) {
        setDate_exp(date_exp);
        setRegistratorExp(registatorExp);
        setRelevance("archive");
    }

    @Override
    public int compareTo(Price price) {
        return this.getDate_from().compareTo(price.getDate_from());
    }
}
