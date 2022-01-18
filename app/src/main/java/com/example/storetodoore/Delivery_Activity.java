package com.example.storetodoore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Delivery_Activity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button changeorAddnewAddressBtn;
    public static final int SELECT_ADDRESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        changeorAddnewAddressBtn = findViewById(R.id.change_or_add_address_btn);
        deliveryRecyclerView = findViewById(R.id.delivery_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);


        List<CartItemModel> cartItemModelList = new ArrayList<>();

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        changeorAddnewAddressBtn.setVisibility(View.VISIBLE);

        changeorAddnewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressesIntent = new Intent(Delivery_Activity.this,MyAddressesActivity.class);
                myAddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}