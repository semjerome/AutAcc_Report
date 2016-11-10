/*
*Team Name : Galaxy Noise
*/

package com.example.paper.autacc_report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;


/*
* This Part is a temporary database for checking
* Is version different from 2, delete old sql and make new one
* */

class DBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "mycontacts.db";

    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE contacts ( _id INTEGER PRIMARY KEY" + " AUTOINCREMENT, date TEXT, location TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}


public class searching extends AppCompatActivity
{

    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        helper = new DBHelper(this);
        try
        {
            db = helper.getWritableDatabase();
        }
        catch (SQLiteException ex)
        {
            db = helper.getReadableDatabase();
        }

        Button b3 = (Button) findViewById(R.id.button3);
        final DatePicker dateP = (DatePicker)findViewById(R.id.datePicker);


        b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                Intent intent2 = new Intent(getApplicationContext(), lists.class);
                //still trying to find the method to send data of calendar to child activity
//              intent2.putExtra("Event_Time", dateP.getMonth()+"."+dateP.getDayOfMonth()+"."+dateP.getYear());
                startActivity(intent2);
            }
        });



    }
}

