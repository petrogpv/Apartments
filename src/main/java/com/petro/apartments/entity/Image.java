package com.petro.apartments.entity;

//import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "Images")
public class Image {


    @Id
    @Column(name = "id")
    private Long id;
//    @NotNull
    private String filename;

    @ManyToOne( fetch = FetchType.EAGER)
//    cascade={CascadeType.MERGE, CascadeType.REFRESH},
    @JoinColumn(name="apartrment_id")
    Apartment apartment;


    public Image(long id, String filename, Apartment apartment) {
        this.id = id;
        this.filename = filename;
        setApartment(apartment);
    }
    public Image() {}

    public Long getId() {
        return id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        setApartment(apartment, true);
    }

    void setApartment(Apartment apartment, boolean add) {
        this.apartment = apartment;
        if (apartment != null && add) {
            apartment.addImage(this, false);
        }
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }




    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof Image))
            return false;

        final Image a = (Image)object;

        if (id != null && a.getId() != null) {
            return id.equals(a.getId());
        }
        return false;
    }
}
