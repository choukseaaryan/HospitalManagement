package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteAppointment extends AppCompatActivity {

    EditText patientcheck;
    Button chkButton;
    String Data;
    SQLiteDatabase db1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointment);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        Data = str;
        patientcheck = (EditText)findViewById(R.id.patientcheck);
        patientcheck = findViewById(R.id.patientcheck);
        chkButton = findViewById(R.id.chkButton);

        db1=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS appointments(patientid integer);");
        chkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c=db1.rawQuery("SELECT * FROM appointments WHERE patientid='"+patientcheck.getText().toString()+"'", null);
                if(c.moveToFirst()) {
                    db1.execSQL("DELETE FROM appointments WHERE patientid='" + patientcheck.getText().toString() + "'");
                    Toast.makeText(DeleteAppointment.this, "Prescribtion Done!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(DeleteAppointment.this, "Invalid Patient Email", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}