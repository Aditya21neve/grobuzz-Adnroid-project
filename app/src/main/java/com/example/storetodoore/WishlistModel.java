package com.example.storetodoore;

public class WishlistModel {
    private String productId;
    private String productimage;
    private String productTitle;
    private long freeCoupons;
    private String ratings;
    private long totalRatings;
    private String productPrice;
    private String cuttedprice;
    private boolean COD;

    public WishlistModel(String productId, String productimage, String productTitle, long freeCoupons, String ratings, long totalRatings, String productPrice, String cuttedprice, boolean COD) {
        this.productId = productId;
        this.productimage = productimage;
        this.productTitle = productTitle;
        this.freeCoupons = freeCoupons;
        this.ratings = ratings;
        this.totalRatings = totalRatings;
        this.productPrice = productPrice;
        this.cuttedprice = cuttedprice;
        this.COD = COD;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupons() {
        return freeCoupons;
    }

    public void setFreeCoupons(long freeCoupons) {
        this.freeCoupons = freeCoupons;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedprice() {
        return cuttedprice;
    }

    public void setCuttedprice(String cuttedprice) {
        this.cuttedprice = cuttedprice;
    }

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }
}