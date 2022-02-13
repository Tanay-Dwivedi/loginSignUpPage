package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;

public class loginAndSignUpAsk extends AppCompatActivity {

Button lgn, sgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_and_sign_up_ask);

        //This removed the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        lgn = findViewById(R.id.loginBtn);
        sgn = findViewById(R.id.signUpBtn);

        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginAndSignUpAsk.this, loginPage.class);
                startActivity(intent);
                finish();
            }
        });

        sgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginAndSignUpAsk.this, signUpPage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}