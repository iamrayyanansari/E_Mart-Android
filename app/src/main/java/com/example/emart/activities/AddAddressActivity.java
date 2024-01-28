package com.example.emart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import the correct Toolbar class

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    EditText name, address, city, postalCode, phoneNumber;
    Toolbar toolbar;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Button addAddressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);



        toolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore =FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        name=findViewById(R.id.ad_Name);
        address=findViewById(R.id.ad_Address);
        city=findViewById(R.id.ad_City);
        postalCode=findViewById(R.id.ad_code);
        phoneNumber=findViewById(R.id.ad_phone);
        addAddressBtn = findViewById(R.id.ad_add_address);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userAddress = address.getText().toString();
                String userCity = city.getText().toString();
                String userPostalCode = postalCode.getText().toString();
                String userPhoneNumber = phoneNumber.getText().toString();
                String finalAddress= "";
                if(!userName.isEmpty()){
                    finalAddress+=userName;
                }
                if(!userAddress.isEmpty()){
                    finalAddress+=userAddress;
                }if(!userCity.isEmpty()){
                    finalAddress+=userCity;
                }if(!userPostalCode .isEmpty()){
                    finalAddress+=userPostalCode ;
                }if(!userPhoneNumber.isEmpty()){
                    finalAddress+=userPhoneNumber;
                }
                if(!userName.isEmpty() &&!userAddress.isEmpty() &&!userCity.isEmpty() &&!userPostalCode.isEmpty() &&!userPhoneNumber.isEmpty() ){
                    Map<String,String> map = new HashMap<>();
                    map.put("userAddress",finalAddress);
                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddAddressActivity.this, "Address added successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(AddAddressActivity.this, "Address not added!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
