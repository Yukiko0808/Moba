package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ZodiacsignActivity extends AppCompatActivity {

    String displayedzodiacsign;
    TextView zodiacsignstext;
    ImageView zodiacsignimage;
    String imagename;
    TextView zodiacsigntitle;
    TextView Sternzeichen_zeitraum;
    ImageView zodiacsignsternbild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodiacsign);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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

        zodiacsignsternbild = findViewById(R.id.zodiacsignsternbild);
        imagename = "drawable/" + displayedzodiacsign + "sternbild";
        zodiacsignsternbild.setImageResource(getResources().getIdentifier(imagename, "id", getPackageName()));

        Sternzeichen_zeitraum = findViewById(R.id.Sternzeichen_zeitraum);
        String zeitraum =  displayedzodiacsign+ "_zeitraum";
        Sternzeichen_zeitraum.setText(getResources().getIdentifier(zeitraum, "string", getPackageName()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.profil_settings){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

        if(id == R.id.contact_settings){
            Intent intent = new Intent(this, ContactListActivity.class);
            startActivity(intent);
        }

        if(id == R.id.zodiacsign_settings){
            Intent intent = new Intent(this, ZodiacsignsActivity.class);
            startActivity(intent);
        }

        if(id == R.id.calendar_settings){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
