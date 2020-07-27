package com.example.nirma_canteen_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class StaffActivity extends AppCompatActivity {
    Button editmenubtn,historybtn,pendingordersbtn,availableitembtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        pendingordersbtn = findViewById(R.id.pendingordersbtn);
        editmenubtn = findViewById(R.id.editmenubtn);

        editmenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editmenu = new Intent(StaffActivity.this , EditMenuActivity.class);
                startActivity(editmenu);
            }
        });
        pendingordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendingorders = new Intent(StaffActivity.this , PendingOrderActivity.class);
                startActivity(pendingorders);
            }
        });

    }
}