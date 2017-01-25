package com.zconnect.login.zconnect;

/**
 * Created by shubhamk on 2/1/17.
 */

public class PhonebookItem {
    String imgurl;
    String name;
    String number;
    PhonebookDisplayItem phonebookDisplayItem;

    public PhonebookItem(String imgurl, String name, String number, PhonebookDisplayItem phonebookDisplayItem) {
        this.imgurl = imgurl;
        this.name = name;
        this.number = number;
        this.phonebookDisplayItem = phonebookDisplayItem;
    }

    public PhonebookItem() {
    }

    public PhonebookDisplayItem getPhonebookDisplayItem() {
        return phonebookDisplayItem;
    }

    public void setPhonebookDisplayItem(PhonebookDisplayItem phonebookDisplayItem) {
        this.phonebookDisplayItem = phonebookDisplayItem;
    }

    public String getImgurl() {
        if (phonebookDisplayItem != null) return phonebookDisplayItem.getImageurl();
        else return "";
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        if (phonebookDisplayItem != null) return phonebookDisplayItem.getName();
        else return "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        if (phonebookDisplayItem != null) return phonebookDisplayItem.getNumber();
        else return "";
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
