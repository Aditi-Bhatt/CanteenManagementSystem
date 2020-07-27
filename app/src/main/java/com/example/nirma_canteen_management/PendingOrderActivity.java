package com.example.nirma_canteen_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingOrderActivity extends AppCompatActivity {
     RecyclerView placedorderlist;
     ArrayList<PlacedOrder> orderlist = new ArrayList<>();
    private ArrayList<String> orderId = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_order);

        placedorderlist = findViewById(R.id.placedorderlist);
        placedorderlist.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Current Orders");
        final PendingOrderAdapter adapter = new PendingOrderAdapter(this);
        adapter.setOdrIdlist(orderId);
        placedorderlist.setAdapter(adapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    orderlist.clear();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        PlacedOrder pItem = ds.getValue(PlacedOrder.class);
                        orderlist.add(pItem);
                    }
                    for(PlacedOrder p : orderlist){
                        orderId.add(p.getUsername()+p.getTime());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

