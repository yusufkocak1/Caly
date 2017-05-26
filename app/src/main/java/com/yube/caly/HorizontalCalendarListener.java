package com.yube.caly;

/**
 * Created by yusuf on 11.05.2017.
 */

import java.util.Date;

public abstract class HorizontalCalendarListener {

    public abstract void onDateSelected(Date date, int position) throws InterruptedException;

    public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy){}

    public boolean onDateLongClicked(Date date, int position){
        return false;
    }

}