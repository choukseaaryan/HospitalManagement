package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PatientLoginActivity extends AppCompatActivity {

    EditText loginEmail,loginpassword;
    Button loginButton;
    private TextView noAccountQuestion;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        db=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);

        loginEmail = (EditText)findViewById(R.id.loginEmail);
        loginpassword = (EditText)findViewById(R.id.loginPassword);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginEmail.getText().toString().trim().length() == 0 ||
                        loginpassword.getText().toString().trim().length() == 0) {
                    Toast.makeText(PatientLoginActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PatientLoginActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(PatientLoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PatientLoginActivity.this, PatientPageActivity.class);
                startActivity(intent);
                clearText();
            }
        });

        noAccountQuestion = findViewById(R.id.noAccountQuestion);
        noAccountQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientLoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void clearText()
    {
        loginEmail.setText("");
        loginpassword.setText("");
        loginEmail.requestFocus();
    }
}
