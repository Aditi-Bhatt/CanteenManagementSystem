package com.example.nirma_canteen_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddItemActivity extends AppCompatActivity {
    private Button addnewitembtn;
    EditText itemname , itemprice;
    DatabaseReference foodref;
    FoodItem newitem;
    String currid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        addnewitembtn = findViewById(R.id.addnewitembtn);

        itemname = findViewById(R.id.itemname);
        itemprice = findViewById(R.id.itemprice);
        foodref = FirebaseDatabase.getInstance().getReference().child("Menu");



        addnewitembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkdata()){
                    String foodname = itemname.getText().toString();
                    int foodprice = Integer.parseInt(itemprice.getText().toString().trim());
                    newitem = new FoodItem(foodname,foodprice,"https://media5.newsnationtv.com/images/2017/06/16/878609020-frenchfries.jpg");
                    currid = itemprice.getText().toString() + foodname.replaceAll("\\s", "");
                    foodref.child(String.valueOf(currid)).setValue(newitem);
                    itemname.setText("");
                    itemprice.setText("");

                    Toast.makeText(AddItemActivity.this, "Item is added successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddItemActivity.this, "Enter valid data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkdata(){
        return !(itemname.getText().toString().isEmpty() || itemprice.getText().toString().isEmpty());
    }
}