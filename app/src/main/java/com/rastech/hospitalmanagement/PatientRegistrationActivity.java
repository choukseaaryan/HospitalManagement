package com.rastech.hospitalmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class PatientRegistrationActivity extends AppCompatActivity {

    private TextView alreadyHaveAccount;
    private TextInputEditText regName, regID, loginEmail, loginPassword, regPhoneNumber;
    private Button regButton;
    SQLiteDatabase db;

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

        db=openOrCreateDatabase("PatientData", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS PatientData(regName string,regID integer,regPhoneNumber integer,loginEmail email, loginPassword password);");

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
                    db.execSQL("INSERT INTO PatientData VALUES('" + regName.getText() + "','" + regID.getText() +
                            "','" + regPhoneNumber.getText() + "','" + loginEmail.getText() + "','" + loginPassword.getText() + "');");
                }
                Intent intent = new Intent(PatientRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}