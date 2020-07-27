package com.example.nirma_canteen_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditMenuActivity extends AppCompatActivity {
    FloatingActionButton additembtn;
    ArrayList<FoodItem> fooditems=new ArrayList<>();
    private RecyclerView foodrecview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        foodrecview=findViewById(R.id.foodrecview);


        DatabaseReference foodref = FirebaseDatabase.getInstance().getReference().child("Menu");
        final FoodRecViewAdapter adapter = new FoodRecViewAdapter(this);
        adapter.setFoodItems(fooditems);
        foodrecview.setAdapter(adapter);
        foodrecview.setLayoutManager(new LinearLayoutManager(this));

        foodref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fooditems.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    FoodItem f = ds.getValue(FoodItem.class);
                    fooditems.add(f);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        additembtn = findViewById(R.id.additembtn);
        additembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoadditem = new Intent(EditMenuActivity.this,AddItemActivity.class);
                startActivity(gotoadditem);
            }
        });
    }
}