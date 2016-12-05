package com.petro.apartments.common;

import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Booking;
import com.petro.apartments.entity.Day;
import com.petro.apartments.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Utility {

    @Autowired
    AppService appService;
    private long ONE_DAY = 86400000;

    public Date getStartOfToday() {
        return getStartOfDay(Calendar.getInstance().getTime());
    }
    public Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Apartment getApartmentWithActualPrices (Apartment apartment){
        Set<Price> prices = apartment.getPrices();
        if(prices.size()!=0) {
            Iterator<Price> iter = prices.iterator();
            long now = Calendar.getInstance().getTime().getTime();
            while (iter.hasNext()) {
                if(iter.next().getRevelance().equals("Archive")) {
                    iter.remove();
                    continue;
                }
            }
            apartment.setPrices(prices);
        }
        return apartment;
    }
    public List<Apartment> getListApartmentsWithActualPrices (List<Apartment> apartments){
        if(apartments.size()!=0) {
            for (int i = 0; i < apartments.size(); i++) {
                apartments.set(i, getApartmentWithActualPrices(apartments.get(i)));
            }
        }
        return apartments;
    }
public  Map<String,List<Price>> separatePrices (List<Price> prices){
    Map<String,List<Price>> map = new HashMap<>();

    List<Price> pricesActualType1 = new ArrayList<>();
    List<Price> pricesActualType2 = new ArrayList<>();
    List<Price> pricesActualType3 = new ArrayList<>();

    List<Price> pricesOldType1 = new ArrayList<>();
    List<Price> pricesOldType2 = new ArrayList<>();
    List<Price> pricesOldType3 = new ArrayList<>();


    for (Price p :prices) {
        switch (p.getType()){
            case 1:
                if(p.getRevelance()=="actual")
                    pricesActualType1.add(p);
                else
                    pricesOldType1.add(p);
                break;
            case 2:
                if(p.getRevelance()=="actual")
                    pricesActualType2.add(p);
                else
                    pricesOldType2.add(p);
                break;
            case 3:
                if(p.getRevelance()=="actual")
                    pricesActualType3.add(p);
                else
                    pricesOldType3.add(p);
        }
    }
    Collections.sort(pricesActualType1,Collections.reverseOrder());
    Collections.sort(pricesActualType2,Collections.reverseOrder());
    Collections.sort(pricesActualType3,Collections.reverseOrder());

    Collections.sort(pricesOldType1,Collections.reverseOrder());
    Collections.sort(pricesOldType2,Collections.reverseOrder());
    Collections.sort(pricesOldType3,Collections.reverseOrder());


    map.put("pricesActualType1",pricesActualType1);
    map.put("pricesActualType2",pricesActualType2);
    map.put("pricesActualType3",pricesActualType3);
    map.put("pricesOldType1",pricesOldType1);
    map.put("pricesOldType2",pricesOldType2);
    map.put("pricesOldType3",pricesOldType3);
    return map;

}
    public  List<Price> getPricesByRevelance(Set<Price> prices, String revelance){
        List<Price> result= new ArrayList<>();

        result.addAll(getPricesByRevelanceAndType(prices,revelance,1));
        result.addAll(getPricesByRevelanceAndType(prices,revelance,2));
        result.addAll(getPricesByRevelanceAndType(prices,revelance,3));

        return result;

    }

    public List<Price> getPricesByRevelanceAndType(Set<Price> prices, String revelance, int type){
        List<Price> pricesActual = new ArrayList<>();
        for (Price p :prices) {
            if(p.getType()==type && p.getRevelance().equals(revelance))
                pricesActual.add(p);
        }
        Collections.sort(pricesActual,Collections.reverseOrder());
        return pricesActual;
    }


    public Price [] daysWhithPrices (Apartment apartment, Date monthDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthDate);

        Price daysPrices [] = new Price[cal.getActualMaximum(Calendar.DAY_OF_MONTH)+1];


        List <Price> prices = appService.listPrices(apartment);
        List <Day> days = appService.listMonthDays(cal.getTime());
        List <Booking> bookings = appService.listMonthBookings(apartment, cal.getTime());



        Day day;
        int priceType;
        Date dayDate;

        for (int i = 1; i<daysPrices.length; i++){
//            if(daysPrices[i]== null)
//                continue;

            day = days.get(i-1);
            priceType = day.getPriceType();
            dayDate = day.getId();

            for (int j=priceType; j>=1; j--){
                daysPrices[i] = getPriceByType(prices,j,dayDate);
                if(daysPrices[i]!=null)                             // ????
                    break;
            }


        }

        for (Booking booking:bookings) {
            cal.setTime(booking.getDay().getId());
            daysPrices [cal.get(Calendar.DAY_OF_MONTH)] = null;
        }

        return daysPrices;
    }
    private Price getPriceByType (List<Price> prices, int priceType, Date dayDate){
        Price price = null;
        for (Price p:prices) {
            if(p.getType()==priceType && p.getRevelance().equals("actual")){
                Date dateFrom = p.getDate_from();
                Date dateTo=p.getDate_to();
                if(dateTo==null){
                    if(dateFrom.getTime()<=dayDate.getTime()||(dateFrom.getTime()-dayDate.getTime()<ONE_DAY)){
                        price = p;
                    }
                }
                else {
                    if((dateFrom.getTime()-dayDate.getTime()<ONE_DAY)&& dayDate.getTime()<= dateTo.getTime()){
                        price = p;
                    }
                }
            }
        }
        return price;
    }

}
