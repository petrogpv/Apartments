package com.petro.apartments.common;

import com.petro.apartments.entity.Apartment;
import com.petro.apartments.entity.Price;

import java.util.*;


public class Utility {
    static Date lastDayChange;

//    @Autowired
//    static AppService appService;

    public static Date getStartOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Apartment getApartmentWithActualPrices (Apartment apartment){
//        pricesExpirationControl();
        List<Price> prices = apartment.getPrices();
        if(prices.size()!=0) {
            Iterator<Price> iter = prices.iterator();
            long now = Calendar.getInstance().getTime().getTime();
            while (iter.hasNext()) {
                if(iter.next().getRevelance().equals("Expired")) {
                    iter.remove();
                    continue;
                }
            }
            apartment.setPrices(prices);
        }
        return apartment;
    }
    public static List<Apartment> getListApartmentsWithActualPrices (List<Apartment> apartments){
        if(apartments.size()!=0) {
            for (int i = 0; i < apartments.size(); i++) {
                apartments.set(i, getApartmentWithActualPrices(apartments.get(i)));
            }
        }
        return apartments;
    }
public static Map<String,List<Price>> separatePrices (List<Price> prices){
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
    public static List<Price> getActualPrices (List<Price> prices){
        List<Price> result= new ArrayList<>();

        result.addAll(getActualPricesByType(prices,1));
        result.addAll(getActualPricesByType(prices,2));
        result.addAll(getActualPricesByType(prices,3));

        return result;

    }

    public static List<Price> getActualPricesByType (List<Price> prices, int type){
        List<Price> pricesActual = new ArrayList<>();
        for (Price p :prices) {
            if(p.getType()==type && p.getRevelance().equals("actual"))
                pricesActual.add(p);
        }
        Collections.sort(pricesActual,Collections.reverseOrder());
        return pricesActual;
    }

//    public static void pricesExpirationControl() {
//        if (lastDayChange == null) {
//            lastDayChange = new Date();
//            lastDayChange.setTime(0);
//        }
//        Date today = Utility.getStartOfToday();
//        if (lastDayChange.getTime() == today.getTime()) {
//            return;
//        }
//
//        List<Price> prices = AppController.appService.listPrices();
//
//        if (prices.size() == 0) {
//            return;
//        }
//        for (Price price : prices) {
//            if (price.getDate_to() != null && price.getDate_to() == today) {
//                price.setArchive(today, "Auto");
//                AppController.appService.changePrice(price);
//            }
//        }
//        lastDayChange = today;
//
//    }

//    public static void pricesExpirationControl (){
//        if(lastDayChange==null){
//            lastDayChange = new Date();
//            lastDayChange.setTime(0);
//        }
//        Date today  = getStartOfToday();
//        if(lastDayChange.getTime() == today.getTime()){
//            return;
//        }
//        List<Price> prices = appService.listPrices();
//        if(prices.size()==0){
//            return;
//        }
//        for (Price price:prices) {
//            if(price.getDate_to()!=null&&price.getDate_to()==today){
//                price.setArchive(today,"Auto");
//            }
//        }
//        lastDayChange=today;
//    }
}
