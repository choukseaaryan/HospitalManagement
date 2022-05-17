package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PatientPageActivity extends AppCompatActivity {
    Button logoutPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_page);
        logoutPatient = (Button)findViewById(R.id.logoutPatient);
<<<<<<< HEAD
        logoutPatient.setOnClickListener(new View.OnClickListener() {
=======

        logoutPatient.setOnClickListener(new OnClickListener() {
>>>>>>> 906e5162828a461178b90621d592f921ea668ef6
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}