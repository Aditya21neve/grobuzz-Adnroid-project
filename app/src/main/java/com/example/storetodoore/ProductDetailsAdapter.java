package com.example.storetodoore;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    private String productDescription;
    private String productOtherDetailes;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailsAdapter(@NonNull FragmentManager fm, int totalTabs, String productDescription, String productOtherDetailes, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.productDescription = productDescription;
        this.productOtherDetailes = productOtherDetailes;
        this.productSpecificationModelList = productSpecificationModelList;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                productDescriptionFragment1.body = productDescription;
                return productDescriptionFragment1;
            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelList = productSpecificationModelList;
                return productSpecificationFragment;
            case 2:
                ProductDescriptionFragment productDescriptionFragment2 = new ProductDescriptionFragment();
                productDescriptionFragment2.body = productOtherDetailes;
                return productDescriptionFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
