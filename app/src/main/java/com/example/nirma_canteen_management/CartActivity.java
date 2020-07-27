package com.example.nirma_canteen_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    private TextView totalamount;
    private Button placebtn;
    private RecyclerView cartrecview;
    ArrayList<OrderItem> selectedfood = new ArrayList<>();
    FirebaseAuth auth;
    private long time;


    @Override
    public void onBackPressed() {
        if(time+2000 > System.currentTimeMillis())
        {
            super.onBackPressed();
            FirebaseUser cUser = auth.getCurrentUser();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("OrderList").child("UserCartList");

            db.child(cUser.getDisplayName()).removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(CartActivity.this, "Cart is empty.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            return;
        }
        else
        {
            Toast.makeText(this, "Press once more to exit!", Toast.LENGTH_SHORT).show();
        }
        time = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        totalamount = findViewById(R.id.totalamount);
        auth = FirebaseAuth.getInstance();
        placebtn = findViewById(R.id.placebtn);
        cartrecview = findViewById(R.id.cartrecview);
        cartrecview.setHasFixedSize(true);
        cartrecview.setLayoutManager(new LinearLayoutManager(this));

        final CartRecViewAdapter adapter = new CartRecViewAdapter(this);
        adapter.setFoodItems(selectedfood);

        final FirebaseUser cUser = auth.getCurrentUser();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("OrderList").child("UserCartList");

        db.child(cUser.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    selectedfood.clear();
                    int total=0;
                    for(DataSnapshot ds : snapshot.getChildren())
                    {
                        OrderItem sItem = ds.getValue(OrderItem.class);
                        total = total + (sItem.getPrice() * sItem.getQuantity());
                        selectedfood.add(sItem);
                    }
                    totalamount.setText(Integer.toString(total));
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cartrecview.setAdapter(adapter);


        placebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("OrderList").child("UserCartList");
                DatabaseReference from = db.child(cUser.getDisplayName());



                from.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            ArrayList<OrderItem> alist = new ArrayList<>();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                OrderItem orditm = ds.getValue(OrderItem.class);
                                alist.add(orditm);
                            }
                            String cdate,ctime;
                            Calendar cal= Calendar.getInstance();
                            SimpleDateFormat currdate = new SimpleDateFormat("MM dd, yyyy");
                            cdate = currdate.format(cal.getTime());

                            SimpleDateFormat currtime = new SimpleDateFormat("HH:mm:ss a");
                            ctime = currtime.format(cal.getTime());

                            String currentTime = cdate + " " + ctime;
                            String uName = cUser.getDisplayName();

                            PlacedOrder place = new PlacedOrder(uName,currentTime,alist);

                            DatabaseReference to = FirebaseDatabase.getInstance().getReference().child("Current Orders");

                            to.child(uName + currentTime).setValue(place);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                

                from.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CartActivity.this, "Order is Successfully placed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CartActivity.this , UserActivity.class));
                        }
                    }
                });

            }
        });

    }
}