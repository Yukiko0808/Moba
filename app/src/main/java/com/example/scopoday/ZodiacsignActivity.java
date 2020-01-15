package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;

public class ZodiacsignActivity extends AppCompatActivity {

    String displayedzodiacsign;
    TextView zodiacsignstext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodiacsign);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();//Mit bundle werden mehrere Daten oder Besondere Datentypen übergeben (in diesem fall Contactdata)
        displayedzodiacsign = (String) bundle.getSerializable("ZODIACSIGN");
        zodiacsignstext = findViewById(R.id.zodiacsignstext);
        zodiacsignstext.setText(getResources().getIdentifier(displayedzodiacsign, "string", getPackageName()));


    }
}
