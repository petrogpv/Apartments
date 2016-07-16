package com.petro.apartments.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "Images")
public class Image {


    @Id
    private long id;
    @NotNull
    private String filename;

    @ManyToOne
//            (cascade=CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name="apartrment_id")
    Apartment apartment;


    public Image(long id, String filename, Apartment apartment) {
        this.id = id;
        this.filename = filename;
        this.apartment = apartment;
    }
    public Image() {}

    public long getId() {
        return id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
