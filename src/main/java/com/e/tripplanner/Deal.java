package com.e.tripplanner;

public class Deal {
    private String hdisc,himage,hname;

    public Deal() {
    }

    public Deal(String hdisc, String himage, String hname) {
        this.hdisc = hdisc;
        this.himage = himage;
        this.hname = hname;
    }

    public String getHdisc() {
        return hdisc;
    }

    public void setHdisc(String hdisc) {
        this.hdisc = hdisc;
    }

    public String getHimage() {
        return himage;
    }

    public void setHimage(String himage) {
        this.himage = himage;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }
}
