package com.example.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUpPage extends AppCompatActivity {

    EditText sgnEmail, sgnPass, sgnPhone, sgnName;
    Button sgnBtn;
    String strName, strPhone, strEmail, strPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        sgnName = findViewById(R.id.signUpName);
        sgnPhone = findViewById(R.id.signUpPhoneNumber);
        sgnEmail = findViewById(R.id.signupEmail);
        sgnPass = findViewById(R.id.signupPassword);
        sgnBtn = findViewById(R.id.signUpBtn);

        // setting the progress dialog box
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);

        // setting up the sign up button
        sgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }

    private void Validation() {

        strName = sgnName.getText().toString();
        strPhone = sgnPhone.getText().toString();
        strEmail = sgnEmail.getText().toString();
        strPassword = sgnPass.getText().toString();

        if(strName.isEmpty()) {
            sgnName.setError("Please give the valid username");
            sgnName.requestFocus();
            return;
        }

        if(!numberCheck(strPhone)) {
            sgnPhone.setError("Please give the valid Phone Number");
            sgnPhone.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            sgnEmail.setError("Please give the valid email address");
            sgnEmail.requestFocus();
            return;
        }

        if(strPassword.isEmpty()) {
            sgnPass.setError("Please give the valid password");
            sgnPass.requestFocus();
            return;
        }
        else if(!passwordValidate(strPassword)) {
            sgnPass.setError("Enter Min 6 digits");
            sgnPass.requestFocus();
            return;
        }

        createAccount();

    }

    private void createAccount() {
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        sendDataToFb();
    }

    private void sendDataToFb() {

        String sgnTime = ""+System.currentTimeMillis();
        HashMap<String, Object> data = new HashMap<>();
        data.put("Name", strName);
        data.put("Phone Number", strPhone);
        data.put("Email", strEmail);
        data.put("Password", strPassword);

        //Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(strName).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                //firebase updating
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "SignUp Successful", Toast.LENGTH_SHORT).show();

                //navigating to the new activity
                Intent intent = new Intent(signUpPage.this, loginPage.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean passwordValidate(String strPassword) {

        Pattern p = Pattern.compile("[a-zA-Z0-9@#$%^&+=].{6,20}");
        Matcher m = p.matcher(strPassword);
        return m.matches();
    }

    private boolean numberCheck(String strPhone) {

        Pattern p = Pattern.compile("[0-9]{10}");
        Matcher m = p.matcher(strPhone);
        return m.matches();
    }
}