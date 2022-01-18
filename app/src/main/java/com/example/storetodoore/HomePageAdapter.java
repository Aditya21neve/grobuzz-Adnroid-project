package com.example.storetodoore;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPosition = -1;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannersliderview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_ad_layout, viewGroup, false);
                return new BannerSliderViewholder(bannersliderview);
            case HomePageModel.STRIP_AD_BANNER:
                View stripadview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_ad_layout, viewGroup, false);
                return new striAdBannerViewHolder(stripadview);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout, viewGroup, false);
                return new HorizontalProductViewHolder(horizontalProductview);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridproductview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup, false);
                return new GridProductViewHolder(gridproductview);


            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewholder) viewHolder).setBannersliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                String resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackgroundColor();
                ((striAdBannerViewHolder) viewHolder).setStripAd(resource, color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String layoutcolor = homePageModelList.get(position).getBackgroundColor();
                String horizontallayouttitle = homePageModelList.get(position).getTitle();
                List<WishlistModel> viewAllProductList = homePageModelList.get(position).getViewAllProductList();
                List<HorizontalProduceScrollModel> horizontalProduceScrollModelList = homePageModelList.get(position).getHorizontalProduceScrollModelList();
                ((HorizontalProductViewHolder) viewHolder).setHorizontalProductLayout(horizontalProduceScrollModelList, horizontallayouttitle, layoutcolor, viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridlayoutcolor = homePageModelList.get(position).getBackgroundColor();
                String gridlayouttitle = homePageModelList.get(position).getTitle();
                List<HorizontalProduceScrollModel> gridProduceScrollModelList = homePageModelList.get(position).getHorizontalProduceScrollModelList();
                ((GridProductViewHolder) viewHolder).setGridProductLayout(gridProduceScrollModelList, gridlayouttitle, gridlayoutcolor);
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
        return homePageModelList.size();
    }

    public class BannerSliderViewholder extends RecyclerView.ViewHolder {
        private ViewPager BannersliderViewPager;
        private int currentPage;
        private Timer timer;
        final private long delaytime = 3000;
        final private long priodtime = 3000;
        private List<SliderModel> arrangedList;


        public BannerSliderViewholder(@NonNull View itemView) {
            super(itemView);
            BannersliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager);


        }


        private void setBannersliderViewPager(List<SliderModel> sliderModelList) {
            currentPage = 2;
            if (timer != null) {
                timer.cancel();
            }

            arrangedList = new ArrayList<>();
            for (int x = 0; x < sliderModelList.size(); x++) {
                arrangedList.add(x, sliderModelList.get(x));
            }
            arrangedList.add(0, sliderModelList.get(sliderModelList.size() - 2));
            arrangedList.add(1, sliderModelList.get(sliderModelList.size() - 1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));


            SliderAdpter sliderAdpter = new SliderAdpter(arrangedList);
            BannersliderViewPager.setAdapter(sliderAdpter);
            BannersliderViewPager.setClipToPadding(false);
            BannersliderViewPager.setPageMargin(20);

            BannersliderViewPager.setCurrentItem(currentPage);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        pageLopper(arrangedList);
                    }
                }
            };
            BannersliderViewPager.addOnPageChangeListener(onPageChangeListener);

            StartBannerslideShow(arrangedList);

            BannersliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLopper(arrangedList);
                    StopBannerslideshow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        StartBannerslideShow(arrangedList);
                    }
                    return false;
                }
            });
        }

        private void pageLopper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                BannersliderViewPager.setCurrentItem(currentPage, false);

            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                BannersliderViewPager.setCurrentItem(currentPage, false);

            }
        }

        private void StartBannerslideShow(List<SliderModel> sliderModelList) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    BannersliderViewPager.setCurrentItem(currentPage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);

                }
            }, delaytime, priodtime);
        }

        private void StopBannerslideshow() {
            timer.cancel();
        }

    }

    public class striAdBannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public striAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(String resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(stripAdImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private TextView horizontallayoutTitle;
        private Button horizontalviewallBtn;
        private RecyclerView horizontalReclerView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            horizontallayoutTitle = itemView.findViewById(R.id.h_s_product_title);
            horizontalviewallBtn = itemView.findViewById(R.id.horizontal_scrolllayout_Viewall);
            horizontalReclerView = itemView.findViewById(R.id.horizontal_scrolllayout_recyclerview);
            horizontalReclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalProductLayout(List<HorizontalProduceScrollModel> horizontalProduceScrollModelList, String title, String color, List<WishlistModel> viewAllProductList) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
//            horizontallayoutTitle.setText(title);
//            horizontallayoutTitle.setText(title);
            if (horizontalProduceScrollModelList.size() > 8) {
                horizontalviewallBtn.setVisibility(View.VISIBLE);
                horizontalviewallBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishlistModelList = viewAllProductList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code", 0);
                        viewAllIntent.putExtra("title", title);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            } else {
                horizontalviewallBtn.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProduceScrollModelList);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalReclerView.setLayoutManager(linearLayoutManager1);

            horizontalReclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private TextView gridLayoutTitle;
        private Button gridLayoutViewallBtn;
        private GridLayout gridProductLayout;


        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewallBtn = itemView.findViewById(R.id.grid_product_layout_viewall_btn);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);

        }

        private void setGridProductLayout(List<HorizontalProduceScrollModel> horizontalProduceScrollModelList, String title, String color) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);

            for (int x = 0; x < 4; x++) {
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_title);
                TextView productdescription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_price);

                Glide.with(itemView.getContext()).load(horizontalProduceScrollModelList.get(x).getProductimage()).apply(new RequestOptions().placeholder(R.drawable.fakeimage)).into(productImage);
                productTitle.setText(horizontalProduceScrollModelList.get(x).getProducttitile());
                productdescription.setText(horizontalProduceScrollModelList.get(x).getProductdescription());
                productPrice.setText("Rs." + horizontalProduceScrollModelList.get(x).getProductprice() + "/-");
                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));
                if (!title.equals("")) {
                    int finalX = x;
                    gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent productDetailsIntent = new Intent(itemView.getContext(), Product_Details_Activity.class);
                            productDetailsIntent.putExtra("PRODUCT_ID", horizontalProduceScrollModelList.get(finalX).getProductID());
                            itemView.getContext().startActivity(productDetailsIntent);
                        }
                    });
                }
            }

            if (!title.equals("")) {
                gridLayoutViewallBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.horizontalProduceScrollModelList = horizontalProduceScrollModelList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code", 1);
                        viewAllIntent.putExtra("title", title);
                        itemView.getContext().startActivity(viewAllIntent);

                    }
                });
            }


        }


    }


}