package com.example.storetodoore;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

    private List<HorizontalProduceScrollModel> horizontalProduceScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProduceScrollModel> horizontalProduceScrollModelList) {
        this.horizontalProduceScrollModelList = horizontalProduceScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_iteam_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
        String resource = horizontalProduceScrollModelList.get(position).getProductimage();
        String title = horizontalProduceScrollModelList.get(position).getProducttitile();
        String description = horizontalProduceScrollModelList.get(position).getProductdescription();
        String price = horizontalProduceScrollModelList.get(position).getProductprice();
        String productId = horizontalProduceScrollModelList.get(position).getProductID();
         holder.setData(resource,title,description,price,productId);
    }

    @Override
    public int getItemCount() {
        if (horizontalProduceScrollModelList.size() > 8) {
            return 8;
        } else {
            return horizontalProduceScrollModelList.size();

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productimage;
        private TextView producttitle;
        private TextView productdescription;
        private TextView productprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage = itemView.findViewById(R.id.h_s_product_image);
            producttitle = itemView.findViewById(R.id.h_s_product_title);
            productdescription = itemView.findViewById(R.id.h_s_product_description);
            productprice = itemView.findViewById(R.id.h_s_product_price);


        }

        private void setData(final String productId,String resource, String title, String description, String price) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.fakeimage)).into(productimage);
            producttitle.setText(title);
            productdescription.setText(description);
            productprice.setText("Rs." + price + "/-");
            if (!title.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), Product_Details_Activity.class);
                        productDetailsIntent.putExtra("PRODUCT_ID",productId);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }


        }


    }
}
