package com.zconnect.login.zconnect.shop.Details_of_shop;

/**
 * Created by f390 on 28/1/17.
 */

public class modelData {
    String Url, Title, Description;

    public modelData(String abc, String def, String ghi) {
        Url = abc;
        Title = def;
        Description = ghi;
    }

    public modelData() {
    }

    public String getUl() {
        return Url;
    }

    public String getTitl() {
        return Title;
    }

    public String getDesc() {
        return Description;
    }
}
