package com.example.emart.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.emart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.emart.databinding.ActivityRegistrationBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private boolean hasUpperAndNumber(String password) {
        Pattern pattern = Pattern.compile("(?=.*[A-Z])(?=.*\\d)");
        Matcher matcher = pattern.matcher(password);
       return matcher.find();
    }
    EditText name, email, password;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    private AppBarConfiguration appBarConfiguration;
    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();

        }
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        sharedPreferences=getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
        boolean isFirstTime=sharedPreferences.getBoolean("firstTime",true);
        if(isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent = new Intent(RegistrationActivity.this,OnBoardingActivity.class);
            startActivity(intent);
            finish();

        }
    }

    public void signup(View view) {
        String userName=name.getText().toString();
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();
//        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Enter the username ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Enter the email ", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!userEmail.contains("@") && !userEmail.contains(".com")){
            Toast.makeText(this, "invalid username ! ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<8 && !hasUpperAndNumber( userPassword)){
            Toast.makeText(this, "Require 8 digit,uppercase letter & nmuerical digit", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                }else{
                    Toast.makeText(RegistrationActivity.this, "UnSuccessfull registered"+task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void signin(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

    }


}