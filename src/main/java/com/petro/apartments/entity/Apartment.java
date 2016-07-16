package com.petro.apartments.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Apartments")
public class Apartment {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="district_id")
    @NotNull
    private District district;

    @OneToMany(mappedBy="apartment", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<Booking>();

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Price> prices = new ArrayList<>();

    @OneToMany(mappedBy = "apartment", cascade = {CascadeType.DETACH, CascadeType.REMOVE,}, fetch = FetchType.EAGER)
//    , fetch = FetchType.EAGER orphanRemoval=true,
    List<Image> images = new ArrayList<>();

    @NotNull
    private String street;
    @NotNull
    private String building;
    @Column(name = "apt_number")
    private int aptNumber;
    private double latitude;
    private double longtitude;

    public Apartment() {}

    public Apartment(District district, String street, String building, int aptNumber, double latitude, double longtitude) {
        this.district = district;
        this.street = street;
        this.building = building;
        this.aptNumber = aptNumber;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(int aptNumber) {
        this.aptNumber = aptNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
