package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoctorSearchActivity extends AppCompatActivity {
    EditText docID;
    Button searchButton;
    SQLiteDatabase db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search);

        docID = findViewById(R.id.docID);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db1=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
                db1.execSQL("CREATE TABLE IF NOT EXISTS Docdata(Docname string,docID integer,docdesig string,loginEmail email,loginPassword string,regPhoneNumber integer);");

                if(docID.getText().toString().trim().length()==0)
                {
                    Toast.makeText(DoctorSearchActivity.this, "Please enter Doctor ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor c=db1.rawQuery("SELECT * FROM Docdata WHERE docID='"+docID.getText()+"'", null);
                if(c.moveToFirst())
                {
                    StringBuffer buffer=new StringBuffer();

                    buffer.append("Name: "+c.getString(0)+"\n");
                    buffer.append("Id: "+c.getString(1)+"\n");
                    buffer.append("Designation: "+c.getString(2)+"\n");
                    buffer.append("Email: "+c.getString(3)+"\n");
                    buffer.append("Password "+c.getString(4)+"\n");
                    buffer.append("Phone Number: "+c.getString(5)+"\n\n");

                    showMessage("Doctor Data", buffer.toString());
                }
                else
                {
                    Toast.makeText(DoctorSearchActivity.this, "Invalid Doctor ID", Toast.LENGTH_SHORT).show();
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