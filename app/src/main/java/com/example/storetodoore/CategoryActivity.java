package com.example.storetodoore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.storetodoore.DBquries.lists;
import static com.example.storetodoore.DBquries.loadFragmentData;
import static com.example.storetodoore.DBquries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerview;
    private List<HomePageModel> homePageModeFAKElList = new ArrayList<>();
    private HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ///////////// home page Fake List
        List<SliderModel> sliderFakeModelsList = new ArrayList<>();
        sliderFakeModelsList.add(new SliderModel("null", "#ffffff"));
        sliderFakeModelsList.add(new SliderModel("null", "#ffffff"));
        sliderFakeModelsList.add(new SliderModel("null", "#ffffff"));
        sliderFakeModelsList.add(new SliderModel("null", "#ffffff"));
        sliderFakeModelsList.add(new SliderModel("null", "#ffffff"));
        sliderFakeModelsList.add(new SliderModel("null", "#ffffff"));

        List<HorizontalProduceScrollModel> horizontalProduceScrollModelFAKEList = new ArrayList<>();

        horizontalProduceScrollModelFAKEList.add(new HorizontalProduceScrollModel("", "", "", "", ""));
        horizontalProduceScrollModelFAKEList.add(new HorizontalProduceScrollModel("", "", "", "", ""));
        horizontalProduceScrollModelFAKEList.add(new HorizontalProduceScrollModel("", "", "", "", ""));
        horizontalProduceScrollModelFAKEList.add(new HorizontalProduceScrollModel("", "", "", "", ""));
        horizontalProduceScrollModelFAKEList.add(new HorizontalProduceScrollModel("", "", "", "", ""));
        horizontalProduceScrollModelFAKEList.add(new HorizontalProduceScrollModel("", "", "", "", ""));
        horizontalProduceScrollModelFAKEList.add(new HorizontalProduceScrollModel("", "", "", "", ""));
        horizontalProduceScrollModelFAKEList.add(new HorizontalProduceScrollModel("", "", "", "", ""));

        homePageModeFAKElList.add(new HomePageModel(0, sliderFakeModelsList));
        homePageModeFAKElList.add(new HomePageModel(1, "", "#ffffff"));
        homePageModeFAKElList.add(new HomePageModel(2, "", "#ffffff", horizontalProduceScrollModelFAKEList, new ArrayList<WishlistModel>()));
        homePageModeFAKElList.add(new HomePageModel(3, "", "#ffffff", horizontalProduceScrollModelFAKEList));
        ///////////// home page Fake List

        categoryRecyclerview = findViewById(R.id.category_Recycler_View);
        LinearLayoutManager testinglayoutmanager = new LinearLayoutManager(this);
        testinglayoutmanager.setOrientation(RecyclerView.VERTICAL);
        categoryRecyclerview.setLayoutManager(testinglayoutmanager);

        adapter = new HomePageAdapter(homePageModeFAKElList);


        int listPosition = 0;
        for (int x = 0; x < loadedCategoriesNames.size(); x++) {
            if (loadedCategoriesNames.get(x).equals(title.toUpperCase())) {
                listPosition = x;

            }
        }
        if (listPosition == 0) {
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());

            loadFragmentData(categoryRecyclerview, this, loadedCategoriesNames.size() - 1,title);

        } else {
            adapter = new HomePageAdapter(lists.get(listPosition));
        }
        categoryRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_Search_icon) {
            //todo: Search
            return true;
        } else if (id == android.R.id.home) {
            Toast.makeText(this,"home",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}