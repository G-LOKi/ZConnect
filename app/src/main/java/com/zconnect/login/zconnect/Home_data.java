package com.zconnect.login.zconnect;


/**
 * Created by Lokesh Garg on 14-11-2016.
 */
public class Home_data {
    private String Title, Description, Url, multiUse2, multiUse1, Phone_no;
    private long type;

    public Home_data() {

    }

    public Home_data(String title, String description, String url, String multiUse2, String multiUse1, String phnNo, long type) {
        Title = title;
        Description = description;
        Url = url;
        this.multiUse2 = multiUse2;
        this.type = type;
        this.multiUse1 = multiUse1;
        Phone_no = phnNo;
    }

    public String getTitle() {
        return Title;
    }

    public long getType() {
        return type;
    }

    public String getDescription() {
        return Description;
    }

    public String getUrl() {
        return Url;
    }

    public String getmultiUse2() {
        return multiUse2;
    }

    public String getmultiUse1() {
        return multiUse1;
    }

    public String getPhone_no() {
        return Phone_no;
    }
}