package com.petro.apartments.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="Districts")
public class District {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy="district", cascade=CascadeType.ALL)
    private List<Apartment> apartment = new ArrayList<Apartment>();

    private  String name;

    public District() {
    }

    public District(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
}
