package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import java.util.UUID;

public class DoctorRegistrationActivity extends AppCompatActivity implements  OnClickListener {

    private TextView alreadyHaveAccount;
    Button regButton,View,ViewAll;
    EditText Docname,docID,docdesig,loginEmail,loginPassword,regPhoneNumber;
    SQLiteDatabase db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);
        Docname = (EditText)findViewById(R.id.Docname);
        docID = (EditText)findViewById(R.id.docID);
        docdesig = (EditText)findViewById(R.id.docdesig);
        loginEmail = (EditText)findViewById(R.id.loginEmail);
        loginPassword = (EditText)findViewById(R.id.loginPassword);
        regPhoneNumber = (EditText)findViewById(R.id.regPhoneNumber);
        regButton = (Button)findViewById(R.id.regButton);
        regButton.setOnClickListener(this);
        View = (Button)findViewById(R.id.viewbutton);
        View.setOnClickListener(this);
        ViewAll = (Button)findViewById(R.id.viewallbutton);
        ViewAll.setOnClickListener(this);
        db1=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Docdata(Docname string,docID integer,docdesig string,loginEmail email,loginPassword string,regPhoneNumber integer);");

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegistrationActivity.this, doctorloginActivity.class);
                startActivity(intent);
            }
        });

        }
    public void onClick(View view){
        // Inserting a record to the Student table
        if (view == regButton) {
            // Checking for empty fields
            if (Docname.getText().toString().trim().length() == 0 ||
                    docID.getText().toString().trim().length() == 0 ||
                    docdesig.getText().toString().trim().length() == 0 ||
                    loginEmail.getText().toString().trim().length() == 0 ||
                    loginPassword.getText().toString().trim().length() == 0 ||
                    regPhoneNumber.getText().toString().trim().length() == 0) {
                Toast.makeText(DoctorRegistrationActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                return;
            }
            int count =0;
            Cursor c=db1.rawQuery("SELECT * FROM Docdata", null);
            count = c.getCount();
            count+=1;
            db1.execSQL("INSERT INTO Docdata VALUES('" + Docname.getText() + "','" + count + "','" + docdesig.getText() +
                    "','" + loginEmail.getText() + "','" + loginPassword.getText() + "','" + regPhoneNumber.getText() + "');");
            showMessage("ID","Your Id is "+count);
            Toast.makeText(DoctorRegistrationActivity.this, "Record added", Toast.LENGTH_SHORT).show();
            clearText();
        }
        if(view==View)
        {
            // Checking for empty Room ID
            if(docID.getText().toString().trim().length()==0)
            {
                Toast.makeText(DoctorRegistrationActivity.this, "Please enter Doctor ID", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor c=db1.rawQuery("SELECT * FROM Docdata WHERE docID='"+docID.getText()+"'", null);
            if(c.moveToFirst())
            {
                Docname.setText(c.getString(0));
                docdesig.setText(c.getString(2));
                loginEmail.setText(c.getString(3));
                loginPassword.setText(c.getString(4));
                regPhoneNumber.setText(c.getString(5));
            }
            else
            {
                Toast.makeText(DoctorRegistrationActivity.this, "Invalid Doctor ID", Toast.LENGTH_SHORT).show();
                clearText();
            }
        }
        if(view==ViewAll)
        {
            Cursor c=db1.rawQuery("SELECT * FROM Docdata", null);
            if(c.getCount()==0)
            {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("Docname: "+c.getString(0)+"\n");
                buffer.append("DocId: "+c.getString(1)+"\n");
                buffer.append("loginEmail: "+c.getString(3)+"\n");
                buffer.append("LoginPassword: "+c.getString(4)+"\n\n");

            }
            showMessage("Doctor Details", buffer.toString());
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
    public void clearText()
    {
        docdesig.setText("");
        docID.setText("");
        Docname.setText("");
        loginEmail.setText("");
        loginPassword.setText("");
        Docname.requestFocus();
    }
}