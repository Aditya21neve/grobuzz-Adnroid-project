package com.example.storetodoore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Set;

public class Setting extends AppCompatActivity {
    private String userID;
    DatabaseReference refuser,reffeedback;
    Button button,delete;
    FirebaseUser user;

    ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        button = findViewById(R.id.btn_logout);
        delete = findViewById(R.id.btn_delete);
        back = findViewById(R.id.btnback);
        refuser = FirebaseDatabase.getInstance().getReference("Users");
        reffeedback = FirebaseDatabase.getInstance().getReference("Feedback");


        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting.this,Dashboard.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog  = new AlertDialog.Builder(Setting.this);
                dialog.setTitle("Are You Sure ?");
                dialog.setMessage("Delete Account will result in completely removing Your Account from the System and you won't be able to access this app."
                        +           "                                              THANK YOU FOR USING THE APP");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            refuser.child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                }
                                            });
                                            reffeedback.child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                            Toast.makeText(Setting.this,"User account deleted",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(Setting.this,Login.class));

                                        }else {
                                            Toast.makeText(Setting.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog =dialog.create();
                alertDialog.show();
            }
        });
    }
    public void logout (View view){
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(Setting.this,Login.class));
        finish();


    }
}