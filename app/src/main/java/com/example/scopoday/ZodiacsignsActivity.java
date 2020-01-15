package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class ZodiacsignsActivity extends AppCompatActivity implements View.OnClickListener {

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


        aquarius = (Button) findViewById(R.id.aquarius);

        aquarius.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v){
               Intent intent = new Intent (getApplicationContext(), ZodiacsignActivity.class);


               Bundle bundle = new Bundle();

               bundle.putSerializable("ZODIACSIGN", "aquarius");
               intent.putExtras(bundle);

               //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

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

                //Activity starten

                startActivity(intent);

            }

        });


    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent (this, ZodiacsignActivity.class);


            Bundle bundle = new Bundle();




           // bundle.putSerializable("ZODIACSIGN", _zodiacsign);
            intent.putExtras(bundle);

            //Activity starten

        startActivity(intent);
        }
}

