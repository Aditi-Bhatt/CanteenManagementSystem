package com.example.nirma_canteen_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    FloatingActionButton cartbtn;
    ArrayList<OrderItem> fooditems=new ArrayList<>();
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        RecyclerView menurecview = findViewById(R.id.menurecview);
        auth=FirebaseAuth.getInstance();
        DatabaseReference foodref = FirebaseDatabase.getInstance().getReference().child("Menu");
        final MenuRecViewAdapter adapter = new MenuRecViewAdapter(this);
        adapter.setFoodItems(fooditems);
        menurecview.setAdapter(adapter);

        foodref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fooditems.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    FoodItem food = ds.getValue(FoodItem.class);
                    assert food != null;
                    OrderItem f = new OrderItem(food.getName(),food.getImageurl(),food.getPrice(),0);
                    fooditems.add(f);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final DatabaseReference usercartlistreff = FirebaseDatabase.getInstance().getReference().child("OrderList").child("UserCartList");
        final FirebaseUser currentUser = auth.getCurrentUser();

        usercartlistreff.child(Objects.requireNonNull(currentUser.getDisplayName())).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(UserActivity.this, "Now cart is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        menurecview.setLayoutManager(new LinearLayoutManager(this));

        cartbtn = findViewById(R.id.cartbtn);
        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(OrderItem orders : fooditems)
                {
                    if(orders.getQuantity() > 0){
                        String foodid = orders.getPrice()+orders.getName().replaceAll("\\s", "");
                        usercartlistreff.child(Objects.requireNonNull(currentUser.getDisplayName())).child(foodid).setValue(orders);
                    }
                }

                startActivity(new Intent(UserActivity.this, CartActivity.class));
            }
        });
    }
}