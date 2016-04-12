package com.example.administrator.myapplication.Search;

/**
 * Created by Administrator on 2016/4/7.
 */
public class SearchBean {

    private String numHttp;
    private String cityHttp;
    public SearchBean(String numHttp, String cityHttp){
        this.numHttp =numHttp;
        this.cityHttp =cityHttp;
    }

    public String getNumHttp() {
        return numHttp;
    }

    public void setNumHttp(String numHttp) {
        this.numHttp = numHttp;
    }

    public String getCityHttp() {
        return cityHttp;
    }

    public void setCityHttp(String cityHttp) {
        this.cityHttp = cityHttp;
    }
}
