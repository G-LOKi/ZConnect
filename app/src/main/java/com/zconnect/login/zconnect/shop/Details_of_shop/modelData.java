package com.zconnect.login.zconnect.shop.Details_of_shop;

/**
 * Created by f390 on 28/1/17.
 */

public class modelData {
    String Url, Title, Description;

    public modelData(String url, String title, String description) {
        Url = url;
        Title = title;
        Description = description;
    }

    public modelData() {
    }

    public String getUrl() {
        return Url;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }
}
