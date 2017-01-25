package com.zconnect.login.zconnect;

/**
 * Created by Lokesh Garg on 14-11-2016.
 */
public class Product {
    private String ProductName,ProductDescription,Image,Key;

    public  Product(){

    }

    public Product(String productName, String productDescription, String image, String key) {
        ProductName = productName;
        ProductDescription = productDescription;
        Image = image;
        Key = key;
    }



    public String getProductName() {
        return ProductName;
    }

    public String getKey()
    {
        return Key;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
