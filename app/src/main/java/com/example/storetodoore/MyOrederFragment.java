package com.example.storetodoore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MyOrederFragment extends Fragment {

    public MyOrederFragment() {

    }

private RecyclerView myOrdersRecyclerview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_oreder, container, false);
        myOrdersRecyclerview = view.findViewById(R.id.my_order_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrdersRecyclerview.setLayoutManager(layoutManager);

        List<MyOrderitemModel> myOrderitemModelList = new ArrayList<>();
        myOrderitemModelList.add(new MyOrderitemModel(R.drawable.shopping4,2,"rice","delivered on 28th Feb  2021"));
        myOrderitemModelList.add(new MyOrderitemModel(R.drawable.shopping,1,"rice","delivered on 28th Feb  2021"));
        myOrderitemModelList.add(new MyOrderitemModel(R.drawable.shopping1,0,"rice","Cancelled"));
        myOrderitemModelList.add(new MyOrderitemModel(R.drawable.shopping2,4,"rice","delivered on 28th Feb  2021"));

        MyOrderAdpter myOrderAdpter = new MyOrderAdpter(myOrderitemModelList);
        myOrdersRecyclerview.setAdapter(myOrderAdpter);
        myOrderAdpter.notifyDataSetChanged();

        return view;
    }
}