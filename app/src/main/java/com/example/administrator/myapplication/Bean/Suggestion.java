package com.example.administrator.myapplication.Bean;

/**
 * Created by Administrator on 2016/3/16.
 */
public class Suggestion {

    private BrfTxt comf;
    private BrfTxt cw;
    private BrfTxt drsg;
    private BrfTxt flu;
    private BrfTxt sport;
    private BrfTxt trav;
    private BrfTxt qlty;
    private BrfTxt uv;

    public BrfTxt getComf() {
        return comf;
    }

    public void setComf(BrfTxt comf) {
        this.comf = comf;
    }

    public BrfTxt getCw() {
        return cw;
    }

    public void setCw(BrfTxt cw) {
        this.cw = cw;
    }

    public BrfTxt getDrsg() {
        return drsg;
    }

    public void setDrsg(BrfTxt drsg) {
        this.drsg = drsg;
    }

    public BrfTxt getFlu() {
        return flu;
    }

    public void setFlu(BrfTxt flu) {
        this.flu = flu;
    }

    public BrfTxt getSport() {
        return sport;
    }

    public void setSport(BrfTxt sport) {
        this.sport = sport;
    }

    public BrfTxt getTrav() {
        return trav;
    }

    public void setTrav(BrfTxt trav) {
        this.trav = trav;
    }

    public BrfTxt getQlty() {
        return qlty;
    }

    public void setQlty(BrfTxt qlty) {
        this.qlty = qlty;
    }

    public BrfTxt getUv() {
        return uv;
    }

    public void setUv(BrfTxt uv) {
        this.uv = uv;
    }


    private class BrfTxt {
        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }
}
