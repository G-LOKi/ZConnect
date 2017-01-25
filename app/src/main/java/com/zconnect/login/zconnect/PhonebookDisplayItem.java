package com.zconnect.login.zconnect;

/**
 * Created by shubhamk on 21/1/17.
 */

public class PhonebookDisplayItem {
    private String imageurl;
    private String name;
    private String desc;
    private String number;
    private String category;
    private String email;

    public PhonebookDisplayItem(String imageurl, String name, String desc, String number, String email, String category) {
        this.imageurl = imageurl;
        this.name = name;
        this.category = category;
        this.desc = desc;
        this.number = number;
        this.email = email;
    }

    public PhonebookDisplayItem() {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
