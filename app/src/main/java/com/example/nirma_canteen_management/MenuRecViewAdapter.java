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

public class MenuRecViewAdapter extends RecyclerView.Adapter<MenuRecViewAdapter.ViewHolder> {
    ArrayList<OrderItem> foodItems = new ArrayList<OrderItem>();
    private Context context;
    public MenuRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_list, parent, false);
        MenuRecViewAdapter.ViewHolder holder = new MenuRecViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.fooditemname.setText(foodItems.get(position).getName());
        holder.price.setText(Integer.toString(foodItems.get(position).getPrice()));
        holder.fooditemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, foodItems.get(position).getName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(context).asBitmap().load(foodItems.get(position).getImageurl()).into(holder.image);
        holder.quabtn.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = holder.quabtn.getNumber();
                foodItems.get(position).setQuantity(Integer.parseInt(num));
            }
        });
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
        private TextView fooditemname , price;
        private CardView fooditemlayout;
        private ImageView image;
        private ElegantNumberButton quabtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fooditemname = itemView.findViewById(R.id.fooditemname);
            fooditemlayout = itemView.findViewById(R.id.fooditemlayout);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.foodimage);
            quabtn = itemView.findViewById(R.id.quabtn);
        }
    }
}
