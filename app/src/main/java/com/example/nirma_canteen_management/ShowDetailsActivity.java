package com.example.nirma_canteen_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowDetailsActivity extends AppCompatActivity {
    private RecyclerView orderdetailslayout;
    private String odrid;
    private TextView usernametxt;
    private  ArrayList<OrderItem> currOrder = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        orderdetailslayout = findViewById(R.id.orderdetailslayout);

        odrid = getIntent().getStringExtra("pid");

        Toast.makeText(this, odrid, Toast.LENGTH_SHORT).show();

        final ShowDetailsAdapter adapter = new ShowDetailsAdapter(this);
        adapter.setItems(currOrder);
        orderdetailslayout.setAdapter(adapter);
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderdetailslayout.setLayoutManager(lManager);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Current Orders");

        db.child(odrid).child("order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    currOrder.clear();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        OrderItem co = ds.getValue(OrderItem.class);
                        currOrder.add(co);
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


















