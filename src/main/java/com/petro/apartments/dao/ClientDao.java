package com.petro.apartments.dao;


import com.petro.apartments.entity.Client;

import java.util.List;

public interface ClientDao {
    void add (Client client);
    void delete (Client client);
    List<Client> list ();
    List<Client> list (String pattern);
    Client findOne(long id);

}
