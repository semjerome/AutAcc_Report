/*
*Team Name : Galaxy Noise
*/


package com.example.paper.autacc_report;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


public class lists extends AppCompatActivity {

    searching search = new searching();
    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        String[] results  = {"", "", "2. 26. 2016 10:41"};

        /*
        * I'm still trying to get data from the parent's app
        * Onclick of button2, send data location(URL) in sever
        * with sql select statement
        *
        Intent intent3 = getIntent();
        String eventime = intent3.getExtras().getString("Event_Time");

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT location, FROM contacts WHERE date='" + eventime + "';", null);
        startManagingCursor((cursor));

        String[] from = { "date", "location"};
        int[] to = { android.R.id.text1, android.R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);
        */

        ListView list = (ListView) findViewById(R.id.listview);

        ArrayList<String> arraylist = new ArrayList<String>();
        arraylist.add("1. 02.16.2016 09:24");
        arraylist.add("2. 02.16.2016 12:20");

        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
        list.setAdapter(Adapter);

        Button b2 = (Button) findViewById(R.id.button2);


        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                Intent intent3 = new Intent(getApplicationContext(), VidActivity.class);

                //still trying to find the method to send data of calendar to child activity
//              intent2.putExtra("Event_Time", dateP.getMonth()+"."+dateP.getDayOfMonth()+"."+dateP.getYear());

                startActivity(intent3);
            }
        });




    }
}
