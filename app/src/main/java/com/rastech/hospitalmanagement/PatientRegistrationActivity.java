package com.rastech.hospitalmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PatientRegistrationActivity extends AppCompatActivity {

    private TextView alreadyHaveAccount;
    private TextInputEditText regName, regID, loginEmail, loginPassword, regPhoneNumber;
    private Button regButton;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        regName = findViewById(R.id.regName);
        regID = findViewById(R.id.regID);
        regPhoneNumber = findViewById(R.id.regPhoneNumber);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        regButton = findViewById(R.id.regButton);

        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = regName.getText().toString().trim();
                final String idNumber = regID.getText().toString().trim();
                final String phone = regPhoneNumber.getText().toString().trim();
                final String email = loginEmail.getText().toString().trim();
                final String password = loginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    regName.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(idNumber)){
                    regID.setError("ID is required");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    regPhoneNumber.setError("Phone Number is required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is required");
                     return;
                }

                else {
                    loader.setMessage("Registration in progress...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                String error = task.getException().toString();
                                Toast.makeText(PatientRegistrationActivity.this, "Error Occured: " + error, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                                HashMap userInfo = new HashMap();

                                userInfo.put("name", regName);
                                userInfo.put("idNumber", regID);
                                userInfo.put("phone", regPhoneNumber);
                                userInfo.put("email", loginEmail);
                                userInfo.put("password", loginPassword);
                                userInfo.put("type", "patient");

                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(PatientRegistrationActivity.this, "Details added successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(PatientRegistrationActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                });

                            }
                        }
                    });
                    Intent intent = new Intent(PatientRegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    loader.dismiss();
                }

            }
        });


    }
}