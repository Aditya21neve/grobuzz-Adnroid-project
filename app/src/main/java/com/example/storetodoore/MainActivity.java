package com.example.storetodoore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import static com.example.storetodoore.Register1.setSignUpFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDER_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARDS_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    public static Boolean showCart = false;
    public static  DrawerLayout drawer;

    private FrameLayout frameLayout;
    private ImageView noInternetconnection;
    private int currentFragment = -1;

    //private ImageView actionbarlogo;
    private LinearLayout actionbarlogo;
    private Toolbar toolbar;
    private Dialog signInDialog;
    private Window window;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        actionbarlogo = findViewById(R.id.actionbarlogo);
        setSupportActionBar(toolbar);


        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

       drawer= (DrawerLayout) findViewById(R.id.drawer_layout);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.main_framelayout);


        if (showCart) {

            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.open, R.string.close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }

        signInDialog = new Dialog(MainActivity.this);
        signInDialog.setContentView(R.layout.signin_dialog);
        signInDialog.setCancelable(true);

        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogSignInBtn = signInDialog.findViewById(R.id.SignIn_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.signUpbtn);
        Intent registerIntent = new Intent(MainActivity.this, Register1.class);
        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn = true;
                SignInFragment.disableCloseBtn = true;
                setSignUpFragment =false;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);

        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {
                if (showCart) {
                    showCart = false;
                    finish();

                } else {
                    actionbarlogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);

            MenuItem cartItem = menu.findItem(R.id.action_bar_cart);
            if (DBquries.cartList.size()> 0){
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
                         gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                     }
                 }
             });
            }else {
                cartItem.setActionView(null);
            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_Search_icon) {
            //todo: Search
            return true;
        } else if (id == R.id.main_noitification) {
            //todo: notification
            return true;
        } else if (id == R.id.action_bar_cart) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            }
            return true;

        } else if (id == android.R.id.home) {
            if (showCart) {
                showCart = false;
                finish();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }


    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        actionbarlogo.setVisibility(View.GONE);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (currentUser != null) {
            int id = item.getItemId();
            if (id == R.id.nav_shop) {

                actionbarlogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new HomeFragment(), HOME_FRAGMENT);

            } else if (id == R.id.nav_order) {
                setFragment(new MyOrederFragment(), ORDER_FRAGMENT);
            } else if (id == R.id.nav_order_list) {

            } else if (id == R.id.nav_cart) {
                gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            } else if (id == R.id.nav_whishlist) {
                gotoFragment("My Wishlist", new MyWishlistFragment(), WISHLIST_FRAGMENT);

            } else if (id == R.id.nav_reward) {
                gotoFragment("My Rewards", new My__Reward_Fragment(), REWARDS_FRAGMENT);

            } else if (id == R.id.nav_account) {
                gotoFragment("My Account", new My_Account_Fragment(), ACCOUNT_FRAGMENT);

            } else if (id == R.id.nav_signout) {
                FirebaseAuth.getInstance().signOut();
                DBquries.clearData();
                Intent refisterIntent = new Intent(MainActivity.this,Register1.class);
                startActivity(refisterIntent);
                finish();
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            drawer.closeDrawer(GravityCompat.START);
            signInDialog.show();
            return false;
        }

    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            if (fragmentNo == REWARDS_FRAGMENT) {
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));

            } else {
                window.setStatusBarColor(getResources().getColor(R.color.actionbarbule));
                toolbar.setBackgroundColor(getResources().getColor(R.color.actionbarbule));
            }
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.commit();
        }
    }


}
