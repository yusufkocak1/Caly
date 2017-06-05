package com.yube.caly.contact;

/**
 * Created by yusuf on 26.05.2017.
 */

public class dailyContact {

    private String daily, date, username;

    public dailyContact(String daily, String date, String username) {
        setDaily(daily);
        setDate(date);
        setUsername(username);


    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
