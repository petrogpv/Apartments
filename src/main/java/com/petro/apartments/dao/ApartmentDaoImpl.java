package com.petro.apartments.dao;

import com.petro.apartments.common.Utility;
import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.District;
import com.petro.apartments.entity.Price;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApartmentDaoImpl implements ApartmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Apartment apartment) {
        entityManager.merge(apartment);
    }

    @Override
    public void delete(Apartment apartment) {
        entityManager.remove(apartment);
    }

    @Override
    public List<Apartment> list(District district) {
        Query query = entityManager.createQuery("SELECT a FROM Apartment a WHERE a.district = :district", Apartment.class);
        query.setParameter("district", district);
        return Utility.getListApartmentsWithActualPrices((List<Apartment>)query.getResultList());
    }

    @Override
    public List<Apartment> list() {
        Query query = entityManager.createQuery("SELECT a FROM Apartment a", Apartment.class);
        return Utility.getListApartmentsWithActualPrices((List<Apartment>)query.getResultList());
    }

    @Override
    public List<Apartment> findMany(long[] ids) {
        List<Apartment> list = new ArrayList<>();
        for (long id:ids) {
            list.add(findOne(id));
        }
        return list;
    }

    @Override
    public Apartment findOne(long id) {
//        Query query = entityManager.createQuery("SELECT a FROM Apartment a WHERE a.id = :id", Apartment.class);
//        query.setParameter("id", id);
//        (Apartment)query.getSingleResult();
        return Utility.getApartmentWithActualPrices(entityManager.find(Apartment.class,id));
    }
}
