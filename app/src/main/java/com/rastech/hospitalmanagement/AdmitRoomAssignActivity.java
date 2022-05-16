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
        db=openOrCreateDatabase("RoomDB", Context.MODE_PRIVATE, null);
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
        // Inserting a record to the Student table
        if (view == assignbutton) {
            // Checking for empty fields
            if (roomid.getText().toString().trim().length() == 0 ||
                    patientid.getText().toString().trim().length() == 0 ||
                    doctorid.getText().toString().trim().length() == 0 ||
                    totaldays.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter all values");
                return;
            }
            db.execSQL("INSERT INTO roomdata VALUES('" + roomid.getText() + "','" + patientid.getText() +
                    "','" + doctorid.getText() + "','" + totaldays.getText() + "');");
            showMessage("Success", "Record added");
            clearText();
        }
        if(view==Update)
        {
            // Checking for empty roll number
            if(roomid.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter roomid");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM roomdata WHERE roomid='"+roomid.getText()+"'", null);
            if(c.moveToFirst()) {
                db.execSQL("UPDATE roomdata SET patientid='" + patientid.getText() + "',doctorid='" + doctorid.getText() + "',totaldays='" + totaldays.getText()+
                        "' WHERE rollno='"+roomid.getText()+"');");
                showMessage("Success", "Record Modified");
            }
            else {
                showMessage("Error", "Invalid RoomID");
            }
            clearText();
        }
        if(view==Discharge)
        {
            // Checking for empty roll number
            if(roomid.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter RoomId");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM roomdata WHERE roomid='"+roomid.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM roomdata WHERE roomid='"+roomid.getText()+"'");
                showMessage("Success", "Record Deleted");
            }
            else
            {
                showMessage("Error", "Invalid Roomid");
            }
            clearText();
        }
        // Display a record from the Student table
        if(view==View)
        {
            // Checking for empty roll number
            if(roomid.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter RoomID");
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
                showMessage("Error", "Invalid RoomID");
                clearText();
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