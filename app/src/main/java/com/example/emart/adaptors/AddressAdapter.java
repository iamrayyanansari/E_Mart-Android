package com.example.emart.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emart.R;

import java.util.List;

import models.AddressModel;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    List<AddressModel> addressModelList;
    SelectedAddress selectedAddress;
    private RadioButton selectedRadiobtn;

    public AddressAdapter(Context context, List<AddressModel> addressModelList, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.address.setText(addressModelList.get(position).getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(AddressModel address:addressModelList){
                    address.setSelected(false);
                }
                addressModelList.get(position).setSelected(true);

                if(selectedRadiobtn != null){
                    selectedRadiobtn.setChecked(false);
                }
                selectedRadiobtn = (RadioButton) v;
                selectedRadiobtn.setChecked(true);
                selectedAddress.setAddress(addressModelList.get(position).getUserAddress());
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {

        TextView address;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress{
        void setAddress(String address);
    }
}
