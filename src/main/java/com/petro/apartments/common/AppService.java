package com.petro.apartments.common;


import com.petro.apartments.dao.*;
import com.petro.apartments.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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
    @Autowired
    private ImageDao imageDao;


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
    public List<Apartment> getManyApartments(long [] ids){
        return apartmentDao.getMany(ids);
    }
    @Transactional
    public Apartment findOneApartment (long id){
        return apartmentDao.findOne(id) ;
    }
    @Transactional
    public Apartment getOneApartment (long id){
        return apartmentDao.getOne(id) ;
    }

    @Transactional
    public void addDistrict (District district){
        districtDao.add(district);
    }
    @Transactional
    public void editDistrict (District district) {
        districtDao.edit(district);
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
    public District getDistrict (long id){
        return districtDao.getOne(id);
    }
    @Transactional
    public List<District> listDistricts (){
        List<District> districts = districtDao.list();
        districts.sort(Comparator.comparing(District::getName));
        return districts;
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
    public void editPrice (Price price) {
        priceDao.edit(price);
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

    @Transactional
    public void addImage(Image image){
        imageDao.add(image);
    }

    @Transactional
    public void deleteImage(Image image) {
        imageDao.delete(image);
    }

    @Transactional
    public List<Image> listImages(Apartment apartment) {
        return imageDao.list(apartment);
    }

    @Transactional
    public Image findOneImage(long id) {
        return imageDao.findOne(id);
    }


//    @Transactional
//    public List<Price> listActualPrices (Apartment apartment) {
//        return priceDao.listActual(apartment);
//    }
}
