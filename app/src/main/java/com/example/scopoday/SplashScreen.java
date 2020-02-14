package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.content.Intent;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    ImageView splashBg;
    Animation sBgAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashscreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

/*
        splashBg= (ImageView) findViewById(R.id.backgroudIMG_splashAnim);
        sBgAnim = AnimationUtils.loadAnimation(this,R.anim.splashanim);
        splashBg.startAnimation(sBgAnim);
        sBgAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation){

            }
            @Override
            public void onAnimationEnd(Animation animation){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
            @Override
            public void onAnimationRepeat(Animation animation){
            }
        });*/
        //splashBg.startAnimation(sBgAnim);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class);
                //.withBackgroundResource(R.drawable.background_splaschscreen);
                //.withSplashTimeOut(2000);


        //View splashView = getWindow().getDecorView();
        //splashView.setSystemUiVisibility(
        //        View.SYSTEM_UI_FLAG_FULLSCREEN
        //);
       // View view = config.create();

        //setContentView(view);


    Thread myThread = new Thread(){
        @Override
        public void run(){
            try {
                sleep(2000);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);


                finish();
            }catch (InterruptedException e){
                e. printStackTrace();
            }
        }
    };

    myThread.start();

    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }



}
