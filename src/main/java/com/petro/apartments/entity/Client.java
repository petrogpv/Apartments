package com.petro.apartments.entity;

//import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Clients")
public class Client {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy="client", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Order> orders = new ArrayList<Order>();

    @Column(name = "first_name")
//    @NotNull
    private String firstName;
    @Column(name = "last_name")
//    @NotNull
    private String lastName;
//    @NotNull
    private String address;
    @Column(name = "e_mail")
    private String eMail;
    @Column(name = "phone_number")
//    @NotNull
    private String phoneNumber;
    private Integer discount;

    public Client() {
    }

    public Client(String firstName, String lastName, String address, String eMail, String phoneNumber, int discount) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
