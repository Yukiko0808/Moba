package com.example.scopoday;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.view.View;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.view.inputmethod.InputMethodManager;

public class ContactActivity extends AppCompatActivity {

    EditText contactNameText;
    TextView contactAge;
    TextView contactBirthdayTV;
    TextView starSignText;
    ImageView zodiacsign;

    Contactdata displayedContact;

    int todayLuck;
    int todayLove;
    int todayJob;

    //SimpleDateFormat sdf = new SimpleDateFormat("dd_mm_yyyy hh:mm:ss");
    private DatePickerDialog.OnDateSetListener mdateSetListener;

    MySQLHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        db = MainActivity.db;

        //Kontaktdaten aus intent holen
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        displayedContact =(Contactdata) bundle.getSerializable("CONTACTDATA");

        Log.d("Displayed Kontakt id: ", String.valueOf(displayedContact.getId()) + "Name: " + displayedContact.getName());

        //Kontakt Namenfeld einrichten
        contactNameText = findViewById(R.id.ContactName_TV_ID);
        contactNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactNameText.selectAll();
            }
        });

        zodiacsign = findViewById(R.id.zodiacsign);

        contactNameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE){

                    Log.d("CONTACT_CHANGE_NAME", "neuer name:" + contactNameText.getText().toString());
                    //SetNameInMain(contactNameText.getText().toString());
                    // Namen in der datenbank setzen
                    db.updateContactName(displayedContact, contactNameText.getText().toString());

                    handled = true;
                    InputMethodManager imm = (InputMethodManager)getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(contactNameText.getWindowToken(), 0);
                }
                return handled;
            }
        });

        //Kontakt Name anzeigen
        contactNameText.setText(displayedContact.getName());

        //Sternzeichen Bild anzeigen
        zodiacsign = findViewById(R.id.zodiacsign);

        //Sternzeichen Text anzeigen
        starSignText =  findViewById(R.id.starSign_TV_ID);
        starSignText.setText(CalculateStarSign());

        //Kontakt Alter anzeigen
        contactAge = findViewById(R.id.ContactAlter_TV_ID);
        String ageString = Integer.toString(CalculateAge(displayedContact.getBirthdayDate()));
        contactAge.setText(ageString);
        //Log.d("birthday", "neuer birthday:" + displayedContact.getBirthdayDate());

        //Datepicker

        //Geburtsdatum anzeigen
        contactBirthdayTV = (TextView) findViewById(R.id.contactDatepicker_TV_ID);
        contactBirthdayTV.setText(displayedContact.birthday);


        contactBirthdayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                //int year = cal.get(Calendar.YEAR);

               // int month = cal.get(Calendar.MONTH);
                //int day = cal.get(Calendar.DAY_OF_MONTH);

                int year = displayedContact.getBirthdayDate().getYear();
                int month = displayedContact.getBirthdayDate().getMonth();
                int day = displayedContact.getBirthdayDate().getDate();
                //DatePicker auf aktuelles geburtsdatum setzen
                DatePickerDialog dialog = new DatePickerDialog(ContactActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mdateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day +"."+ month +"."+ year;
                contactBirthdayTV.setText(date);
               db.updateContactbirthday(displayedContact,date);
            }
        };

        CalculateHoroskopValues();

        //LOVE
        ProgressBar pieChart_love = findViewById(R.id.pieChart_Love_ID).findViewById(R.id.progressBar);
        pieChart_love.setProgress(todayLove);
        TextView txt_love = (TextView) findViewById(R.id.pieChart_Love_ID).findViewById(R.id.percentText);
        txt_love.setText(todayLove + "%");

        //JOB
        ProgressBar pieChart_job = findViewById(R.id.pieChart_Job_ID).findViewById(R.id.progressBar);
        pieChart_job.setProgress(todayJob);
        TextView txt_job = (TextView) findViewById(R.id.pieChart_Job_ID).findViewById(R.id.percentText);
        txt_job.setText(todayJob + "%");

        //LUCK
        ProgressBar pieChart_luck = findViewById(R.id.pieChart_Luck_ID).findViewById(R.id.progressBar);
        pieChart_luck.setProgress(todayLuck);
        TextView txt_luck = (TextView) findViewById(R.id.pieChart_Luck_ID).findViewById(R.id.percentText);
        txt_luck.setText(todayLuck + "%");


     }


     private int CalculateAge(Date _birthday){

        Log.d("Date of Contact", Integer.toString(_birthday.getYear()));

        //hier müsste eigentlich noch das genaue datum berüchsichtigt werden
        int geburtsjahr =  _birthday.getYear() + 1900;
         int age =  Calendar.getInstance().get(Calendar.YEAR) - geburtsjahr;

        return age;

    }

    private void CalculateHoroskopValues(){

        Random horoskopValue = new Random();
/*
        todayJob = 10 + horoskopValue.nextInt(100) * MainActivity.transmittedContact.getJob();
        todayLove = 10 + horoskopValue.nextInt(100) * MainActivity.transmittedContact.getLove();
        todayLuck = 10 + horoskopValue.nextInt(100) * MainActivity.transmittedContact.getLove();
*/
        Double ndsek = Math.random()*(91)+10;
        todayJob = ndsek.intValue();

        if (todayJob > 100){
            todayJob = todayJob / 10;
        }
        if (todayLove > 100){
            todayLove = todayLove / 10;
        }
        if (todayLuck > 100){
            todayLuck = todayLuck / 10;
        }

    }

    //public void setTempContact(Contactdata tempContact) {
        //this.tempContact = tempContact;
   // }

    private String CalculateStarSign(){
        String starSign = "NoStarsignFound";
        Contactdata actualContact = MainActivity.transmittedContact;

        return "capricorn";
        /*
        if(actualContact.birthdayDate.after(new Date(actualContact.birthdayDate.getYear(),11,21))
                && actualContact.birthdayDate.before(new Date(actualContact.birthdayDate.getYear(),11, 32))) {
            // Steinbock - capricorn
            starSign = "capricorn";
            /*
            actualContact.setJob(10);
            actualContact.setLuck(2);
            actualContact.setLove(9);*//*
            zodiacsign.setImageResource(R.drawable.capricorn_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.capricorn);
            return starSign;

        }

        if(actualContact.birthdayDate.after(new Date(actualContact.birthdayDate.getYear(),0,0))
                && actualContact.birthdayDate.before(new Date(actualContact.birthdayDate.getYear(),0, 21))) {
            // Steinbock - capricorn
            starSign = "capricorn";
            /*
            actualContact.setJob(5);
            actualContact.setLuck(4);
            actualContact.setLove(8);*/
           /* zodiacsign.setImageResource(R.drawable.capricorn_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.capricorn);
            return starSign;
        }/*
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),0,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),1, 20))){
            //Wassermann - aquarius
            starSign = "aquarius";
            actualContact.setJob(8);
            actualContact.setLuck(4);
            actualContact.setLove(3);
            zodiacsign.setImageResource(R.drawable.aquarius_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.aquarius);
            return starSign;
        }
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),1,19))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),2, 21))){
            starSign = "pisces";
            actualContact.setJob(5);
            actualContact.setLuck(9);
            actualContact.setLove(2);
            zodiacsign.setImageResource(R.drawable.pisces_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.pisces);
            return starSign;

        }
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),2,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),3, 21))){
            //widder - aries
            starSign = "aries";
            actualContact.setJob(7);
            actualContact.setLuck(6);
            actualContact.setLove(3);
            zodiacsign.setImageResource(R.drawable.aries_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.aries);
            return starSign;

        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),3,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),4, 21))){
            starSign = "taurus";
            actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(3);
            zodiacsign.setImageResource(R.drawable.taurus_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.taurus);
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),4,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),5, 22))){
            starSign = "gemini";
            actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(2);
            zodiacsign.setImageResource(R.drawable.gemini_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.gemini);
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),5,21))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),6, 23))){
            //fish
            starSign = "cancer";
            actualContact.setJob(8);
            actualContact.setLuck(8);
            actualContact.setLove(1);
            zodiacsign.setImageResource(R.drawable.cancer_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.cancer);
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),6,22))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),7, 24))){
            starSign = "lio";
            actualContact.setJob(2);
            actualContact.setLuck(10);
            actualContact.setLove(4);
            zodiacsign.setImageResource(R.drawable.leo_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.leo);
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),7,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),8, 24))){
            starSign = "virgo";
            actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(3);
            zodiacsign.setImageResource(R.drawable.virgo_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.virgo);
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),8,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),9, 24))){
            //fish
            starSign = "libra";
            actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(2);
            zodiacsign.setImageResource(R.drawable.libra_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.libra);
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),9,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),10, 23))){
            //fish
            starSign = "scorpio";
            actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(2);
            zodiacsign.setImageResource(R.drawable.scorpio_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.scorpio);
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),10,22))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),11, 22))){
            //fish
            starSign = "sagittarius";
            actualContact.setJob(9);
            actualContact.setLuck(2);
            actualContact.setLove(8);
            zodiacsign.setImageResource(R.drawable.sagittarius_black);
            TextView pisces = findViewById(R.id.horoskoptext);
            pisces.setText(R.string.sagittarius);
            return starSign;
        }*//*
        else {
            return starSign;
        }*/
    }


/*  // war um transmittet contakt zu setzen kann eigentlich raus
    private void SetBirthdateInMain(Date d){
        Contactdata newContact = MainActivity.transmittedContact;
        newContact.setBirthdayDate(d);
        MainActivity.contactList.set(MainActivity.transmittedContactPosition, newContact);
        String ageString = Integer.toString(CalculateAge());
        contactAge.setText(ageString);
        starSignText.setText(CalculateStarSign());
        Log.d("CONTACT", "changed contact:" + MainActivity.transmittedContact.name);
    }

    private void SetNameInMain(String n){
        Contactdata newContact = MainActivity.transmittedContact;
        newContact.setName(n);
        MainActivity.contactList.set(MainActivity.transmittedContactPosition, newContact);

    }*/

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
