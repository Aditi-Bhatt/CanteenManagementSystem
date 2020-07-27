package com.example.nirma_canteen_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegStaffActivity extends AppCompatActivity {

    private Button registerbtn2;
    private TextView warnname,warnemail,warnpassword;
    private EditText registername,registeremail,registerpassword,registerpasswordrep;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth auth;
    private DatabaseReference staffreff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_staff);

        registerbtn2 = findViewById(R.id.registerbtn2);
        warnname = findViewById(R.id.warnname);
        warnemail = findViewById(R.id.warnemail);
        warnpassword = findViewById(R.id.warnpassword);
        registername = findViewById(R.id.registername);
        registeremail = findViewById(R.id.registeremail);
        registerpassword = findViewById(R.id.registerpassword);
        registerpasswordrep = findViewById(R.id.registerpasswordrep);

        auth = FirebaseAuth.getInstance();
        staffreff = FirebaseDatabase.getInstance().getReference();

        registerbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateregdata()){
                    warnname.setVisibility(View.GONE);
                    warnemail.setVisibility(View.GONE);
                    warnpassword.setVisibility(View.GONE);

                    String emailid = registeremail.getText().toString(),
                            password = registerpassword.getText().toString();

                    createUser(emailid,password);

                }else{
                    Toast.makeText(RegStaffActivity.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser(String emailid, String password) {
        auth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RegStaffActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    if (auth.getCurrentUser() != null) {
                        onAuthSuccess(Objects.requireNonNull(auth.getCurrentUser()));
                    }
                }else{
                    Toast.makeText(RegStaffActivity.this, "Already registered with this email id!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onAuthSuccess(FirebaseUser currentUser) {
        String username = usernameFromEmail(Objects.requireNonNull(currentUser.getEmail()));
        writeNewUser(currentUser.getUid(), username, currentUser.getEmail());
    }

    private void writeNewUser(String uid, String username, String email) {
        User user = new User(username, email);

        staffreff.child("staff").child(uid).setValue(user);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateregdata(){
        if(registername.getText().toString().isEmpty()) {
            warnname.setText("Enter a valid name");
            warnname.setVisibility(View.VISIBLE);
            return false;
        }else{
            warnname.setVisibility(View.GONE);
        }
        if(registeremail.getText().toString().isEmpty() || !registeremail.getText().toString().trim().matches(emailPattern)) {
            warnemail.setText("Enter a valid Email-id");
            warnemail.setVisibility(View.VISIBLE);
            return false;
        }else{
            warnemail.setVisibility(View.GONE);
        }
        if(registerpassword.getText().toString().length()<6) {
            warnpassword.setText("Enter a stronger password");
            warnpassword.setVisibility(View.VISIBLE);
            return false;
        }else if(!registerpassword.getText().toString().equals(registerpasswordrep.getText().toString())){
            warnpassword.setText("Enter the same password");
            warnpassword.setVisibility(View.VISIBLE);
            return false;
        }else{
            warnpassword.setVisibility(View.GONE);
        }
        return true;
    }
}