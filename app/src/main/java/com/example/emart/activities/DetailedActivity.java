package com.example.emart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.emart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import models.NewProductsModel;
import models.PopularProductsModel;
import models.ShowAllModel;

public class DetailedActivity extends AppCompatActivity {

    ImageView  detailedImg;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;
    Toolbar toolbar;
    int totalQuantity = 1;
    int totalPrice=0;
    NewProductsModel newProductsModel = null;
    PopularProductsModel popularProductsModel = null;
    //Show all
    ShowAllModel showAllModel=null;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firestore = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();


        final Object obj = getIntent().getSerializableExtra("detailed");

        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if (obj instanceof  PopularProductsModel) {
            popularProductsModel = (PopularProductsModel) obj;
        }else if (obj instanceof  ShowAllModel)
        {
            showAllModel = (ShowAllModel) obj;
    }

        detailedImg= findViewById(R.id.detailed_img);
        quantity= findViewById(R.id.quantity);

        name= findViewById(R.id.detailed_name);
        rating= findViewById(R.id.rating);
        description= findViewById(R.id.detailed_desc);
        price= findViewById(R.id.detailed_price);
        addToCart= findViewById(R.id.add_to_cart);
        buyNow= findViewById(R.id.buy_now);
        addItems= findViewById(R.id.add_item);
        removeItems= findViewById(R.id.remove_item);
//Show new products

        if(newProductsModel != null ){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(String.valueOf(newProductsModel.getDescription()));
            price.setText(String.valueOf(newProductsModel.getPrice()));
//            name.setText(newProductsModel.getName());
            totalPrice=newProductsModel.getPrice() * totalQuantity;

        }
//Show popular products

        if(popularProductsModel != null ){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(String.valueOf(popularProductsModel.getDescription()));
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            totalPrice=popularProductsModel.getPrice() * totalQuantity;

//            name.setText(popularProductsModel.getName());

        }
//Show all products
        if(showAllModel != null ){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(String.valueOf(showAllModel.getDescription()));
            price.setText(String.valueOf(showAllModel.getPrice()));
            totalPrice=showAllModel.getPrice() * totalQuantity;

//            name.setText(popularProductsModel.getName());

        }
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailedActivity.this,AddressActivity.class));
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }

            private void addToCart() {
                String saveCurrentTime, saveCurrentDate;

                Calendar calForData = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyyy");
                saveCurrentDate = currentDate.format(calForData.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForData.getTime());

                // Store relevant data in HashMap
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", name.getText().toString());
                cartMap.put("productPrice", price.getText().toString());
                cartMap.put("currentTime", saveCurrentTime);  // Store formatted time
                cartMap.put("currentDate", saveCurrentDate);
                cartMap.put("totalQuantity", quantity.getText().toString());

                // Calculate totalPrice after updating the quantity
                int productPrice = Integer.parseInt(price.getText().toString());
                totalPrice = productPrice * totalQuantity;
                cartMap.put("totalPrice", totalPrice);

                // Logging the data to be added
                Log.d("Firestore", "Adding to cart - productName: " + name.getText().toString() +
                        ", productPrice: " + price.getText().toString() +
                        ", currentTime: " + saveCurrentTime +
                        ", currentDate: " + saveCurrentDate +
                        ", totalQuantity: " + quantity.getText().toString() +
                        ", totalPrice: " + totalPrice);

                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User").add(cartMap)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DetailedActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(DetailedActivity.this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                                    Log.e("Firestore", "Error adding to cart", task.getException());
                                }
                            }
                        });
            }

        });
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                     if(newProductsModel != null){
                         totalPrice=newProductsModel.getPrice() * totalQuantity;
                     }
                     if(popularProductsModel != null){
                         totalPrice=popularProductsModel.getPrice() * totalQuantity;
                     }

                    if(showAllModel != null){
                        totalPrice=showAllModel.getPrice() * totalQuantity;
                    }
                }
            }
        });
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));

                }
            }
        });


    }
}