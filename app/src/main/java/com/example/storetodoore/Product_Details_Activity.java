package com.example.storetodoore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.app.Dialog;
import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.storetodoore.MainActivity.showCart;
import static com.example.storetodoore.Register1.setSignUpFragment;

public class Product_Details_Activity extends AppCompatActivity {
    public static boolean running_wishlit_query = false;
    public static boolean running_rating_query = false;
    public static boolean running_cart_query = false;

    private ViewPager productImagesViewpager;
    private TextView product_title;
    private TextView avrageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView productCuttedPrice;
    private ImageView codIndicator;
    private TextView tvCODIndicator;
    private TextView rewardTitle;
    private TextView rewardBody;
    private Dialog signInDialog;

    private FirebaseUser currentUser;

    private TabLayout viewpagerIndicator;
    public static FloatingActionButton addToWishlistBtn;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static boolean ALREADY_ADDED_TO_CART = false;

    private ViewPager productDetailsViewpager;
    private TabLayout productDetailsTabLayout;
    private LinearLayout couponRedeemtionLayout;
    private Button couponRedeemBtn;


    /////////////// rating layout
    public static int initialRating;
    public static LinearLayout rateNowcontainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgressBarContainer;
    private TextView avrageRating;
    /////////////// rating layout

    /////Product Discription layout
    private ConstraintLayout productDetailsOnlycontainer;
    private ConstraintLayout productDetailsTabscontainer;
    private String productDescription;
    private String productOthersDetails;
    private TextView productOnlyDescriptionBody;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

    /////Product Discription layout
    private Button buyNowBtn;
    private LinearLayout addToCartBtn;
    public static MenuItem cartItem;
    private Dialog loadingDialog;


    ////////////////Coupondialog
    public static TextView couponTitle;
    public static TextView couponBody;
    public static TextView couponExpairyDate;
    private static RecyclerView couponsRecyclerView;
    private static LinearLayout selectedCoupon;
    ////////////////Coupondialog
    private FirebaseFirestore firebaseFirestore;
    public static String productID;
    private DocumentSnapshot documentSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewpager = findViewById(R.id.product_images_ViewPager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        couponRedeemBtn = findViewById(R.id.coupen_redemption_btn);
        product_title = findViewById(R.id.product_title);
        avrageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        productCuttedPrice = findViewById(R.id.cutted_price);
        codIndicator = findViewById(R.id.cod_indicator_imageview);
        tvCODIndicator = findViewById(R.id.tv_cod_indicator);
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);
        productDetailsTabscontainer = findViewById(R.id.product_details_tabs_container);
        productDetailsOnlycontainer = findViewById(R.id.product_details_Contoner);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_numbers_contoner);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        avrageRating = findViewById(R.id.avrage_rating);
        couponRedeemtionLayout = findViewById(R.id.coupen_redemption_layout);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);

        initialRating = -1;
        //////////Loading dialog
        loadingDialog = new Dialog(Product_Details_Activity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////////Loading dialog

        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("PRODUCT_ID");
//        firebaseFirestore.collection("PRODUCTS").document("fMsGoqwF7DJtBv8qlmxV")
        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();

                    for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductimagesAdapter productimagesAdapter = new ProductimagesAdapter(productImages);
                    productImagesViewpager.setAdapter(productimagesAdapter);
                    product_title.setText(documentSnapshot.get("product_title").toString());
                    avrageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    totalRatingMiniView.setText("(" + (long) documentSnapshot.get("total_ratings") + ")ratings");
                    productPrice.setText("Rs." + documentSnapshot.get("product_price").toString() + "/-");
                    productCuttedPrice.setText("Rs." + documentSnapshot.get("cutted_price").toString() + "/-");

                    if ((boolean) documentSnapshot.get("COD")) {
                        codIndicator.setVisibility(View.VISIBLE);
                        tvCODIndicator.setVisibility(View.VISIBLE);
                    } else {
                        codIndicator.setVisibility(View.INVISIBLE);
                        tvCODIndicator.setVisibility(View.INVISIBLE);
                    }
                    rewardTitle.setText((long) documentSnapshot.get("free_coupens") + documentSnapshot.get("free_coupen_title").toString());
                    rewardBody.setText(documentSnapshot.get("free_coupen_body").toString());

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabscontainer.setVisibility(View.VISIBLE);
                        productDetailsOnlycontainer.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("product_description").toString();
                        productSpecificationModelList = new ArrayList<>();
                        productOthersDetails = documentSnapshot.get("product_other_details").toString();

                        productSpecificationModelList = new ArrayList<>();


                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_title") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));
                            for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; y++) {
                                boolean add = productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name").toString(), documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value").toString()));

                            }
                        }

                    } else {
                        productDetailsTabscontainer.setVisibility(View.GONE);
                        productDetailsOnlycontainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                    }
                    totalRatings.setText((long) documentSnapshot.get("total_ratings") + " ratings");
                    for (int x = 0; x < 5; x++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long) documentSnapshot.get(5 - x + "_star")));

                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));
                    }


                    totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings")));

                    avrageRating.setText(documentSnapshot.get("average_rating").toString());
                    productDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOthersDetails, productSpecificationModelList));

                    if (currentUser != null) {

                        if (DBquries.myRating.size() == 0) {
                            DBquries.loadRatingList(Product_Details_Activity.this);
                        }
                        if (DBquries.cartList.size() == 0) {
                            DBquries.loadCartList(Product_Details_Activity.this, loadingDialog, false);
                        }
                        if (DBquries.wishList.size() == 0) {
                            DBquries.loadWishList(Product_Details_Activity.this, loadingDialog, false);

                        } else {
                            loadingDialog.dismiss();
                        }
                    } else {
                        loadingDialog.dismiss();
                    }


                    if (DBquries.wishList.contains(productID)) {
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.Red));
                    } else {
                        addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        ALREADY_ADDED_TO_WISHLIST = false;
                    }


                } else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(Product_Details_Activity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

        });

        productDetailsViewpager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);


        viewpagerIndicator.setupWithViewPager(productImagesViewpager, true);

        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {

                    if (!running_wishlit_query) {
                        running_wishlit_query = true;


                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = DBquries.wishList.indexOf(productID);
                            DBquries.removeFromWishlist(index, Product_Details_Activity.this);
                            addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            addToWishlistBtn.setImageTintList(getResources().getColorStateList(R.color.Red));
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBquries.wishList.size()), productID);
                            addProduct.put("list_size", (long) (DBquries.wishList.size() + 1));
                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        if (DBquries.wishlistModelList.size() != 0) {
                                            DBquries.wishlistModelList.add((new WishlistModel(productID, documentSnapshot.get("product_image_1").toString()
                                                    , documentSnapshot.get("product_title").toString()
                                                    , (long) documentSnapshot.get("free_coupens")
                                                    , documentSnapshot.get("average_rating").toString()
                                                    , (long) documentSnapshot.get("total_rating")
                                                    , documentSnapshot.get("product_price").toString()
                                                    , documentSnapshot.get("cutted_price").toString()
                                                    , (boolean) documentSnapshot.get("COD"))));
                                        }
                                        ALREADY_ADDED_TO_WISHLIST = true;
                                        addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.Red));
                                        DBquries.wishList.add(productID);
                                        Toast.makeText(Product_Details_Activity.this, "Added to Wishlist successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                        String error = task.getException().getMessage();
                                        Toast.makeText(Product_Details_Activity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    running_wishlit_query = false;
                                }
                            });
                        }
                    }
                }
            }
        });

        ///////coupon Dialog
        Dialog checkCouponPriceDialog = new Dialog(Product_Details_Activity.this);
        checkCouponPriceDialog.setContentView(R.layout.coupon_reedem_dialog);
        checkCouponPriceDialog.setCancelable(true);
        checkCouponPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCouponPriceDialog.findViewById(R.id.toggle_recyclerView);
        couponsRecyclerView = checkCouponPriceDialog.findViewById(R.id.coupons_Recyclerview);
        selectedCoupon = checkCouponPriceDialog.findViewById(R.id.selected_Coupon);


        couponTitle = checkCouponPriceDialog.findViewById(R.id.coupon_title);
        couponExpairyDate = checkCouponPriceDialog.findViewById(R.id.coupon_validity);
        couponBody = checkCouponPriceDialog.findViewById(R.id.coupon_body);

        TextView orginalPrice = checkCouponPriceDialog.findViewById(R.id.orginal_Price);
        TextView discountedPrice = checkCouponPriceDialog.findViewById(R.id.discounted_Price);


        LinearLayoutManager layoutManager = new LinearLayoutManager(Product_Details_Activity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        couponsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback", "till 2nd May 2021", "GET 20% CASHBACK on any product above Rs.200/-"));
        rewardModelList.add(new RewardModel("avx", "till 2nd May 2021", "GET 20% CASHBACK on any product above Rs.200/-"));
        rewardModelList.add(new RewardModel("cdbj", "till 2nd May 2021", "GET 20% CASHBACK on any product above Rs.200/-"));
        rewardModelList.add(new RewardModel("Cashdfdfdback", "till 2nd May 2021", "GET 20% CASHBACK on any product above Rs.200/-"));
        rewardModelList.add(new RewardModel("Casdf", "till 2nd May 2021", "GET 20% CASHBACK on any product above Rs.200/-"));
        rewardModelList.add(new RewardModel("dssdack", "till 2nd May 2021", "GET 20% CASHBACK on any product above Rs.200/-"));
        rewardModelList.add(new RewardModel("Casdfdfdfdfdfhback", "till 2nd May 2021", "GET 20% CASHBACK on any product above Rs.200/-"));
        rewardModelList.add(new RewardModel("free", "till 2nd May 2021", "GET 20% CASHBACK on any product above Rs.200/-"));


        MyRewardAdapter myRewardAdapter = new MyRewardAdapter(rewardModelList, true);
        couponsRecyclerView.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });
        /////Coupon Dialog
        couponRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCouponPriceDialog.show();


            }
        });

        ///////////// sign in diallog
        signInDialog = new Dialog(Product_Details_Activity.this);
        signInDialog.setContentView(R.layout.signin_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.SignIn_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.signUpbtn);
        Intent registerIntent = new Intent(Product_Details_Activity.this, Register1.class);
        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn = true;
                SignInFragment.disableCloseBtn = true;
                setSignUpFragment = false;
                signInDialog.dismiss();
                startActivity(registerIntent);

            }
        });
        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn = true;
                SignInFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);


            }
        });


///////////// sign in diallog
        productDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /////////////// rating layout

        rateNowcontainer = findViewById(R.id.rate_now_contaner);
        for (int x = 0; x < rateNowcontainer.getChildCount(); x++) {
            final int starPosition = x;
            rateNowcontainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        if (starPosition != initialRating) {
                            if (!running_rating_query) {
                                running_rating_query = true;

                                setRating(starPosition);
                                Map<String, Object> updateRating = new HashMap<>();
                                if (DBquries.myRatedIds.contains(productID)) {

                                    TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                    TextView finalrating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                    updateRating.put(initialRating + 1 + "_star", Long.parseLong(oldRating.getText().toString()) - 1);
                                    updateRating.put(starPosition + 1 + "_star", Long.parseLong(finalrating.getText().toString()) + 1);
                                    updateRating.put("average_rating", calculateAvrageRating((long) starPosition - initialRating, true));
                                } else {

                                    updateRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                    updateRating.put("average_rating", String.valueOf(calculateAvrageRating((long) starPosition + 1, false)));
                                    updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);

                                }

                                firebaseFirestore.collection("PRODUCTS").document(productID)
                                        .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Map<String, Object> myRating = new HashMap<>();
                                            if (DBquries.myRatedIds.contains(productID)) {
                                                myRating.put("rating_" + DBquries.myRatedIds.indexOf(productID), (long) starPosition + 1);
                                            } else {


                                                myRating.put("list_size", (long) DBquries.myRatedIds.size() + 1);
                                                myRating.put("product_ID_" + DBquries.myRatedIds.size(), productID);
                                                myRating.put("rating_" + DBquries.myRatedIds.size(), (long) starPosition + 1);
                                            }
//

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATINGS")
                                                    .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (DBquries.myRatedIds.contains(productID)) {

                                                            DBquries.myRating.set(DBquries.myRatedIds.indexOf(productID), (long) starPosition + 1);

                                                            TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                            TextView finalrating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                                            oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                            finalrating.setText(String.valueOf(Integer.parseInt(finalrating.getText().toString()) + 1));
                                                        } else {
                                                            DBquries.myRatedIds.add(productID);
                                                            DBquries.myRating.add((long) starPosition + 1);

                                                            TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                            totalRatingMiniView.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + ")ratings");
                                                            totalRatings.setText((long) documentSnapshot.get("total_ratings") + 1 + " ratings");
                                                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));


                                                            Toast.makeText(Product_Details_Activity.this, "Thank you ! for rating", Toast.LENGTH_SHORT).show();
                                                        }

                                                        for (int x = 0; x < 5; x++) {
                                                            TextView ratingfigures = (TextView) ratingsNoContainer.getChildAt(x);

                                                            ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                                            int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());
                                                            progressBar.setMax(maxProgress);

                                                            progressBar.setProgress(Integer.parseInt(ratingfigures.getText().toString()));


                                                        }
                                                        initialRating = starPosition;
                                                        avrageRating.setText(calculateAvrageRating(0, true));
                                                        avrageRatingMiniView.setText(calculateAvrageRating(0, true));

                                                        if (DBquries.wishList.contains(productID) && DBquries.wishlistModelList.size() != 0) {

                                                            int index = DBquries.wishList.indexOf(productID);
                                                            DBquries.wishlistModelList.get(index).setRatings(avrageRating.getText().toString());
                                                            DBquries.wishlistModelList.get(index).setTotalRatings(Long.parseLong(totalRatingsFigure.getText().toString()));

                                                        }

                                                    } else {
                                                        setRating(initialRating);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(Product_Details_Activity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    running_rating_query = false;

                                                }
                                            });
                                        } else {
                                            running_rating_query = false;
                                            setRating(initialRating);
                                            String error = task.getException().getMessage();
                                            Toast.makeText(Product_Details_Activity.this, error, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });


                            }
                        }
                    }
                }

            });


        }
        /////// rating layout
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent deliveryIntent = new Intent(Product_Details_Activity.this, Delivery_Activity.class);
                    startActivity(deliveryIntent);
                }
            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (!running_cart_query) {
                        running_cart_query = true;

                        if (ALREADY_ADDED_TO_CART) {
                            running_cart_query = false;
                            Toast.makeText(Product_Details_Activity.this, "Already added to cart!", Toast.LENGTH_SHORT).show();

                        } else {

                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBquries.cartList.size()), productID);
                            addProduct.put("list_size", (long) (DBquries.cartList.size() + 1));

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        if (DBquries.cartItemModelList.size() != 0) {
                                            DBquries.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID, documentSnapshot.get("product_image_1").toString(),
                                                    documentSnapshot.get("product_title").toString()
                                                    , (long) documentSnapshot.get("free_coupens")
                                                    , documentSnapshot.get("product_price").toString()
                                                    , documentSnapshot.get("cutted_price").toString()
                                                    , (long) 1
                                                    , (long) 0
                                                    , (long) 0));
                                        }
                                        ALREADY_ADDED_TO_CART = true;
                                        DBquries.cartList.add(productID);
                                        Toast.makeText(Product_Details_Activity.this, "Added to Cart successfully", Toast.LENGTH_SHORT).show();
                                        invalidateOptionsMenu();
                                        running_cart_query = false;
                                    } else {
                                        running_cart_query = false;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(Product_Details_Activity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            couponRedeemtionLayout.setVisibility(View.GONE);

        } else {
            couponRedeemtionLayout.setVisibility(View.VISIBLE);
        }
        if (currentUser != null) {
            if (DBquries.myRating.size() == 0) {
                DBquries.loadRatingList(Product_Details_Activity.this);
            }
            if (DBquries.cartList.size() == 0) {
                DBquries.loadCartList(Product_Details_Activity.this, loadingDialog, false);
            }

            if (DBquries.wishList.size() == 0) {
                DBquries.loadWishList(Product_Details_Activity.this, loadingDialog, false);

            } else {
                loadingDialog.dismiss();
            }
        } else {
            loadingDialog.dismiss();

        }

        if (DBquries.myRatedIds.contains(productID)) {
            int index = DBquries.myRatedIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(DBquries.myRating.get(index))) - 1;
            setRating(initialRating);
        }
        if (DBquries.cartList.contains(productID)) {
            ALREADY_ADDED_TO_CART = true;
        } else {
            ALREADY_ADDED_TO_CART = false;
        }


        if (DBquries.wishList.contains(productID)) {
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.Red));
        } else {
            addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
            ALREADY_ADDED_TO_WISHLIST = false;

        }
    }

    public static void showDialogRecyclerView() {
        if (couponsRecyclerView.getVisibility() == View.GONE) {
            couponsRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);

        } else {
            couponsRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
    }

    public static void setRating(int starPosition) {

        for (int x = 0; x < rateNowcontainer.getChildCount(); x++) {
            ImageView starBtn = (ImageView) rateNowcontainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= starPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }

    }


    private String calculateAvrageRating(long currentuserRating, boolean update) {

        double totalstars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView ratingNo = (TextView) ratingsNoContainer.getChildAt(5 - x);
            totalstars = totalstars + (Long.parseLong(ratingNo.getText().toString()) * x);
        }
        totalstars = totalstars + currentuserRating;
        if (update) {
            return String.valueOf(totalstars / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0, 3);
        } else {
            return String.valueOf(totalstars / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1)).substring(0, 3);
        }
    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        cartItem = menu.findItem(R.id.main_cart_icon);
        if (DBquries.cartList.size() > 0){
            cartItem.setActionView(R.layout.badge_layout);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
            TextView badegeCount = cartItem.getActionView().findViewById(R.id.badge_count);
            if (DBquries.cartList.size() < 99) {
                badegeCount.setText(String.valueOf(DBquries.cartList.size()));
            }else{
                badegeCount.setText("99");
            }
            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        Intent cartIntent = new Intent(Product_Details_Activity.this, MainActivity.class);
                        showCart = true;
                        startActivity(cartIntent);

                    }
                }
            });
        }else {
            cartItem.setActionView(null);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_Search_icon) {
            //todo: notification
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent(Product_Details_Activity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
