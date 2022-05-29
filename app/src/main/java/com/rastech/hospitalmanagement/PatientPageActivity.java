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
    Button logoutPatient,bookAppointPatient;
    SQLiteDatabase db;
    String Data ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_page);
        db=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS appointments(patientid integer);");
        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        Data = str;
        logoutPatient = (Button)findViewById(R.id.logoutPatient);
        bookAppointPatient=(Button)findViewById(R.id.bookAppointPatient);
        logoutPatient = findViewById(R.id.logoutPatient);
        logoutPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        bookAppointPatient = findViewById(R.id.bookAppointPatient);
        bookAppointPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c1=db.rawQuery("SELECT * FROM PatientData WHERE loginEmail='" +Data+"'" , null);
                //String s = c1.getString(1);
                db.execSQL("INSERT INTO appointments VALUES('" + str + "');");
                showMessage("Status","Your Appointment is Set ");
            }
        });
    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}