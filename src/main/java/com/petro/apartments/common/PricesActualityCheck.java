package com.petro.apartments.common;

import com.petro.apartments.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class PricesActualityCheck extends TimerTask{
    @Autowired
    private AppService appService;
    @Autowired
    private Utility utility;

    Boolean timerStart = false;

    @PostConstruct
    void start(){
        if(timerStart == false){
            run();
            Date tomorrowStart  = new Date (utility.getStartOfToday().getTime()+ 86400001);
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(this,tomorrowStart,86400000);
            timerStart = true;
        }
    }
    @Override
    public void run() {
        Date today = utility.getStartOfToday();
        List<Price> prices = appService.listPrices();

        if (prices.size() != 0) {
            for (Price price : prices) {
                if (price.getDate_to() != null && price.getDate_to() == today) {
                    price.setArchive(today, "Auto");
                    appService.editPrice(price);
                }
            }
        }
        System.out.println("Thread WORKING");
    }
}
