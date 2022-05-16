package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminActivity extends AppCompatActivity {

    private Button doctorReg,assignRo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        doctorReg = findViewById(R.id.doctorReg);
        doctorReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, DoctorRegistrationActivity.class);
                startActivity(intent);
            }
        });
        assignRo = findViewById(R.id.assignRo);
        assignRo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, AdmitRoomAssignActivity.class);
                startActivity(intent);
            }
        });
    }

}