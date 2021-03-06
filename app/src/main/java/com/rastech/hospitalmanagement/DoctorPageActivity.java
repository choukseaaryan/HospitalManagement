package com.rastech.hospitalmanagement;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.database.Cursor;
import android.widget.Toast;

public class DoctorPageActivity extends AppCompatActivity implements View.OnClickListener {

    Button doctorprofile,schedule,appointmentdone;
    TextView useremail;
    SQLiteDatabase db1;
    String Data="";
    String str1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_page);
        useremail = (TextView)findViewById(R.id.user);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        useremail.setText("Welcome, Doctor");
        Data = str;
        doctorprofile = (Button)findViewById(R.id.doctorprofile);
        doctorprofile.setOnClickListener(this);
        schedule = (Button)findViewById(R.id.schedule);
        schedule.setOnClickListener(this);
        appointmentdone = (Button)findViewById(R.id.appointmentdone);
        appointmentdone.setOnClickListener(this);
        db1=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Docdata(Docname string,docID integer,docdesig string,loginEmail email,loginPassword string,regPhoneNumber integer);");
        db1.execSQL("CREATE TABLE IF NOT EXISTS appointments(patientid integer);");
        appointmentdone = findViewById(R.id.appointmentdone);
        appointmentdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = str;
                Intent intent = new Intent(DoctorPageActivity.this, DeleteAppointment.class);
                intent.putExtra("message_key", str);
                startActivity(intent);
            }
        });
    }
    public void onClick(View view) {

        if (view == doctorprofile) {
            Cursor c=db1.rawQuery("SELECT * FROM Docdata WHERE loginEmail='" +Data+"'", null);
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
                buffer.append("Designation: "+c.getString(2)+"\n");
                buffer.append("Email: "+c.getString(3)+"\n");
                buffer.append("Password "+c.getString(4)+"\n");
                buffer.append("Phone Number: "+c.getString(5)+"\n\n");

            }
            showMessage("Doctor Data", buffer.toString());
        }
        if(view == schedule)
        {
            Cursor c1=db1.rawQuery("SELECT * FROM appointments ", null);
            if(c1.getCount()==0)
            {
                showMessage("Schedule", "No Schedule found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c1.moveToNext())
            {
                buffer.append("Patient Email: "+c1.getString(0)+"\n");
            }
            showMessage("Scheduled Patient Data", buffer.toString());
        }

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
