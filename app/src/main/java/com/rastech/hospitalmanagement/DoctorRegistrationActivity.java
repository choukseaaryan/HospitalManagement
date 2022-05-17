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

public class DoctorRegistrationActivity extends AppCompatActivity implements  OnClickListener {

    private TextView alreadyHaveAccount;
    Button regButton;
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
        db1=openOrCreateDatabase("Docinfo", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Docdata(Docname VARCHAR,docID integer,docdesig VARCHAR,loginEmail VARCHAR,loginPassword VARCHAR,regPhoneNumber integer);");

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegistrationActivity.this, LoginActivity.class);
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
                showMessage("Error", "Please enter all values");
                return;
            }
            db1.execSQL("INSERT INTO Docdata VALUES('" + Docname.getText() + "','" + docID.getText() + "','" + docdesig.getText() +
                    "','" + loginEmail.getText() + "','" + loginPassword.getText() + "','" + regPhoneNumber.getText() + "');");
            showMessage("Success", "Record added");
            clearText();
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
        Docname.setText("");
        docID.setText("");
        docdesig.setText("");
        loginEmail.setText("");
        loginPassword.setText("");
        regPhoneNumber.setText("");
    }
}