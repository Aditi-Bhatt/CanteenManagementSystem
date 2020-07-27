package com.example.nirma_canteen_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ShowDetailsAdapter extends RecyclerView.Adapter<ShowDetailsAdapter.ViewHolder>{

    private ArrayList<OrderItem> foodItems = new ArrayList<>();
    private Context context;
    public ShowDetailsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_detail_list, parent, false);
        ShowDetailsAdapter.ViewHolder holder = new ShowDetailsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fooditemname.setText(foodItems.get(position).getName());
        holder.quantity.setText("Quantity: " + Integer.toString(foodItems.get(position).getQuantity()));
        Glide.with(context).asBitmap().load(foodItems.get(position).getImageurl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.foodItems = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fooditemname , quantity;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fooditemname = itemView.findViewById(R.id.fooditemname);
            image = itemView.findViewById(R.id.foodimage);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
