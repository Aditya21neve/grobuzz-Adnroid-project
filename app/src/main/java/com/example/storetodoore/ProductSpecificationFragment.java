package com.example.storetodoore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationFragment extends Fragment {

    public ProductSpecificationFragment() {

    }
    private RecyclerView productspecificationRecyclerview;
    public List<ProductSpecificationModel> productSpecificationModelList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);
        productspecificationRecyclerview = view.findViewById(R.id.product_specification_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productspecificationRecyclerview.setLayoutManager(linearLayoutManager);


//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Genral"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Ram","4Gb"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Ram","4Gb"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Ram","4Gb"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"display"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Ram","4Gb"));


        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productspecificationRecyclerview.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

        return view;
    }
}