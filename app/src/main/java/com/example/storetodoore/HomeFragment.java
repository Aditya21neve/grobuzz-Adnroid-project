 package com.example.storetodoore;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;

import static com.example.storetodoore.DBquries.categoryModelList;
import static com.example.storetodoore.DBquries.firebaseFirestore;

import static com.example.storetodoore.DBquries.lists;
import static com.example.storetodoore.DBquries.loadCategories;
import static com.example.storetodoore.DBquries.loadFragmentData;
import static com.example.storetodoore.DBquries.loadedCategoriesNames;


public class HomeFragment extends Fragment {


    public HomeFragment() {

    }

    private RecyclerView categoreRecyclerview;
    private CatregoryAdpter catregoryAdpter;
    private List<CategoryModel> categoryModelFAKEList = new ArrayList<>();
    private RecyclerView homePageRecyclerView;
    private List<HomePageModel> homePageModeFAKElList = new ArrayList<>();
    private ImageView noInternetconnection;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button retryBtn;
    private FirebaseFirestore firebaseFirestore;

    private HomePageAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_Layout);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.delete), getContext().getResources().getColor(R.color.delete), getContext().getResources().getColor(R.color.delete));

        noInternetconnection = view.findViewById(R.id.no_internet_connection);
        homePageRecyclerView = view.findViewById(R.id.home_page_RecyclerView);
        retryBtn = view.findViewById(R.id.retry_btn);

        categoreRecyclerview = view.findViewById(R.id.Category_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoreRecyclerview.setLayoutManager(linearLayoutManager);

        firebaseFirestore = FirebaseFirestore.getInstance();
        LinearLayoutManager testinglayoutmanager = new LinearLayoutManager(getContext());
        testinglayoutmanager.setOrientation(RecyclerView.VERTICAL);
        homePageRecyclerView.setLayoutManager(testinglayoutmanager);

///////////// Category Fake List
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));
        categoryModelFAKEList.add(new CategoryModel("null", ""));

///////////// Category Fake List


        
        ///////////// home page Fake List
        List<SliderModel> sliderFakeModelsList = new ArrayList<>();
        sliderFakeModelsList.add(new SliderModel("null", "#dfdfdf"));
        sliderFakeModelsList.add(new SliderModel("null", "#dfdfdf"));
        sliderFakeModelsList.add(new SliderModel("null", "#dfdfdf"));
        sliderFakeModelsList.add(new SliderModel("null", "#dfdfdf"));
        sliderFakeModelsList.add(new SliderModel("null", "#dfdfdf"));
        sliderFakeModelsList.add(new SliderModel("null", "#dfdfdf"));

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
        homePageModeFAKElList.add(new HomePageModel(1, "", "#dfdfdf"));
        homePageModeFAKElList.add(new HomePageModel(2, "", "#dfdfdf", horizontalProduceScrollModelFAKEList, new ArrayList<WishlistModel>()));
        homePageModeFAKElList.add(new HomePageModel(3, "", "#dfdfdf", horizontalProduceScrollModelFAKEList));
        ///////////// home page Fake List

        catregoryAdpter = new CatregoryAdpter(categoryModelFAKEList);


        adapter = new HomePageAdapter(homePageModeFAKElList);


        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            noInternetconnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoreRecyclerview.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);


            if (categoryModelList.size() == 0) {
                loadCategories(categoreRecyclerview, getContext());

            } else {
                catregoryAdpter = new CatregoryAdpter(categoryModelList);
                catregoryAdpter.notifyDataSetChanged();
            }
            categoreRecyclerview.setAdapter(catregoryAdpter);
            if (lists.size() == 0) {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                adapter = new HomePageAdapter(lists.get(0));
                loadFragmentData(homePageRecyclerView, getContext(), 0, "Home");


            } else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);


        } else {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            categoreRecyclerview.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.internet5).into(noInternetconnection);
            noInternetconnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);


        }

        ////////////Refresh Layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();

            }
        });
        ////////////Refresh Layout
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadPage();
            }
        });

        return view;
    }

    private void reloadPage() {
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();


        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            categoreRecyclerview.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            noInternetconnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);


            catregoryAdpter = new CatregoryAdpter(categoryModelFAKEList);
            adapter = new HomePageAdapter(homePageModeFAKElList);
            categoreRecyclerview.setAdapter(catregoryAdpter);
            homePageRecyclerView.setAdapter(adapter);

            loadCategories(categoreRecyclerview, getContext());

            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView, getContext(), 0, "Home");


        } else {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            Toast.makeText(getContext(), "No Internet connection ", Toast.LENGTH_SHORT).show();
            categoreRecyclerview.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.internet5).into(noInternetconnection);
            noInternetconnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);


            swipeRefreshLayout.setRefreshing(false);


        }


    }
}