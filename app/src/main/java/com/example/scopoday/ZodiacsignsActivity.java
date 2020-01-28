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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ZodiacsignsActivity extends AppCompatActivity{

    Button aquarius;
    Button aries;
    Button pisces;
    Button virgo;
    Button leo;
    Button cancer;
    Button sagittarius;
    Button taurus;
    Button libra;
    Button scorpio;
    Button gemini;
    Button capricorn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zodiacsigns);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        aquarius = (Button) findViewById(R.id.aquarius);

        aquarius.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v){
               Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

               Bundle bundle = new Bundle();

               bundle.putSerializable("ZODIACSIGN", "aquarius");
               intent.putExtras(bundle);

               startActivity(intent);

           }

        });

        aries = (Button) findViewById(R.id.aries);
        aries.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);


                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "aries");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

        taurus = (Button) findViewById(R.id.taurus);
        taurus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "taurus");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

        pisces = (Button) findViewById(R.id.pisces);
        pisces.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "pisces");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });


        cancer = (Button) findViewById(R.id.cancer);
        cancer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "cancer");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

        gemini = (Button) findViewById(R.id.gemini);
        gemini.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "gemini");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

        leo = (Button) findViewById(R.id.leo);
        leo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "leo");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

        virgo = (Button) findViewById(R.id.virgo);
        virgo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "virgo");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

        libra = (Button) findViewById(R.id.libra);
        libra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "libra");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

        scorpio= (Button) findViewById(R.id.scorpio);
        scorpio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "scorpio");
                intent.putExtras(bundle);

                startActivity(intent);

            }

        });

        sagittarius = (Button) findViewById(R.id.sagittarius);
        sagittarius.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "sagittarius");
                intent.putExtras(bundle);

                startActivity(intent);
            }

        });

        capricorn = (Button) findViewById(R.id.capricorn);
        capricorn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("ZODIACSIGN", "capricorn");
                intent.putExtras(bundle);

                startActivity(intent);
            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem tempItem = menu.getItem(3) ;

        SpannableString spanString = new SpannableString(tempItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0,     spanString.length(), 0); //fix the color to white

        tempItem.setTitle(spanString);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.profil_settings){
            Toast.makeText(this, "Profil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

        if(id == R.id.contact_settings){
            Toast.makeText(this, "contacts", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactListActivity.class);
            startActivity(intent);
        }

        if(id == R.id.zodiacsign_settings){
            //Toast.makeText(this, "zodiacsigns", Toast.LENGTH_SHORT).show();
        }


        if(id == R.id.calendar_settings){
            Toast.makeText(this, "calendar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


}

