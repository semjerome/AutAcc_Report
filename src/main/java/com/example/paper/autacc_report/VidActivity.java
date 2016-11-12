/*
*Team Name : Galaxy Noise
*/


package com.example.paper.autacc_report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VidActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid);

        Button btnv1 = (Button) findViewById(R.id.btnv1);
        btnv1.setOnClickListener(this); // calling onClick() method

        Button btnv2 = (Button) findViewById(R.id.btnv2);
        btnv2.setOnClickListener(this);

        Button btnv3 = (Button) findViewById(R.id.btnv3);
        btnv3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
        Intent intent = new Intent(VidActivity.this, Showvid.class);
        startActivity(intent);
    }
}