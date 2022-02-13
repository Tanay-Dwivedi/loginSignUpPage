package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class loginPage extends AppCompatActivity {

    EditText lgnName, lgnPass;
    Button lgnBtn;
    String str_lgn_name, str_lgn_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        lgnName = findViewById(R.id.loginName);
        lgnPass = findViewById(R.id.loginPassword);
        lgnBtn = findViewById(R.id.loginBtn);

        lgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });

    }

    private void Validation() {

        str_lgn_name = lgnName.getText().toString();
        str_lgn_pass = lgnPass.getText().toString();

        if(str_lgn_name.isEmpty()) {
            lgnName.setError("Please fill the field");
            lgnName.requestFocus();
        }
        if(str_lgn_pass.isEmpty()) {
            lgnPass.setError("Please fill the field");
            lgnPass.requestFocus();
        }

        confirmFromFb();
    }

    private void confirmFromFb() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(str_lgn_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String fb_pass = dataSnapshot.child("Password").getValue(String.class);
                    if(str_lgn_pass.equals(fb_pass)) {
                        Intent intent = new Intent(loginPage.this, AfterLoginPage.class);
                        Toast.makeText(loginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Invalid Password or Email", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(loginPage.this, "User record Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}