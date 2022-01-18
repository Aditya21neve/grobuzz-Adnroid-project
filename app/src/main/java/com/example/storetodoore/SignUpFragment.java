package com.example.storetodoore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout prantFrameLayoout;

    private EditText email;
    private EditText fullname;
    private EditText password;
    private EditText confirmPassword;
    private ImageButton closeBtn;
    private Button signUPBtn;
    private ProgressBar progressBar;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public static boolean disableCloseBtn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        alreadyHaveAnAccount = view.findViewById(R.id.already_have_an_account);
        email = view.findViewById(R.id.SignUp_email);
        fullname = view.findViewById(R.id.signUpFullName);
        password = view.findViewById(R.id.signUp_password);
        prantFrameLayoout = getActivity().findViewById(R.id.register_Freaelayout);
        confirmPassword = view.findViewById(R.id.signUpConfPassword);
        closeBtn = view.findViewById(R.id.sign_up_close_Btn);
        signUPBtn = view.findViewById(R.id.SignUp1_btn);
        progressBar = view.findViewById(R.id.sign_Up_progressBar);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        if (disableCloseBtn) {
            closeBtn.setVisibility(View.GONE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragement(new SignInFragment());
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cheeckInputes();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cheeckInputes();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cheeckInputes();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cheeckInputes();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signUPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailandPassword();
            }
        });
    }


    private void setFragement(Fragment fragement) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_form_left, R.anim.slide_out_from_right);
        fragmentTransaction.replace(prantFrameLayoout.getId(), fragement);
        fragmentTransaction.commit();
    }


    private void cheeckInputes() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(fullname.getText())) {
                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmPassword.getText())) {
                        signUPBtn.setEnabled(true);
                        signUPBtn.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        signUPBtn.setEnabled(false);
                        signUPBtn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    signUPBtn.setEnabled(false);
                    signUPBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                signUPBtn.setEnabled(false);
                signUPBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signUPBtn.setEnabled(false);
            signUPBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkEmailandPassword() {
        Drawable customErrorIcon = getResources().getDrawable(R.drawable.error);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
        if (email.getText().toString().matches(emailpattern)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                progressBar.setVisibility(View.VISIBLE);
                signUPBtn.setEnabled(false);
                signUPBtn.setTextColor(Color.argb(50, 255, 255, 255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Map<String, Object> userdata = new HashMap<>();
                                    userdata.put("fullname", fullname.getText().toString());

                                    firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                            .set(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        CollectionReference userDataRefrence = firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

                                                        ///////Mapes

                                                        Map<String, Object> wishlistMap = new HashMap<>();
                                                        wishlistMap.put("list_size", (long) 0);

                                                        Map<String, Object> ratingsMap = new HashMap<>();
                                                        ratingsMap.put("list_size", (long) 0);

                                                        Map<String, Object> cartMap = new HashMap<>();
                                                        cartMap.put("list_size", (long) 0);

                                                        ///////Mapes
                                                        List<String> documentNames = new ArrayList<>();
                                                        documentNames.add("MY_WISHLIST");
                                                        documentNames.add("MY_RATINGS");
                                                        documentNames.add("MY_CART");

                                                        List<Map<String, Object>> documenetFields = new ArrayList<>();
                                                        documenetFields.add(wishlistMap);
                                                        documenetFields.add(ratingsMap);
                                                        documenetFields.add(cartMap);


                                                        for (int x = 0; x < documentNames.size(); x++) {
                                                            int finalX = x;
                                                            userDataRefrence.document(documentNames.get(x))
                                                                    .set(documenetFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        if (finalX == documentNames.size() - 1) {
                                                                            mainIntent();
                                                                        }
                                                                    } else {
                                                                        progressBar.setVisibility(View.INVISIBLE);
                                                                        signUPBtn.setEnabled(true);
                                                                        signUPBtn.setTextColor(Color.rgb(255, 255, 255));
                                                                        String error = task.getException().getMessage();
                                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signUPBtn.setEnabled(true);
                                    signUPBtn.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                confirmPassword.setError("Password doesm't match!", customErrorIcon);
            }
        } else {
            email.setError("Invalid Email", customErrorIcon);
        }
    }

    private void mainIntent() {
        if (disableCloseBtn) {
            disableCloseBtn = false;

        } else {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();

    }
}