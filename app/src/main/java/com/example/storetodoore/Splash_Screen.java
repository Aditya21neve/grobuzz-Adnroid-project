package com.example.storetodoore;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Splash_Screen extends AppCompatActivity {
    //Hook

    TextView txt1,txt2,txt3;
    ImageView logo;

    //Animation
    Animation topAnimation, bottomAnimation, middle_Animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);
        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                    //Intent intent = new Intent(getApplicationContext(),Login.class);
                    Intent intent = new Intent(getApplicationContext(),Register1.class);
                    startActivity(intent);
                    finish();
                    super.run();

                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        timer.start();

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middle_Animation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        //hook


        txt1= findViewById(R.id.txt1);
        txt2= findViewById(R.id.txt2);
        txt3= findViewById(R.id.txt3);
        logo= findViewById(R.id.logo);

        //setting Animation


        logo.setAnimation(topAnimation);




        txt1.setAnimation(bottomAnimation);
        txt2.setAnimation(bottomAnimation);
        txt3.setAnimation(bottomAnimation);

    }
}