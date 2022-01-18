package com.example.storetodoore;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MyCartFragment extends Fragment {
    public MyCartFragment() {

    }

    private RecyclerView cartItemsRecyclerView;
    private Button continuBtn;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        //////////Loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////////Loading dialog

        cartItemsRecyclerView = view.findViewById(R.id.cart_items_RecyclerView);
        continuBtn = view.findViewById(R.id.cart_continue_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);


        if (DBquries.cartItemModelList.size() == 0) {
            DBquries.cartList.clear();
            DBquries.loadCartList(getContext(), loadingDialog, true);

        } else {
            loadingDialog.dismiss();
        }
        cartAdapter = new CartAdapter(DBquries.cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();


        continuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(getContext(), AddAddresActivity.class);
                getContext().startActivity(deliveryIntent);
            }
        });
        return view;
    }

}
