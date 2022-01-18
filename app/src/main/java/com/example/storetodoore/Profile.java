package com.example.storetodoore;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.sql.Time;


public class Profile extends AppCompatActivity {
private String userID;
FirebaseUser user;
DatabaseReference reference;
Button edprofile;
ImageButton back;
ProgressBar pro;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        edprofile =findViewById(R.id.btn_editprofile);
        back =findViewById(R.id.btnback);
        pro = findViewById(R.id.progress);




        final TextView nameEditText = (TextView) findViewById(R.id.fullName);
        final TextView emailEditText = (TextView) findViewById(R.id.email);
        final TextView houseEditText = (TextView) findViewById(R.id.home);
        final TextView areaEditText = (TextView) findViewById(R.id.area);
        final TextView landmarkEditText = (TextView) findViewById(R.id.landmark);
        final TextView mobileEditText = (TextView) findViewById(R.id.mobile);

        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String fullname = userProfile.fullname;
                    String email = userProfile.email;
                    String house = userProfile.house;
                    String area = userProfile.area;
                    String landmark = userProfile.landmark;
                    String mobile = userProfile.mobile;

                    nameEditText.setText(fullname);
                    emailEditText.setText(email);
                    houseEditText.setText(house);
                    areaEditText.setText(area);
                    landmarkEditText.setText(landmark);
                    mobileEditText.setText(mobile);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"Something Wrong happened",Toast.LENGTH_LONG).show();

            }
        });
        edprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String home = houseEditText.getText().toString();
                String Area = areaEditText.getText().toString();
                String Landmark = landmarkEditText.getText().toString();
                String Mobile = mobileEditText.getText().toString();

                Intent intent = new Intent(Profile.this,Update.class);
                intent.putExtra("keyname",name);
                intent.putExtra("keyemail",email);
                intent.putExtra("keyhome",home);
                intent.putExtra("keyarea",Area);
                intent.putExtra("keyland",Landmark);
                intent.putExtra("keymobile",Mobile);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,Dashboard.class));
            }
        });
    }
}









