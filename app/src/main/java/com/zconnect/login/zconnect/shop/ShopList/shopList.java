package com.zconnect.login.zconnect.shop.ShopList;

/**
 * Created by f390 on 21/1/17.
 */

public class shopList {

    private Double longitude, lattitude;
    private String address, Title, Image_Url, phnNo;

    public shopList(Double longitude, Double lattitude, String address, String title, String image_Url, String phnNo) {
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.address = address;
        Title = title;
        Image_Url = image_Url;
        this.phnNo = phnNo;
    }

    public shopList() {
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return Title;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public String getPhnNo() {
        return phnNo;
    }
}