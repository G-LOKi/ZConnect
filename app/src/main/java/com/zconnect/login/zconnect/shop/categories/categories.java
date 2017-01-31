package com.zconnect.login.zconnect.shop.categories;

/**
 * Created by f390 on 19/1/17.
 */

public class categories {


    String name, url;

    public categories(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public categories() {
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
