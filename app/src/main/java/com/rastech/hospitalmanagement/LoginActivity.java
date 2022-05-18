package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    EditText loginEmail,loginpassword;
    Button loginButton;
    private TextView noAccountQuestion,loginAdmin,doctoraccount;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = (EditText)findViewById(R.id.loginEmail);
        loginpassword = (EditText)findViewById(R.id.loginPassword);
        db=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
        loginButton =(Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        noAccountQuestion = findViewById(R.id.noAccountQuestion);
        noAccountQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        loginAdmin = findViewById(R.id.loginAdmin);
        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
        doctoraccount = findViewById(R.id.doctoraccount);
        doctoraccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, doctorloginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onClick(View view) {

        if (view == loginButton) {
            // Checking for empty fields
            if (loginEmail.getText().toString().trim().length() == 0 ||
                    loginpassword.getText().toString().trim().length() == 0) {
                Toast.makeText(LoginActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                return;
            }
            String s1="",s2="";
            Cursor c=db.rawQuery("SELECT * FROM PatientData WHERE loginEmail='"+loginEmail.getText()+"' and loginPassword='"+loginpassword.getText() + "'", null);
            if(c.moveToFirst()) {
                s1 = c.getString(3);
                s2 = c.getString(4);
            }
            if(c.getCount()==0 || !loginEmail.getText().toString().equals(s1) ||!loginpassword.getText().toString().equals(s2) )
            {
                Toast.makeText(LoginActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, PatientPageActivity.class);
            startActivity(intent);
            clearText();
        }}

    public void clearText()
    {
        loginEmail.setText("");
        loginpassword.setText("");
        loginEmail.requestFocus();
    }
}