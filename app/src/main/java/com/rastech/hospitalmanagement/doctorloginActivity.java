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

public class doctorloginActivity extends AppCompatActivity implements OnClickListener {
    EditText loginEmail,loginpassword;
    Button loginButton;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlogin);
        loginEmail = (EditText)findViewById(R.id.loginEmail);
        loginpassword = (EditText)findViewById(R.id.loginPassword);
        db=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
        loginButton =(Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }
    public void onClick(View view) {
        // Inserting a record to the Room table
        if (view == loginButton) {
            // Checking for empty fields
            if (loginEmail.getText().toString().trim().length() == 0 ||
                    loginpassword.getText().toString().trim().length() == 0) {
                Toast.makeText(doctorloginActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                return;
            }
            String s1="",s2="";
            Cursor c=db.rawQuery("SELECT * FROM Docdata WHERE loginEmail='"+loginEmail.getText()+"' and loginPassword='"+loginpassword.getText() + "'", null);
            if(c.moveToFirst()) {
                s1 = c.getString(3);
                s2 = c.getString(4);
            }
            if(c.getCount()==0 || !loginEmail.getText().toString().equals(s1) ||!loginpassword.getText().toString().equals(s2) )
            {
                Toast.makeText(doctorloginActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(doctorloginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(doctorloginActivity.this, DoctorPageActivity.class);
            startActivity(intent);
            clearText();
        }}
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
        loginEmail.setText("");
        loginpassword.setText("");
        loginEmail.requestFocus();
    }
}
