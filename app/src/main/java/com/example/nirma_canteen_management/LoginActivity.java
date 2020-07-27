package com.example.nirma_canteen_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button loginbtn , registerbtn;
    private EditText emailbox , passwordbox;
    private TextView warnemailorpassword;
    private Spinner usertype;
    private FirebaseAuth auth;
    private DatabaseReference reff;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference();
        initlogin();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatedata()){
                    warnemailorpassword.setVisibility(View.GONE);
                    String emailid = emailbox.getText().toString(),
                            password = passwordbox.getText().toString();

                    loginUser(emailid,password);

                }else{
                    warnemailorpassword.setText("Email / Password is not correct");
                    warnemailorpassword.setVisibility(View.VISIBLE);
                }
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customerregister = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(customerregister);
            }
        });

    }

    private void loginUser(String emailid, String password) {
        auth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String utype = usertype.getSelectedItem().toString();
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    assert currentUser != null;
                    String RegisteredUserID = currentUser.getUid();

                    if(utype.equals("Student / Faculty")){
                        reff = FirebaseDatabase.getInstance().getReference().child("users").child(RegisteredUserID);
                        reff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    // send to student activity.
                                    final FirebaseUser user = auth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(usernameFromEmail(user.getEmail())).build();
                                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(LoginActivity.this, "Username: "+ user.getDisplayName(), Toast.LENGTH_SHORT).show();
//                                                Toast.makeText(LoginActivity.this, "getEmail(): "+ user.getEmail(), Toast.LENGTH_SHORT).show();
//                                                Toast.makeText(LoginActivity.this, "user.getPhoneNumber(): "+ user.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                                    startActivity(new Intent(LoginActivity.this, UserActivity.class));

                                }else {
                                    Toast.makeText(LoginActivity.this, "User type is invalid", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else if(utype.equals("Canteen staff")){

                        reff = FirebaseDatabase.getInstance().getReference().child("staff").child(RegisteredUserID);
                        reff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    Intent staffpage = new Intent(LoginActivity.this , StaffActivity.class);
                                    startActivity(staffpage);
                                    Toast.makeText(LoginActivity.this, "sending to staff activity", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "User type is invalid", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        startActivity(new Intent(LoginActivity.this , AdminActivity.class));
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Try again!", Toast.LENGTH_SHORT).show();
                    emailbox.setText("");
                    passwordbox.setText("");
                }
            }
        });
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void initlogin(){
        emailbox = findViewById(R.id.emailbox);
        passwordbox = findViewById(R.id.passwordbox);
        loginbtn = findViewById(R.id.loginbtn);
        registerbtn = findViewById(R.id.registerbtn);
        warnemailorpassword = findViewById(R.id.warnemailorpassword);
        usertype = findViewById(R.id.usertype);
    }
    private boolean validatedata(){
        if(emailbox.getText().toString().isEmpty()) {
            return false;
        }else if (!emailbox.getText().toString().trim().matches(emailPattern)) {
                return false;
        }
        return !passwordbox.getText().toString().isEmpty();
    }
}