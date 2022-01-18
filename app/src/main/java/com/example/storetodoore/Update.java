package com.example.storetodoore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Update extends AppCompatActivity {
    EditText upname,uphouse,uparea,uplandmark;
    Button update;
    TextView upemail,upphone;
    private String userID;
    FirebaseUser user;
    DatabaseReference reference;
    ImageButton btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        upname = findViewById(R.id.upfullName);
        upemail = findViewById(R.id.upEmail);
        uphouse = findViewById(R.id.uphome);
        uparea = findViewById(R.id.uparea);
        uplandmark = findViewById(R.id.uplandmark);
        upphone = findViewById(R.id.upPhoneNumber);
        update = findViewById(R.id.BtnUpdate);
        btnback= findViewById(R.id.btnupback);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        String name = getIntent().getStringExtra("keyname");
        String email = getIntent().getStringExtra("keyemail");
        String house = getIntent().getStringExtra("keyhome");
        String area = getIntent().getStringExtra("keyarea");
        String landmark = getIntent().getStringExtra("keyland");
        String phone = getIntent().getStringExtra("keymobile");


        upname.setText(name);
        upemail.setText(email);
        uphouse.setText(house);
        uparea.setText(area);
        uplandmark.setText(landmark);
        upphone.setText(phone);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = upname.getText().toString().trim();
                String Home = uphouse.getText().toString().trim();
                String Area = uparea.getText().toString().trim();
                String Landmark = uplandmark.getText().toString().trim();
                if (TextUtils.isEmpty(Name)){
                    upname.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(Home)){
                    uphouse.setError("House no. / Plat no. /Plot no. is required");
                    return;
                }
                if (TextUtils.isEmpty(Area)){
                    uparea.setError("Area is required");
                    return;
                }
                if (TextUtils.isEmpty(Landmark)){
                    uplandmark.setError("Landmark is required");
                    return;
                }


                Map<String, Object>map = new HashMap<>();
                map.put("fullname",upname.getText().toString());
                map.put("email",upemail.getText().toString());
                map.put("house",uphouse.getText().toString());
                map.put("area",uparea.getText().toString());
                map.put("landmark",uplandmark.getText().toString());
                map.put("mobile",upphone.getText().toString());
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(userID)
                        .updateChildren(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(Update.this,Profile.class));
                           Toast.makeText(Update.this,"Update Successfull",Toast.LENGTH_LONG).show();

                            }



                        });
                            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Update.this,Profile.class));
            }
        });
    }
}