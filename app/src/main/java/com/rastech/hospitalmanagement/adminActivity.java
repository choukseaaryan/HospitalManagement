package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class adminActivity extends AppCompatActivity {

    private Button doctorReg,assignRo, patientDataButton;
    SQLiteDatabase db;
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

        patientDataButton = findViewById(R.id.patientDataButton);
        patientDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS PatientData(regName string,regID integer,regPhoneNumber integer,loginEmail email, loginPassword password);");

                // Displaying all the records
                Cursor c=db.rawQuery("SELECT * FROM PatientData", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Name: "+c.getString(0)+"\n");
                    buffer.append("Id: "+c.getString(1)+"\n");
                    buffer.append("Phone Number: "+c.getString(2)+"\n");
                    buffer.append("Email: "+c.getString(3)+"\n");
                    buffer.append("Password "+c.getString(4)+"\n\n");

                }
                showMessage("Patient Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        if(!message.equals("No records found")) {
            builder.setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.execSQL("DELETE FROM PatientData");
                    Toast.makeText(adminActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
        builder.show();
    }
}