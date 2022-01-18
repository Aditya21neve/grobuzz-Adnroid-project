package com.example.storetodoore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.storetodoore.Delivery_Activity.SELECT_ADDRESS;


public class MyAddressesActivity extends AppCompatActivity {

    private RecyclerView myAddressesRecyclerView;
    private static AddressesAdapter addressesAdapter;
    private Button deliverHereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myAddressesRecyclerView = findViewById(R.id.addresses_RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);
        deliverHereBtn = findViewById(R.id.delivered_hereBtn);

        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("John doe","dbfhdfbd fjdfj djfhjdh","431001",true));
        addressesModelList.add(new AddressesModel("John doe","dbfhdfbd fjdfj djfhjdh","431001",false));
        addressesModelList.add(new AddressesModel("John doe","dbfhdfbd fjdfj djfhjdh","431001",false));
        addressesModelList.add(new AddressesModel("John doe","dbfhdfbd fjdfj djfhjdh","431001",false));
        addressesModelList.add(new AddressesModel("John doe","dbfhdfbd fjdfj djfhjdh","431001",false));
        addressesModelList.add(new AddressesModel("John doe","dbfhdfbd fjdfj djfhjdh","431001",false));
        addressesModelList.add(new AddressesModel("John doe","dbfhdfbd fjdfj djfhjdh","431001",false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if (mode == SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else {
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter = new AddressesAdapter(addressesModelList,mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();


    }
    public static void refreshitem(int deselect,int selecet){
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(selecet);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}