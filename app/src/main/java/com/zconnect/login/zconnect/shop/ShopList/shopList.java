package com.zconnect.login.zconnect.shop.ShopList;

/**
 * Created by f390 on 21/1/17.
 */

public class shopList {

    Double longitude, lattitude;
    String address, Title, FirebaseUID, Image_Url, phnNo;

    public shopList(Double longitude, Double lattitude, String address, String title, String firebaseUID, String image_Url, String phnNo) {
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.address = address;
        Title = title;
        FirebaseUID = firebaseUID;
        Image_Url = image_Url;
        this.phnNo = phnNo;
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

    public String getFirebaseUID() {
        return FirebaseUID;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public String getPhnNo() {
        return phnNo;
    }
}