package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ZodiacsignsActivity extends AppCompatActivity implements View.OnClickListener {

    Button aquarius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zodiacsigns);


        aquarius = (Button) findViewById(R.id.aquarius);
        aquarius.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent (this, ZodiacsignActivity.class);
        startActivity(intent);
    }
}

