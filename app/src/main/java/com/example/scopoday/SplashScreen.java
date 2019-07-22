package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000);

        //View view = config.create();

       // setContentView(view);

    Thread myThread = new Thread(){
        @Override
        public void run(){
            try {
                sleep(3000);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }catch (InterruptedException e){
                e. printStackTrace();
            }
        }
    };
    myThread.start();
    }
}
