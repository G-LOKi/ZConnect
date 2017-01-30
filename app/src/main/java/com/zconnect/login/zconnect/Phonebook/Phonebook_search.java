package com.zconnect.login.zconnect.Phonebook;

public class Phonebook_search {
    String category , desc, name,imageurl,email,number;

    public Phonebook_search(String category, String desc, String name, String imageurl, String email, String number) {
        this.category = category;
        this.desc = desc;
        this.name = name;
        this.imageurl = imageurl;
        this.email = email;
        this.number = number;
    }
    Phonebook_search()
    {

    }

    public String getCategory() {
        return category;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }
}
