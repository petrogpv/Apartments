package com.petro.apartments.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Clients")
public class Client {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy="client", cascade=CascadeType.ALL)
    private List<Order> orders = new ArrayList<Order>();

    @Column(name = "first_name")
    @NotNull
    private String firstName;
    @Column(name = "last_name")
    private String lastNamae;
    private String address;
    @Column(name = "e_mail")
    private String eMail;
    @Column(name = "phone_number")
    private String phoneNumber;
    @NotNull
    private int discount;

    public Client() {
    }

    public Client(String firstName, String lastNamae, String address, String eMail, String phoneNumber, int discount) {
        this.firstName = firstName;
        this.lastNamae = lastNamae;
        this.address = address;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.discount = discount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNamae() {
        return lastNamae;
    }

    public void setLastNamae(String lastNamae) {
        this.lastNamae = lastNamae;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
