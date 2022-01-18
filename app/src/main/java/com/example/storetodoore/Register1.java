package com.example.storetodoore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Register1 extends AppCompatActivity {

    private FrameLayout frameLayout;
    private FirebaseAuth firebaseAuth;
    public static boolean onResetPasswordFragment = false;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        frameLayout = findViewById(R.id.register_Freaelayout);
        firebaseAuth = FirebaseAuth.getInstance();
        if (setSignUpFragment){
            setSignUpFragment = false;

            setDefaultFragment(new SignUpFragment());
        }else {
            setDefaultFragment(new SignInFragment());
        }
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            SignUpFragment.disableCloseBtn = false;
            SignInFragment.disableCloseBtn = false;
            if (onResetPasswordFragment){
                onResetPasswordFragment = false;
                setFragment(new SignInFragment());
                return false;
            }
        }
        return super.onKeyDown(keyCode,event);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
    private void setFragment(Fragment fragement) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_form_left, R.anim.slide_out_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragement);
        fragmentTransaction.commit();
    }

}