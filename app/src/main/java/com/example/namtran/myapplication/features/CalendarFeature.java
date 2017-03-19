package com.example.namtran.myapplication.features;

import java.util.Calendar;

/**
 * Created by Tran on 25-Feb-17.
 */

public class CalendarFeature extends Feature {

    @Override
    public String doAction() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return "Hôm nay là ngày " + day + "/" + month + "/" + year;
    }


}
