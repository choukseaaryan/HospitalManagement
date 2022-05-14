package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DoctorRegistrationActivity extends AppCompatActivity {

    private TextView alreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}