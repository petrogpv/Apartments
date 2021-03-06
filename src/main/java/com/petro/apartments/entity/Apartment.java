package com.petro.apartments.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Apartments")
public class Apartment implements Comparable<Apartment>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "apartment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="district_id")
    private District district;

    @OneToMany(mappedBy="apartment", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "apartment", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    Set<Price> prices = new HashSet<>();

    @OneToMany(mappedBy = "apartment", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
        Set<Image> images = new HashSet<>();


    @ManyToMany(mappedBy="apartments", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Set<Order> orders = new HashSet<>();


    private String street;
    private String building;
    @Column(name = "apt_number")
    private int aptNumber;

    public Apartment() {}

    public Apartment(District district, String street, String building, int aptNumber) {
        this.district = district;
        this.street = street;
        this.building = building;
        this.aptNumber = aptNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        addImage(image, true);
    }


    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    void addImage(Image image, boolean set) {
        if (image != null) {
                getImages().add(image);
            if (set) {
                image.setApartment(this, false);
            }
        }
    }

    public void removeImage(Image image) {
        getImages().remove(image);
        image.setApartment(null);
    }

    @Override
    public int hashCode() {
        return this.getId().intValue();
    }
    @Override
    public int compareTo(Apartment apartment) {

        return this.getId().compareTo(apartment.getId());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof Apartment))
            return false;

        final Apartment a = (Apartment) object;

        if (id != null && a.getId() != null) {
            return id.equals(a.getId());
        }
        return false;
    }
}
