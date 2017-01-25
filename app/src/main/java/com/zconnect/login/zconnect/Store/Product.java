package com.zconnect.login.zconnect.Store;

public class Product {
    private String ProductName,ProductDescription,Image,Price;

      Product(){

    }

     Product(String productName, String productDescription, String image,String price) {
        ProductName = productName;
        ProductDescription = productDescription;
        Image = image;
        Price = price;
    }

    String getProductName() {
        return ProductName;
    }

    void setProductName(String productName) {
        ProductName = productName;
    }

     String getProductDescription() {
        return ProductDescription;
    }

     void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

     String getImage() {
        return Image;
    }

    void setImage(String image) {
        Image = image;
    }

    String getPrice() {
        return Price;
    }
}
