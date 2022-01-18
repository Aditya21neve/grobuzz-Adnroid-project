package com.example.storetodoore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    ImageButton cart;


private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav= (NavigationView)findViewById(R.id.navmenu);
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer);

        toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        cart =findViewById(R.id.ibtnshoppingcart);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_profile :
                        Toast.makeText(getApplicationContext(),"profile is Open",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Dashboard.this,Profile.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_Order :
                        Toast.makeText(getApplicationContext(),"your order is Open",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_List :
                        Toast.makeText(getApplicationContext(),"Your list is Open",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_Share :
                        Toast.makeText(getApplicationContext(),"App share is Open",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_Shop_Owner :
                        Toast.makeText(getApplicationContext(),"Only for shop owner",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.menu_rate :
                        Toast.makeText(getApplicationContext(),"Please rate us ",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_feed :
                        Toast.makeText(getApplicationContext(),"Feedback",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Dashboard.this,Feedback.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_setting :
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(Dashboard.this,Setting.class));
                        break;
                }
                return true;

            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this,"You Click on Cart",Toast.LENGTH_LONG).show();
            }
        });
        //frameLayout = findViewById(R.id.main_frameLayout);
        //setFragement(new homeFragment());

    }
    /*private void setFragement(Fragment fragement){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragement);
        fragmentTransaction.commit();


    }

     */
}
