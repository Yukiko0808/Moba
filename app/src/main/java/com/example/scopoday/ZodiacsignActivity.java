package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;

public class ZodiacsignActivity extends AppCompatActivity {

    String displayedzodiacsign;
    TextView zodiacsignstext;
    ImageView zodiacsignimage;
    String imagename;
    TextView zodiacsigntitle;
    TextView Sternzeichen_zeitraum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodiacsign);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();//Mit bundle werden mehrere Daten oder Besondere Datentypen übergeben (in diesem fall Contactdata)
        displayedzodiacsign = (String) bundle.getSerializable("ZODIACSIGN");
        zodiacsignstext = findViewById(R.id.zodiacsignstext);
        zodiacsignstext.setText(getResources().getIdentifier(displayedzodiacsign, "string", getPackageName()));
        zodiacsignimage = findViewById(R.id.zodiacsignimage);
        imagename = "drawable/" + displayedzodiacsign + "_white";
        zodiacsignimage.setImageResource(getResources().getIdentifier(imagename, "id", getPackageName()));
        zodiacsigntitle = findViewById(R.id.zodiacsigntitle);
        String titel =  displayedzodiacsign+ "_titel";
        zodiacsigntitle.setText(getResources().getIdentifier(titel, "string", getPackageName()));

        Sternzeichen_zeitraum = findViewById(R.id.Sternzeichen_zeitraum);
        String zeitraum =  displayedzodiacsign+ "_zeitraum";
        Sternzeichen_zeitraum.setText(getResources().getIdentifier(zeitraum, "string", getPackageName()));



    }
}
