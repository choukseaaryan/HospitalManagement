package com.rastech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

public class AdmitRoomAssignActivity extends AppCompatActivity implements OnClickListener {
//    private Button assignButton;
    EditText roomid,patientid,doctorid,totaldays;
    Button assignbutton,View,ViewAll,Update,Discharge;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_room_assign);
        roomid = (EditText)findViewById(R.id.roomid);
        patientid = (EditText)findViewById(R.id.patientid);
        doctorid = (EditText)findViewById(R.id.doctorid);
        totaldays = (EditText)findViewById(R.id.totalDays);
        assignbutton = (Button)findViewById(R.id.assignButton);
        View = (Button)findViewById(R.id.viewbutton);
        ViewAll = (Button)findViewById(R.id.viewalldata);
        Discharge = (Button)findViewById(R.id.dischargePatient);
        Update = (Button)findViewById(R.id.updatedata);
        assignbutton.setOnClickListener(this);
        Discharge.setOnClickListener(this);
        Update.setOnClickListener(this);
        ViewAll.setOnClickListener(this);
        View.setOnClickListener(this);
        db=openOrCreateDatabase("MyHospital", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS roomdata(roomid integer,patientid integer,doctorid integer,totaldays integer);");

//        assignButton = findViewById(R.id.assignButton);
//        assignButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdmitRoomAssignActivity.this, adminActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    public void onClick(View view) {
        // Inserting a record to the Room table
        if (view == assignbutton) {
            // Checking for empty fields
            if (roomid.getText().toString().trim().length() == 0 ||
                    patientid.getText().toString().trim().length() == 0 ||
                    doctorid.getText().toString().trim().length() == 0 ||
                    totaldays.getText().toString().trim().length() == 0) {
                Toast.makeText(AdmitRoomAssignActivity.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                return;
            }
            db.execSQL("INSERT INTO roomdata VALUES('" + roomid.getText() + "','" + patientid.getText() +
                    "','" + doctorid.getText() + "','" + totaldays.getText() + "');");
            Toast.makeText(AdmitRoomAssignActivity.this, "Record added", Toast.LENGTH_SHORT).show();
            clearText();
        }
        if(view==Update)
        {
            // Checking for empty ID
            if(roomid.getText().toString().trim().length()==0)
            {
                Toast.makeText(AdmitRoomAssignActivity.this, "Please enter Room ID", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM roomdata WHERE roomid='"+roomid.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM roomdata WHERE roomid='"+roomid.getText()+"'");
                db.execSQL("INSERT INTO roomdata VALUES('" + roomid.getText() + "','" + patientid.getText() +
                        "','" + doctorid.getText() + "','" + totaldays.getText() + "');");
                Toast.makeText(AdmitRoomAssignActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(AdmitRoomAssignActivity.this, "Invalid Room ID", Toast.LENGTH_SHORT).show();
            }
            clearText();
        }
        if(view==Discharge)
        {
            // Checking for empty Room ID
            if(roomid.getText().toString().trim().length()==0)
            {
                Toast.makeText(AdmitRoomAssignActivity.this, "Please enter Room ID", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM roomdata WHERE roomid='"+roomid.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM roomdata WHERE roomid='"+roomid.getText()+"'");
                Toast.makeText(AdmitRoomAssignActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(AdmitRoomAssignActivity.this, "Invalid Room ID", Toast.LENGTH_SHORT).show();
            }
            clearText();
        }
        // Display a record from the Room table
        if(view==View)
        {
            // Checking for empty Room ID
            if(roomid.getText().toString().trim().length()==0)
            {
                Toast.makeText(AdmitRoomAssignActivity.this, "Please enter Room ID", Toast.LENGTH_SHORT).show();
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM roomdata WHERE roomid='"+roomid.getText()+"'", null);
            if(c.moveToFirst())
            {
                patientid.setText(c.getString(1));
                doctorid.setText(c.getString(2));
                totaldays.setText(c.getString(3));
            }
            else
            {
                Toast.makeText(AdmitRoomAssignActivity.this, "Invalid Room ID", Toast.LENGTH_SHORT).show();
            }
        }
        // Displaying all the records
        if(view==ViewAll)
        {
            Cursor c=db.rawQuery("SELECT * FROM roomdata", null);
            if(c.getCount()==0)
            {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("RoomId: "+c.getString(0)+"\n");
                buffer.append("PatienId: "+c.getString(1)+"\n");
                buffer.append("DoctorId: "+c.getString(2)+"\n");
                buffer.append("TotalDays: "+c.getString(3)+"\n\n");

            }
            showMessage("Room Details", buffer.toString());
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
            roomid.setText("");
            patientid.setText("");
            doctorid.setText("");
            totaldays.setText("");
            roomid.requestFocus();
        }
}