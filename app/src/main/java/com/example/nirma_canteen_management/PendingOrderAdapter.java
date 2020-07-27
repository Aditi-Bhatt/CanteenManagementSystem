package com.example.nirma_canteen_management;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.ViewHolder> {
    private ArrayList<String> odrIdlist = new ArrayList<>();
    private Context context;
    public PendingOrderAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_item_list, parent, false);
        PendingOrderAdapter.ViewHolder holder = new PendingOrderAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.orderId.setText(odrIdlist.get(position));
        holder.orderitemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDetails = new Intent(context , ShowDetailsActivity.class);
                showDetails.putExtra("pid", odrIdlist.get(position));
                context.startActivity(showDetails);
            }
        });
        // onclick listener for donebtn
        final String odrid = odrIdlist.get(position);
        holder.completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Current Orders");

                db.child(odrid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, odrid+" has been completed!", Toast.LENGTH_SHORT).show();
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Current Orders");
                            db.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if(snapshot.exists()){
                                        odrIdlist.clear();
                                        for(DataSnapshot ds : snapshot.getChildren()){
                                            PlacedOrder pItem = ds.getValue(PlacedOrder.class);
                                            odrIdlist.add(pItem.getUsername()+pItem.getTime());
                                        }
                                        for(String p : odrIdlist){
                                            odrIdlist.add(p);
                                        }
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                });
            }
        });

    }

    public void setOdrIdlist(ArrayList<String> odrIdlist) {
        this.odrIdlist = odrIdlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return odrIdlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView orderId;
        private CardView orderitemlayout;
        private Button completebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            orderitemlayout = itemView.findViewById(R.id.orderitemlayout);
            completebtn = itemView.findViewById(R.id.completebtn);
        }
    }
}
