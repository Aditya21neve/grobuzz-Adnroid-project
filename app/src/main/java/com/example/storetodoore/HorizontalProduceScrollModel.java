package com.example.storetodoore;

public class HorizontalProduceScrollModel {
    private String productID;
    private String productimage;
    private String producttitile;
    private String productdescription;
    private String productprice;

    public HorizontalProduceScrollModel(String productID, String productimage, String producttitile, String productdescription, String productprice) {
        this.productID = productID;
        this.productimage = productimage;
        this.producttitile = producttitile;
        this.productdescription = productdescription;
        this.productprice = productprice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProducttitile() {
        return producttitile;
    }

    public void setProducttitile(String producttitile) {
        this.producttitile = producttitile;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }
}
