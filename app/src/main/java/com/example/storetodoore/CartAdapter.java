package com.example.storetodoore;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;
    private int lastPosition = -1;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;

            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
                return new CartItemViewholder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout, viewGroup, false);
                return new CartTotalAmountViewholder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (cartItemModelList.get(position).getType()) {
            case CartItemModel.CART_ITEM:
                String productID = cartItemModelList.get(position).getProductID();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                Long freeCoupons = cartItemModelList.get(position).getFreeCoupons();
                String productPrice = cartItemModelList.get(position).getProductPrice();
                String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                Long offersApplied = cartItemModelList.get(position).getOffersApplied();

                ((CartItemViewholder) viewHolder).setItemDetails(productID, resource, title, freeCoupons, productPrice, cuttedPrice, offersApplied,position);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                String totalItems = cartItemModelList.get(position).getTotalItems();
                String totalItemsPrice = cartItemModelList.get(position).getTotalItemPrice();
                String deliveryPrice = cartItemModelList.get(position).getDeliveryPrice();
                String totalAmount = cartItemModelList.get(position).getTotalAmount();
                String savedAmount = cartItemModelList.get(position).getSavedAmount();

                ((CartTotalAmountViewholder) viewHolder).setTotalAmount(totalItems, totalItemsPrice, deliveryPrice, totalAmount, savedAmount);


                break;
            default:
                return;
        }
        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewholder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupons;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView couponsApplied;
        private TextView productQuantity;
        private ImageView freeCouponIcon;

        private LinearLayout deleteBtn;


        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_Title);
            freeCouponIcon = itemView.findViewById(R.id.free_Coupon_icon);
            freeCoupons = itemView.findViewById(R.id.tv_free_coupon);
            productPrice = itemView.findViewById(R.id.product_Price);
            cuttedPrice = itemView.findViewById(R.id.cutted_Price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            couponsApplied = itemView.findViewById(R.id.coupon_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }

        private void setItemDetails(String productID, String resource, String title, Long freeCouponsNo, String productPriceText, String cuttedPriceText, Long offerAppliedNo, int position) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);
            productTitle.setText(title);
            if (freeCouponsNo > 0) {
                freeCouponIcon.setVisibility(View.VISIBLE);
                freeCoupons.setVisibility(View.VISIBLE);
                if (freeCouponsNo == 1) {
                    freeCoupons.setText("free " + freeCouponsNo + " Coupon");
                } else {
                    freeCoupons.setText("free " + freeCouponsNo + " Coupons");
                }
            } else {
                freeCouponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if (offerAppliedNo > 0) {
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offerAppliedNo + " offers appplied");
            } else {
                offersApplied.setVisibility(View.INVISIBLE);
            }
            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quintity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    EditText quantityNO = quantityDialog.findViewById(R.id.quintity_Number);
                    Button cancleBtn = quantityDialog.findViewById(R.id.cancle_Btn);
                    Button okBtn = quantityDialog.findViewById(R.id.Ok_Btn);

                    cancleBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Qty: " + quantityNO.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();

                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Product_Details_Activity.running_cart_query) {
                        Product_Details_Activity.running_cart_query = true;
                        DBquries.removeFromCart(position, itemView.getContext());
                    }
                }
            });

        }
    }

    class CartTotalAmountViewholder extends RecyclerView.ViewHolder {

        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemsPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }

        private void setTotalAmount(String totalItemText, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String savedAmountText) {
            totalItems.setText(totalItemText);
            totalItemsPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedAmountText);
        }
    }
}
