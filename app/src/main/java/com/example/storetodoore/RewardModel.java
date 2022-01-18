package com.example.storetodoore;

public class RewardModel {

    private String title;
    private String expiryDate;
    private String coupon_Body;

    public RewardModel(String title, String expiryDate, String coupon_Body) {
        this.title = title;
        this.expiryDate = expiryDate;
        this.coupon_Body = coupon_Body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCoupon_Body() {
        return coupon_Body;
    }

    public void setCoupon_Body(String coupon_Body) {
        this.coupon_Body = coupon_Body;
    }
}
