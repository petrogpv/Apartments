package com.petro.apartments.common;


import com.petro.apartments.dao.*;
import com.petro.apartments.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AppService {

    @Autowired
    private ApartmentDao apartmentDao;
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private DayDao dayDao;
    @Autowired
    private DistrictDao districtDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PriceDao priceDao;


    @Transactional
    public void addApartment (Apartment apartment){
        apartmentDao.add(apartment);
    }
    @Transactional
    public void deleteApartment (Apartment apartment){
        apartmentDao.delete(apartment);
    }
    @Transactional
    public List<Apartment> listApartments (District district){
        return apartmentDao.list(district);
    }
    @Transactional
    public List<Apartment> listApartments (){
        return apartmentDao.list();
    }
    @Transactional
    public List<Apartment> findManyApartments(long [] ids){
        return apartmentDao.findMany(ids);
    }
    @Transactional
    public Apartment findOneApartment (long id){
        return apartmentDao.findOne(id);
    }


    @Transactional
    public void addDistrict (District district){
        districtDao.add(district);
    }
    @Transactional
    public void deleteDistrict (District district){
        districtDao.delete(district);
    }
    @Transactional
    public District findDistrict (long id){
        return districtDao.findOne(id);
    }
    @Transactional
    public List<District> listDistricts (){
        return districtDao.list();
    }


    @Transactional
    public void addDays (List<Day> days){
        dayDao.add(days);
    }
    @Transactional
    public void addDay (Day day){
        dayDao.add(day);
    }
    @Transactional
    public List<Day> listDays (Date monthDate){
        return dayDao.list(monthDate);
    }
    @Transactional
    public List<Day> listDays (Date dateFrom, Date dateTo){
        return dayDao.list(dateFrom,dateTo);
    }
    @Transactional
    public Day findOneDay (String id){
        return  dayDao.findOne(id);
    }

    @Transactional
    public void addPrice (Price price) {
        priceDao.add(price);
    }

    @Transactional
    public void changePrice (Price price) {
        priceDao.change(price);
    }

    @Transactional
    public List<Price> listPrices(Apartment apartment) {
        return priceDao.list(apartment);
    }

    @Transactional
    public List<Price> listPrices() {
        return priceDao.list();
    }

    @Transactional
    public Price findOnePrice (long id) {
        return priceDao.findOne(id);
    }

    @Transactional
    public void delete(Price price) {
        priceDao.delete(price);
    }


//    @Transactional
//    public List<Price> listActualPrices (Apartment apartment) {
//        return priceDao.listActual(apartment);
//    }
}
