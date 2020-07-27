package com.example.nirma_canteen_management;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.nirma_canteen_management.Interface.ItemClickListner;
import com.google.firebase.database.annotations.NotNull;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView fooditemname , price;
    public CardView fooditemlayout;
    public ImageView image;
    public ElegantNumberButton quabtn;
    private ItemClickListner itemClickListner;



    public CartViewHolder (@NotNull View itemView){
        super(itemView);
        fooditemname = itemView.findViewById(R.id.fooditemname);
        price = itemView.findViewById(R.id.price);
        fooditemlayout = itemView.findViewById(R.id.fooditemlayout);
        image = itemView.findViewById(R.id.foodimage);
        quabtn = itemView.findViewById(R.id.quabtn);
    }
    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner = itemClickListner;
    }
}
