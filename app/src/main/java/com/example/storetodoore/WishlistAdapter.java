package com.example.storetodoore;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private Boolean wishList;
    private int lastPosition = -1;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishList) {
        this.wishlistModelList = wishlistModelList;
        this.wishList = wishList;
    }

    private List<WishlistModel> wishlistModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String productId = wishlistModelList.get(position).getProductId();
        String resource = wishlistModelList.get(position).getProductimage();
        String title = wishlistModelList.get(position).getProductTitle();
        long freeCoupons = wishlistModelList.get(position).getFreeCoupons();
        String rating = wishlistModelList.get(position).getRatings();
        long totalRatings = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedprice();
        boolean paymentMethod = wishlistModelList.get(position).isCOD();
        viewHolder.setData(productId, resource, title, freeCoupons, rating, totalRatings, productPrice, cuttedPrice, paymentMethod, position);

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupons;
        private ImageView couponIcon;
        private TextView productPricel;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private ImageButton deleteBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.pRoduct_Image);
            productTitle = itemView.findViewById(R.id.pRoduct_title);
            freeCoupons = itemView.findViewById(R.id.fRee_coupon);
            couponIcon = itemView.findViewById(R.id.cOupon_icon);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_Ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPricel = itemView.findViewById(R.id.pRoduct_Price);
            cuttedPrice = itemView.findViewById(R.id.cUtted_Price);
            paymentMethod = itemView.findViewById(R.id.payment_Method);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

        }

        private void setData(String productId, String resource, String title, long freeCouponsNo, String AvrageRate, long totalRatingsNo, String price, String cuttedPriceValue, boolean COD, int index) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.fakeimage)).into(productImage);
            productTitle.setText(title);
            if (freeCouponsNo != 0) {
                couponIcon.setVisibility(View.VISIBLE);
                if (freeCouponsNo == 1) {
                    freeCoupons.setText("free " + freeCouponsNo + " coupon");

                } else {
                    freeCoupons.setText("free " + freeCouponsNo + " coupons");
                }

            } else {
                couponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            rating.setText(AvrageRate);
            totalRatings.setText(("(") + totalRatingsNo + ")ratings");
            productPricel.setText("Rs." + price + "/-");
            cuttedPrice.setText("Rs." + cuttedPriceValue + "/-");
            if (COD) {
                paymentMethod.setVisibility(View.VISIBLE);
            } else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }
            if (wishList) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Product_Details_Activity.running_wishlit_query) {
                        Product_Details_Activity.running_wishlit_query = true;
                        DBquries.removeFromWishlist(index, itemView.getContext());
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(itemView.getContext(), Product_Details_Activity.class);
                    productDetailIntent.putExtra("PRODUCT_ID", productId);
                    itemView.getContext().startActivity(productDetailIntent);
                }
            });
        }
    }

}
