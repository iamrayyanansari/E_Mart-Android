package com.example.emart.activities;

import android.content.Intent;
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

import com.example.emart.databinding.ActivityLoginBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private boolean hasUpperAndNumber(String password) {
        Pattern pattern = Pattern.compile("(?=.*[A-Z])(?=.*\\d)");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    EditText  email, password;
    private FirebaseAuth auth;
    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);


    }
    public void signIn(View view){
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();
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
        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(LoginActivity.this, new 
                OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successfully\uD83D\uDE03", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    
                    
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void signUp(View view){
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }

}