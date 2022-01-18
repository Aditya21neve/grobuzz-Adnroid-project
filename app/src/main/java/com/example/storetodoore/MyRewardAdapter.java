package com.example.storetodoore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRewardAdapter extends RecyclerView.Adapter<MyRewardAdapter.Viewholder> {

    private List<RewardModel> rewardModelList;
    private Boolean useminiLayout = false;

    public MyRewardAdapter(List<RewardModel> rewardModelList, Boolean useminiLayout) {
        this.rewardModelList = rewardModelList;
        this.useminiLayout = useminiLayout;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (useminiLayout) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mini_rewardsitem_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewards_item_layout, viewGroup, false);

        }
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String title = rewardModelList.get(position).getTitle();
        String date = rewardModelList.get(position).getExpiryDate();
        String body = rewardModelList.get(position).getCoupon_Body();
        holder.setData(title, date, body);

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView couponTitle;
        private TextView couponExpiryDate;
        private TextView couponBody;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            couponTitle = itemView.findViewById(R.id.coupon_title);
            couponExpiryDate = itemView.findViewById(R.id.coupon_validity);
            couponBody = itemView.findViewById(R.id.coupon_body);

        }

        private void setData(String title, String date, String body) {
            couponTitle.setText(title);
            couponExpiryDate.setText(date);
            couponBody.setText(body);

            if (useminiLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Product_Details_Activity.couponTitle.setText(title);
                        Product_Details_Activity.couponExpairyDate.setText(date);
                        Product_Details_Activity.couponBody.setText(body);
                        Product_Details_Activity.showDialogRecyclerView();
                    }
                });
            }
        }
    }
}
