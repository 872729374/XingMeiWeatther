package com.example.administrator.myapplication.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class HeWeather {
    private Api aqi;
    private Basic basic;

    private List<Daily_forecast> daily_forecast;
    private List<Hourly_forecast> hourly_forecast;
    private Now now;
    private String status;
    private Suggestion suggestion;


    public Api getAqi() {
        return aqi;
    }

    public void setAqi(Api aqi) {
        this.aqi = aqi;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<Daily_forecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<Daily_forecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<Hourly_forecast> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<Hourly_forecast> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

}










