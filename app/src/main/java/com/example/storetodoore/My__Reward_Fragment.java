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

public class My__Reward_Fragment extends Fragment {


    public My__Reward_Fragment() {
        // Required empty public constructor
    }

    private RecyclerView rewardsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my___reward_, container, false);

        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        rewardsRecyclerView.setLayoutManager(layoutManager);
        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback","till 2nd May 2021","GET 20% CASHBACK on any product above Rs.200/-" ));
        rewardModelList.add(new RewardModel("Cashback","till 2nd May 2021","GET 20% CASHBACK on any product above Rs.200/-" ));
        rewardModelList.add(new RewardModel("Cashback","till 2nd May 2021","GET 20% CASHBACK on any product above Rs.200/-" ));
        rewardModelList.add(new RewardModel("Cashback","till 2nd May 2021","GET 20% CASHBACK on any product above Rs.200/-" ));
        rewardModelList.add(new RewardModel("Cashback","till 2nd May 2021","GET 20% CASHBACK on any product above Rs.200/-" ));
        rewardModelList.add(new RewardModel("Cashback","till 2nd May 2021","GET 20% CASHBACK on any product above Rs.200/-" ));
        rewardModelList.add(new RewardModel("Cashback","till 2nd May 2021","GET 20% CASHBACK on any product above Rs.200/-" ));
        rewardModelList.add(new RewardModel("Cashback","till 2nd May 2021","GET 20% CASHBACK on any product above Rs.200/-" ));

        MyRewardAdapter myRewardAdapter = new MyRewardAdapter(rewardModelList,false);
        rewardsRecyclerView.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();
        return view;
    }
}