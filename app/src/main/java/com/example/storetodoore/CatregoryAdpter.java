package com.example.storetodoore;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class CatregoryAdpter extends RecyclerView.Adapter<CatregoryAdpter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    private int lastPosition = -1;

    public CatregoryAdpter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }


    @NonNull
    @Override
    public CatregoryAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatregoryAdpter.ViewHolder viewHolder, int position) {
        String icon = categoryModelList.get(position).getCategoryIconLink();
        String name = categoryModelList.get(position).getCategoryName();
        viewHolder.setCategory(name, position);
        viewHolder.setCategoryIcon(icon);
        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView CategoryIcon;
        private TextView CategoryName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryIcon = itemView.findViewById(R.id.category_icon);
            CategoryName = itemView.findViewById(R.id.category_name);

        }

        private void setCategoryIcon(String iconUrl) {
            if (!iconUrl.equals("null")) {
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.fakeimage)).into(CategoryIcon);
            } else {
                CategoryIcon.setImageResource(R.drawable.ic_baseline_home_24);
            }

        }

        private void setCategory(String name, int position) {
            CategoryName.setText(name);
            if (!name.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                            categoryIntent.putExtra("CategoryName", name);
                            itemView.getContext().startActivity(categoryIntent);
                        }
                    }
                });

            }
        }
    }
}

