package com.example.nirma_canteen_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

public class CartRecViewAdapter extends RecyclerView.Adapter<CartRecViewAdapter.ViewHolder> {
    ArrayList<OrderItem> foodItems = new ArrayList<>();
    private Context context;
    public CartRecViewAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public CartRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_list, parent, false);
        CartRecViewAdapter.ViewHolder holder = new CartRecViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartRecViewAdapter.ViewHolder holder, final int position) {
        holder.fooditemname.setText(foodItems.get(position).getName());
        holder.price.setText("Price: " + Integer.toString(foodItems.get(position).getPrice()));
        holder.quantity.setText("Quantity: " + Integer.toString(foodItems.get(position).getQuantity()));
        Glide.with(context).asBitmap().load(foodItems.get(position).getImageurl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }


    public void setFoodItems(ArrayList<OrderItem> foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fooditemname , price, quantity;
        private CardView fooditemlayout;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fooditemname = itemView.findViewById(R.id.fooditemname);
            fooditemlayout = itemView.findViewById(R.id.fooditemlayout);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.foodimage);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
