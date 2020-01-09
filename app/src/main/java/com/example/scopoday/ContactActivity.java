package com.example.scopoday;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.media.Image;
import android.media.MediaController2;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
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
import android.widget.ToggleButton;

public class ContactActivity extends AppCompatActivity {

    EditText contactNameText;
    TextView contactAge;
    TextView contactBirthdayTV;
    TextView starSignText;
    ImageView zodiacsign;
    TextView horoskopText;

    FetchingHoroscopeData horoscopeData;

    Contactdata displayedContact;

    int todayLuck;
    int todayLove;
    int todayJob;

    Button reminderButton;


    //SimpleDateFormat sdf = new SimpleDateFormat("dd_mm_yyyy hh:mm:ss");
    private DatePickerDialog.OnDateSetListener mdateSetListener;

    MySQLHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Datenbank aus Main Activity holen (Damit die Daten die gleichen sind)
        db = new MySQLHelper(this);

        //Neues Objekt das die Horoskop Daten holt erstellen -> Verwendet wird es in der Funktion die das Sternzeichen des Kontakts bestimmt
        horoscopeData = new FetchingHoroscopeData();

        //Kontaktdaten aus intent holen (Intent Funktion mit der man Daten von einer Activity in die Andere übertragen kann)
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();//Mit bundle werden mehrere Daten oder Besondere Datentypen übergeben (in diesem fall Contactdata)
        displayedContact = (Contactdata) bundle.getSerializable("CONTACTDATA");

        //Ausgabe ob Intent Kontakt richtig ist
        Log.d("Displayed Kontakt id: ", String.valueOf(displayedContact.getId()) + "Name: " + displayedContact.getName());

        //Kontakt Namenfeld einrichten
        contactNameText = findViewById(R.id.ContactName_TV_ID);
        contactNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactNameText.selectAll(); //Damit text beim antippen makiert ist
            }
        });

        //Kontakt Name 2er On Click Listener !! Vielleicht kann man die noch verbinden ?
        contactNameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {

                    Log.d("CONTACT_CHANGE_NAME", "neuer name:" + contactNameText.getText().toString());
                    // Namen in die Datenbank setzen
                    db.updateContactName(displayedContact, contactNameText.getText().toString());
                    displayedContact.setBirthday(contactNameText.getText().toString());

                    handled = true;
                    InputMethodManager imm = (InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(contactNameText.getWindowToken(), 0);
                }
                return handled;
            }
        });

        //Kontakt Name anzeigen
        contactNameText.setText(displayedContact.getName());

        //Sternzeichen layout image finden
        zodiacsign = findViewById(R.id.zodiacsign);

        //Sternzeichen Text anzeigen
        starSignText = findViewById(R.id.starSign_TV_ID);
        starSignText.setText(CalculateStarSign());


        //Kontakt Alter anzeigen
        contactAge = findViewById(R.id.ContactAlter_TV_ID);
        String ageString = Integer.toString(CalculateAge(displayedContact.getBirthdayDate()));
        contactAge.setText(ageString);

        //Geburtsdatum anzeigen
        contactBirthdayTV = (TextView) findViewById(R.id.contactDatepicker_TV_ID);
        contactBirthdayTV.setText(displayedContact.birthday);
        contactBirthdayTV.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {

                    Log.d("CONTACT_CHANGE_BIRTHDAY", "neuer geburtstag:" + contactBirthdayTV.getText().toString());
                    // Namen in die Datenbank setzen
                    db.updateContactbirthday(displayedContact, contactBirthdayTV.getText().toString());
                    displayedContact.setBirthday(contactBirthdayTV.getText().toString());
                    handled = true;
                    InputMethodManager imm = (InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(contactBirthdayTV.getWindowToken(), 0);
                }
                return handled;

            }
        });


        //Datepicker
       /* contactBirthdayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Neuen Kalender erstellen
                Calendar cal = Calendar.getInstance();

                int year = displayedContact.getBirthdayDate().getYear();
                int month = displayedContact.getBirthdayDate().getMonth();
                int day = displayedContact.getBirthdayDate().getDate();

                //DatePicker auf aktuelles Geburtsdatum setzen
                DatePickerDialog dialog = new DatePickerDialog(ContactActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mdateSetListener,
                        year,month,day);

                //Datepicker anzeigen
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        //Nach eingabe des neuen Geburtsdatums wird ...
        mdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month+1;// (Monat wird um 1 erhöht, da Monate im Programm ab 0 gezählt werden
                String date = day +"."+ month +"."+ year;// ( Neues datum in String umwandeln)

                //... das neue Datum angezeigt ...
                contactBirthdayTV.setText(date);
                //... und in der Datenbank verändert
               db.updateContactbirthday(displayedContact,date);
            }
        };*/


        // Horoskop berechenn und anzeigen
        CalculateHoroskopValues();

        //Kreishoroskop Werte Bestimmen

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


        // toggle der Notification ausläßt / geburtstags reminder
        reminderButton = findViewById(R.id.remainderButton_ID);
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationcall();
            }
        });

     }

////////////// ON CREATE ENDE ////////////////////////


    // Für Alter Bestimmen
    private int CalculateAge(Date _birthday){

        Log.d("Date of Contact", Integer.toString(_birthday.getYear()));

        //hier müsste eigentlich noch das genaue datum berüchsichtigt werden
        int geburtsjahr =  _birthday.getYear() + 1900;
         int age =  Calendar.getInstance().get(Calendar.YEAR) - geburtsjahr;

        return age;

    }

    // Für die Kreise
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

    // notification builden (kontakt hat heute geburtstag)
    public void notificationcall(){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.btn_rounded_corners)
                .setContentTitle("Notification from Scopoday")
                .setContentText("Hello, today is " + displayedContact.getName()+ "'s birthday !!!");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    // Sternzeichen Bestimmen und Bilder/Texte setzen
    private String CalculateStarSign(){
        String starSign = "NoStarsignFound";
        Contactdata actualContact = displayedContact;
        int jobValue = 1;
        int luckValue = 1;
        int loveValue = 1;


        final TextView dailyHoroscopeText = findViewById(R.id.horoskoptext);

        if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),11,21))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),11, 32))) {
            // Steinbock - capricorn
            starSign = "Capricorn";

           /* actualContact.setJob(10);
            actualContact.setLuck(2);
            actualContact.setLove(9);*/
            zodiacsign.setImageResource(R.drawable.capricorn_black);
            //Daten von der Webseite holen
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });

            return starSign;

        }

        if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),0,0))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),0, 21))) {
            // Steinbock - capricorn
            starSign = "Capricorn";

            /*actualContact.setJob(5);
            actualContact.setLuck(4);
            actualContact.setLove(8);*/
            zodiacsign.setImageResource(R.drawable.capricorn_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }
        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),0,20))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),1, 20))){
            //Wassermann - aquarius
            starSign = "Aquarius";
            /*actualContact.setJob(8);
            actualContact.setLuck(4);
            actualContact.setLove(3);*/
            zodiacsign.setImageResource(R.drawable.aquarius_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }
        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),1,19))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),2, 21))){
            starSign = "Pisces";
            /*actualContact.setJob(5);
            actualContact.setLuck(9);
            actualContact.setLove(2);*/
            zodiacsign.setImageResource(R.drawable.pisces_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;

        }
        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),2,20))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),3, 21))){
            //widder - aries
            starSign = "Aries";
           /* actualContact.setJob(7);
            actualContact.setLuck(6);
            actualContact.setLove(3);*/
            zodiacsign.setImageResource(R.drawable.aries_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;

        }

        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),3,20))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),4, 21))){
            starSign = "Taurus";
           /* actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(3);*/
            zodiacsign.setImageResource(R.drawable.taurus_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }


        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),4,20))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),5, 22))){
            starSign = "Gemini";
            /*actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(2);*/
            zodiacsign.setImageResource(R.drawable.gemini_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }

        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),5,21))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),6, 23))){
            //fish
            starSign = "Cancer";
           /* actualContact.setJob(8);
            actualContact.setLuck(8);
            actualContact.setLove(1);*/
            zodiacsign.setImageResource(R.drawable.cancer_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }


        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),6,22))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),7, 24))){
            starSign = "Leo";
           /* actualContact.setJob(2);
            actualContact.setLuck(10);
            actualContact.setLove(4);*/
            zodiacsign.setImageResource(R.drawable.leo_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }

        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),7,23))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),8, 24))){
            starSign = "Virgo";
            /*actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(3);*/
            zodiacsign.setImageResource(R.drawable.virgo_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }

        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),8,23))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),9, 24))){
            //fish
            starSign = "Libra";
           /* actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(2);*/
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }


        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),9,23))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),10, 23))){
            //fish
            starSign = "Scorpio";
            /*actualContact.setJob(10);
            actualContact.setLuck(8);
            actualContact.setLove(2);*/
            zodiacsign.setImageResource(R.drawable.scorpio_black);
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }

        else if(actualContact.getBirthdayDate().after(new Date(actualContact.getBirthdayDate().getYear(),10,22))
                && actualContact.getBirthdayDate().before(new Date(actualContact.getBirthdayDate().getYear(),11, 22))){
            //fish
            starSign = "Sagittarius";
           /* actualContact.setJob(9);
            actualContact.setLuck(2);
            actualContact.setLove(8);*/
            zodiacsign.setImageResource(R.drawable.sagittarius_black);
            //TextView pisces = findViewById(R.id.horoskoptext);
            //pisces.setText(R.string.sagittarius);
            //horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext());
            //dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
            horoscopeData.loadDailyHoroscopeData(starSign, getApplicationContext(), new FetchingHoroscopeData.ServerCallback() {
                @Override
                public void onSuccess(String result) {
                    dailyHoroscopeText.setText(horoscopeData.getDailyHoroscopeText());
                }
            });
            return starSign;
        }
        else {
            return starSign;
        }

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
