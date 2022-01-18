package com.example.storetodoore;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrderAdpter extends RecyclerView.Adapter<MyOrderAdpter.ViewHolder> {

    private List<MyOrderitemModel> myOrderitemModelList;

    public MyOrderAdpter(List<MyOrderitemModel> myOrderitemModelList) {
        this.myOrderitemModelList = myOrderitemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdpter.ViewHolder holder, int position) {
        int resource = myOrderitemModelList.get(position).getProductImage();
        int rating = myOrderitemModelList.get(position).getRating();
        String title = myOrderitemModelList.get(position).getProductTitle();
        String deliveredDate = myOrderitemModelList.get(position).getDeliveryStatus();
        holder.setData(resource,title,deliveredDate,rating);


    }

    @Override
    public int getItemCount() {
        return myOrderitemModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private ImageView orderIndicatior;
        private TextView productTitle;
        private TextView deliveryStatus;
        private LinearLayout rateNowcontainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_images);
            orderIndicatior = itemView.findViewById(R.id.order_indicator);
            productTitle = itemView.findViewById(R.id.product_order_title);
            deliveryStatus = itemView.findViewById(R.id.order_deliverd_date);
            rateNowcontainer = itemView.findViewById(R.id.rate_now_contaner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });
        }

        private void setData(int resource, String title, String deliveredDate,int rating) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if (deliveredDate.equals("Cancelled")) {
                orderIndicatior.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.delete)));
            } else {
                orderIndicatior.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.submitgreen)));

            }
            deliveryStatus.setText(deliveredDate);
            /////////////// rating layout
            setRating(rating);

            for (int x = 0; x < rateNowcontainer.getChildCount(); x++) {
                final int starPosition = x;
                rateNowcontainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }
            /////////////// rating layout

        }

        private void setRating(int starPosition) {
            for (int x = 0; x < rateNowcontainer.getChildCount(); x++) {
                ImageView starBtn = (ImageView) rateNowcontainer.getChildAt(x);
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if (x <= starPosition) {
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
        }


    }


}
