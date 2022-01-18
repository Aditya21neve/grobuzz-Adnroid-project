package com.example.storetodoore;

public class CategoryModel {

    private String CategoryIconLink;

    public String getCategoryIconLink() {
        return CategoryIconLink;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        CategoryIconLink = categoryIconLink;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public CategoryModel(String categoryIconLink, String categoryName) {
        CategoryIconLink = categoryIconLink;
        CategoryName = categoryName;
    }

    private String CategoryName;
}
