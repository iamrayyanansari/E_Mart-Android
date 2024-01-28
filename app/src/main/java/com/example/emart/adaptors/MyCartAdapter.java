package com.example.emart.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emart.R;

import org.w3c.dom.Text;

import java.util.List;

import models.MyCartModel;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> list;
    int totalAmount=0;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCartModel currentItem = list.get(position);
        if (currentItem != null) {
            holder.date.setText(currentItem.getCurrentDate());
            holder.time.setText(currentItem.getCurrentTime());
            holder.price.setText(currentItem.getProductPrice() + " $");
            holder.name.setText(currentItem.getProductName());
            holder.totalPrice.setText(String.valueOf(currentItem.getTotalPrice()));
            holder.totalQuantity.setText(currentItem.getTotalQuantity());

            totalAmount = totalAmount  + list.get(position).getTotalPrice();
            Intent intent = new Intent("MyTotalAmount");
            intent.putExtra("totalAmount",totalAmount);

            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,time,price,name,totalQuantity,totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            date=itemView.findViewById(R.id.current_date);
            time=itemView.findViewById(R.id.current_time);
            totalQuantity=itemView.findViewById(R.id.total_quantity);
            totalPrice=itemView.findViewById(R.id.total_price);
        }
    }
}
