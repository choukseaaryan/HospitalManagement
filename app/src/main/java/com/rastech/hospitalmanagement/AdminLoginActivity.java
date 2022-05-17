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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    EditText adminloginEmail,adminloginPassword;
    Button adminloginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login2);
        adminloginbutton = (Button)findViewById(R.id.adminloginButton);
        adminloginEmail = (EditText)findViewById(R.id.adminloginEmail);
        adminloginPassword = (EditText)findViewById(R.id.adminloginPassword);
        int flag =0;
        adminloginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(adminloginEmail.getText().toString().equals("Admin") && adminloginPassword.getText().toString().equals("1234")) {
                        Intent intent = new Intent(AdminLoginActivity.this, adminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(AdminLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

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
    public void clearText()
    {
        adminloginEmail.setText("");
        adminloginPassword.setText("");
        adminloginEmail.requestFocus();
    }
}