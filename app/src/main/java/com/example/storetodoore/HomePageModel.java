package com.example.storetodoore;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_VIEW = 3;


    private int type;
    private String backgroundColor;

    //////////////Banner Slide View Pager
    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //////////////Banner Slide View Pager

    //////////Strip Ad
    private String resource;

    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    //////////Strip Ad


    private String title;
    private List<HorizontalProduceScrollModel> horizontalProduceScrollModelList;
    ////////////////Horizontal Product Layout
    private List<WishlistModel> viewAllProductList;

    public HomePageModel(int type, String title, String backgroundColor, List<HorizontalProduceScrollModel> horizontalProduceScrollModelList, List<WishlistModel> viewAllProductList) {
        this.type = type;
        this.title = title;
        this.viewAllProductList = viewAllProductList;
        this.backgroundColor = backgroundColor;
        this.horizontalProduceScrollModelList = horizontalProduceScrollModelList;

    }

    public List<WishlistModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishlistModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    ////////////////Horizontal Product Layout

             //////////////////// Grid Product Layout
    public HomePageModel(int type, String title, String backgroundColor, List<HorizontalProduceScrollModel> horizontalProduceScrollModelList) {

        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalProduceScrollModelList = horizontalProduceScrollModelList;

    }
    //////////////////// Grid Product Layout

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalProduceScrollModel> getHorizontalProduceScrollModelList() {
        return horizontalProduceScrollModelList;
    }

    public void setHorizontalProduceScrollModelList(List<HorizontalProduceScrollModel> horizontalProduceScrollModelList) {
        this.horizontalProduceScrollModelList = horizontalProduceScrollModelList;
    }


}
