package com.zconnect.login.zconnect;

/**
 * Created by shubhamk on 2/1/17.
 */

public class PhonebookItem {
    String imgurl;
    String name;
    String number;

    public PhonebookItem(String imgurl, String name, String number) {
        this.imgurl = imgurl;
        this.name = name;
        this.number = number;
    }

    public PhonebookItem() {
        this.imgurl = "";
        this.name = "";
        this.number = "";
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
