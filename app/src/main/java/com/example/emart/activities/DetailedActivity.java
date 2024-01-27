package com.example.emart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emart.R;
import com.google.firebase.firestore.FirebaseFirestore;

import models.NewProductsModel;
import models.PopularProductsModel;
import models.ShowAllModel;

public class DetailedActivity extends AppCompatActivity {

    ImageView  detailedImg;
    TextView rating,name,description,price;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;
    NewProductsModel newProductsModel = null;
    PopularProductsModel popularProductsModel = null;
    //Show all
    ShowAllModel showAllModel=null;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        firestore = FirebaseFirestore.getInstance();


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

        }
//Show popular products

        if(popularProductsModel != null ){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(String.valueOf(popularProductsModel.getDescription()));
            price.setText(String.valueOf(popularProductsModel.getPrice()));
//            name.setText(popularProductsModel.getName());

        }
//Show all products
        if(showAllModel != null ){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(String.valueOf(showAllModel.getDescription()));
            price.setText(String.valueOf(showAllModel.getPrice()));
//            name.setText(popularProductsModel.getName());

        }

    }
}