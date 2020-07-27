package com.example.nirma_canteen_management;

import android.content.Context;
import android.media.Image;
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

import java.util.ArrayList;

public class FoodRecViewAdapter extends RecyclerView.Adapter<FoodRecViewAdapter.ViewHolder> {
    ArrayList<FoodItem> foodItems = new ArrayList<>();
    private Context context;

    public FoodRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.fooditemname.setText(foodItems.get(position).getName());
        holder.price.setText(Integer.toString(foodItems.get(position).getPrice()));
        holder.fooditemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, foodItems.get(position).getName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(context).asBitmap().load(foodItems.get(position).getImageurl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public void setFoodItems(ArrayList<FoodItem> foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fooditemname , price;
        private CardView fooditemlayout;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fooditemname = itemView.findViewById(R.id.fooditemname);
            fooditemlayout = itemView.findViewById(R.id.fooditemlayout);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.foodimage);
        }
    }
}
