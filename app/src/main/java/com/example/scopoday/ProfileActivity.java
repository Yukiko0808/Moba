package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {


    TextView ContactName_TV_ID;
    MySQLHelper db;
    EditText contactNameText;
    TextView contactAge;
    TextView contactBirthdayTV;
    ImageView zodiacsign;
    TextView horoscopeZodiacsignTitle;

    RelativeLayout horoscopeCard;

    FetchingHoroscopeData horoscopeData;

    Contactdata displayedContact;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        db = new MySQLHelper(this);

        ContactName_TV_ID = findViewById(R.id.ContactName_TV_ID);

        boolean profilerstellen = true;

        List<Contactdata> contacts = db.getAllContacts();

        for (int i =0; i < contacts.size(); i++){
            if(contacts.get(i).isYou){
                profilerstellen = false;
                displayedContact = contacts.get(i);
            }
        }
        if (profilerstellen == true) {
            Contactdata you = new Contactdata("You", "01.01.2000");
            you.isYou = true;
            db.addContact(you);
            displayedContact = you;

        }


        //Neues Objekt das die Horoskop Daten holt erstellen -> Verwendet wird es in der Funktion die das Sternzeichen des Kontakts bestimmt
        horoscopeData = new FetchingHoroscopeData();



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
        zodiacsign = findViewById(R.id.horoscopeCard).findViewById(R.id.zodiacsign);
        horoscopeZodiacsignTitle = findViewById(R.id.zodiacsign_TextView);

        //Sternzeichen Text anzeigen
        final String zodiacsignText = CalculateStarSign();
        horoscopeZodiacsignTitle.setText(zodiacsignText);

        //Kontakt Alter anzeigen
        contactAge = findViewById(R.id.ContactAlter_TV_ID);
        final String ageString = Integer.toString(CalculateAge(displayedContact.getBirthdayDate()));
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
                    // Namen in die Datenbank setzen In Liste Ändern und Alter aktualisieren
                    db.updateContactbirthday(displayedContact, contactBirthdayTV.getText().toString());
                    displayedContact.setBirthday(contactBirthdayTV.getText().toString());
                    contactAge.setText(Integer.toString(CalculateAge((displayedContact.getBirthdayDate()))));

                    handled = true;
                    InputMethodManager imm = (InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(contactBirthdayTV.getWindowToken(), 0);
                }
                return handled;

            }
        });

        ContactName_TV_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ContactName_TV_ID.selectAll(); //Damit text beim antippen makiert ist
            }
        });

        //Kontakt Name 2er On Click Listener !! Vielleicht kann man die noch verbinden ?
       /* ContactName_TV_ID.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
            if (i == EditorInfo.IME_ACTION_DONE) {

                    Log.d("CONTACT_CHANGE_NAME", "neuer name:" + ContactName_TV_ID.getText().toString());
                    // Namen in die Datenbank setzen
                    //db.updateContactName(displayedContact, ContactName_TV_ID.getText().toString());
                   db.addContact(Contact).setBirthday(ContactName_TV_ID.getText().toString());

                    handled = true;
                    InputMethodManager imm = (InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ContactName_TV_ID.getWindowToken(), 0);
                }
                return handled;
            }
        });*/
    }

    private String CalculateStarSign(){
        String starSign = "NoStarsignFound";
        Contactdata actualContact = displayedContact;
        int jobValue = 1;
        int luckValue = 1;
        int loveValue = 1;


        final TextView dailyHoroscopeText = findViewById(R.id.horoskopText_ID);

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
            // Wassermann - aquarius
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
            zodiacsign.setImageResource(R.drawable.libra_black);
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

    private int CalculateAge(Date _birthday){

        Calendar calToday = Calendar.getInstance();

        Calendar calBD  = Calendar.getInstance();
        calBD.setTime(_birthday);

        //int geburtsjahr =  _birthday.getYear() + 1900;
        int geburtsjahr = calBD.get(Calendar.YEAR);
        int age =  Calendar.getInstance().get(Calendar.YEAR) - geburtsjahr;

        if(calToday.get(Calendar.DAY_OF_MONTH) >= calBD.get(Calendar.DAY_OF_MONTH) &&
                calToday.get(Calendar.MONTH) >= calBD.get(Calendar.MONTH))
        {
            return age;
        }
        else{
            age = age-1;
            return age;
        }
    }



    public long CalculateDaysTillBD (Date bd){

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        Date birthDate = new Date(0000, bd.getMonth(), bd.getDate());
        Date todayDate = new Date(0000, today.getMonth(), today.getDate());

        if(birthDate.getTime()<todayDate.getTime()){
            birthDate = new Date(1, birthDate.getMonth(), birthDate.getDate());
        }

        long days = birthDate.getTime() - todayDate.getTime();
        days = days / (24 * 60 * 60 * 1000);
        /*
        Log.d("First date", birthDate.toString());
        Log.d("Second Date", todayDate.toString());
        Log.d("Days?" , Long.toString(days));*/

        return  days;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem tempItem = menu.getItem(0) ;

        SpannableString spanString = new SpannableString(tempItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0,     spanString.length(), 0); //fix the color to white

        tempItem.setTitle(spanString);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.profil_settings){

        }

        if(id == R.id.contact_settings){
            Toast.makeText(this, "contacts", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactListActivity.class);
            startActivity(intent);

        }

        if(id == R.id.zodiacsign_settings){
            Toast.makeText(this, "zodiacsigns", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ZodiacsignsActivity.class);
            startActivity(intent);
        }


        if(id == R.id.calendar_settings){
            Toast.makeText(this, "calendar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
