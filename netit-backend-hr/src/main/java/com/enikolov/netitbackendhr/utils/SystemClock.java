package com.enikolov.netitbackendhr.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SystemClock {

    public String getSystemDate(){

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        return formatter.format(date);
    }
    public String getSystemTime(){

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date time = new Date();

        return formatter.format(time);
    }
}
