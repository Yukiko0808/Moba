package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


//import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView contactText;
    String contactInfo;
    public static MainActivity mainActivity;

    Button allContactsButton;
    ImageButton addProfileButton;

    CompactCalendarView compCalendarView;

    TextView monthYearIndicatorText;
    TextView recylerHeadline;
    TextView calendarZodiacsignHeadline;


    //ListView contactListView;
    RecyclerView contactListView;
    public static ArrayList<Contactdata> contactList;
    ContactListAdapter mainAdapter;

    public List<Contactdata> onlyNext3Bds = new ArrayList<>();


    NextBirthdaysListAdapter birthdayContactAdapter;

    ConstraintLayout nextBD1, nextBD2, nextBD3;

    public static MySQLHelper db;

    final SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy");
    final SimpleDateFormat dateFormatForDate = new SimpleDateFormat("MMMM - dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;

        setContentView(R.layout.activity_main);
        // createContactButtons();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // App nach dem Berechtigungen überprüfen -> war für Kontakte aus dem Telefonbuch zu laden
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 10);
        }

        // wenn man sich in einem Fragment befindet, wird nicht "this" übergeben sondern "getContext()"
        db = new MySQLHelper(this);
/*
        // Kontakte in die Tabelle schreiben
        // Wenn man beim Eintragen auf den Haken in der Tastatur geht, muss eine Funktion aufgerufen werden, die folgendes aufruft:
        // (natürlich mit Variablen statt Strings)
        // db.addContact(new Contactdata("Nala", "20.07.1990", "Lio"));
        // db.addContact(new Contactdata("Ben", "22.09.1999", "Libra"));

        // Kontakte aus der Tabelle holen und in eine Liste speichern
        // List<Contactdata> contactdataList = db.getAllContacts();

        // Wenn man die Liste sehen will, folgendes machen:
        // 1. Breakpoint setzen (an Stelle der Liste oder eins weiter)
        // 2. "Run" -> "Debug 'app'. Compiler wird am Breakpoint anhalten. Unter Variables kann man die Liste anschauen.
*/

        contactList = null;
        contactListView = findViewById(R.id.birthdaysRecyclerView);

        if(contactList == null) contactList = new ArrayList<>();

        ArrayList<Contactdata> listOfContacts = new ArrayList<>(db.getAllContacts().size());
        listOfContacts.addAll(db.getAllContacts());
        contactList = listOfContacts;

        List<Contactdata> orderdList = contactList;
        orderdList.sort(new Comparator<Contactdata>() {
             @Override
             public int compare(Contactdata c1, Contactdata c2) {

                 if(c1.CalculateDaysTillBD(c1.getBirthdayDate()) < c2.CalculateDaysTillBD(c2.getBirthdayDate())) {
                     return -1;
                 }
                 return 0;
             }
        });

        for(int i = 0; i<3; i++){
            if(orderdList.size() > i){
                onlyNext3Bds.add(orderdList.get(i));
            }

        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.birthdaysRecyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        birthdayContactAdapter = new NextBirthdaysListAdapter(this, onlyNext3Bds);
        contactListView.setAdapter(birthdayContactAdapter);

        contactText = findViewById(R.id.textViewDateBD_ID);

        allContactsButton = (Button) findViewById(R.id.AddContactButton_ID);

        allContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Open Contact list", Toast.LENGTH_SHORT).show();
                openContactListActivity();
                //AddContact();
            }
        });

        monthYearIndicatorText = findViewById(R.id.calendarHeadline);

        recylerHeadline = findViewById(R.id.textViewDateBD_ID);
        calendarZodiacsignHeadline = findViewById(R.id.currentZodiacsignHeadline);

        compCalendarView = (CompactCalendarView) findViewById(R.id.compactCalendarView);

        compCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compCalendarView.getEvents(dateClicked);
                List<Contactdata> bdToday = new ArrayList<>();
                for(Event bd : events){
                    Contactdata contact = (Contactdata) bd.getData();
                    bdToday.add(contact);
                }
                if(bdToday.size() > 0){
                    Log.d("VIEW", bdToday.size() + "geburtstage heute");
                    birthdayContactAdapter = new NextBirthdaysListAdapter(getApplicationContext(), bdToday);
                    contactListView.setAdapter(birthdayContactAdapter);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateClicked);
                    recylerHeadline.setText("Birthdays on "+ dateFormatForDate.format(dateClicked) );

                    calendarZodiacsignHeadline.setText(CalculateStarSign(dateClicked));
                }
                else {
                    Log.d("VIEW", "keine geburtstag heute");
                    List<Contactdata> setNoBDList = new ArrayList<>();
                    setNoBDList.add(new Contactdata());
                    birthdayContactAdapter = new NextBirthdaysListAdapter(getApplicationContext(), setNoBDList);
                    birthdayContactAdapter.onCreateViewHolder(recyclerView,1);
                    contactListView.setAdapter(birthdayContactAdapter);
                    Log.d("MAIN", "After Set Adapter");
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthYearIndicatorText.setText(dateFormatForMonth.format(firstDayOfNewMonth)); //Headline Setzen = Monat und Jahr
                List<Event> events = compCalendarView.getEvents(firstDayOfNewMonth);
                List<Contactdata> bdToday = new ArrayList<>();
                for(Event bd : events){
                    Contactdata contact = (Contactdata) bd.getData();
                    bdToday.add(contact);
                }
                birthdayContactAdapter = new NextBirthdaysListAdapter(getApplicationContext(), bdToday);
                contactListView.setAdapter(birthdayContactAdapter);
                Calendar cal = Calendar.getInstance();
                cal.setTime(firstDayOfNewMonth);
                recylerHeadline.setText("Birthdays on "+ dateFormatForDate.format(firstDayOfNewMonth) );

                calendarZodiacsignHeadline.setText(CalculateStarSign(firstDayOfNewMonth));
            }
        });

        monthYearIndicatorText.setText(dateFormatForMonth.format(compCalendarView.getFirstDayOfCurrentMonth()));
        calendarZodiacsignHeadline.setText(CalculateStarSign(Calendar.getInstance().getTime()));

        for(int i = 0; i<contactList.size(); i++){
            Date tempBD = contactList.get(i).getBirthdayDate();
            Calendar calNextBD = Calendar.getInstance();
            Calendar calBD = Calendar.getInstance();
            calBD.setTime(tempBD);
            calNextBD.set(Calendar.getInstance().get(Calendar.YEAR), calBD.get(Calendar.MONTH), calBD.get(Calendar.DAY_OF_MONTH));
            Event event = new Event(R.color.colorAccent, calNextBD.getTimeInMillis(), contactList.get(i));
            compCalendarView.addEvent(event);
        }

        compCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem tempItem = menu.getItem(1) ;

        SpannableString spanString = new SpannableString(tempItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0,     spanString.length(), 0); //fix the color to white

        tempItem.setTitle(spanString);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.profil_settings){
            Toast.makeText(MainActivity.this, "Profil", Toast.LENGTH_SHORT).show();
            openProfile();
        }

        if(id == R.id.contact_settings){
            Toast.makeText(MainActivity.this, "Contacts", Toast.LENGTH_SHORT).show();
            openContactListActivity();
        }

        if(id == R.id.zodiacsign_settings){
            Toast.makeText(MainActivity.this, "Zodiacsigns", Toast.LENGTH_SHORT).show();
            openZodiacsigns();
        }

        /*
        if(id == R.id.calendar_settings){
            Toast.makeText(MainActivity.this, "Calendar", Toast.LENGTH_SHORT).show();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        birthdayContactAdapter.notifyDataSetChanged();

        Intent intent = new Intent(this, CheckService.class);
        startService(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();



        onlyNext3Bds = new ArrayList<Contactdata>();

        List<Contactdata> tempList;
        tempList = db.getAllContacts();

        tempList.sort(new Comparator<Contactdata>() {
            @Override
            public int compare(Contactdata c1, Contactdata c2) {

                if(c1.CalculateDaysTillBD(c1.getBirthdayDate()) < c2.CalculateDaysTillBD(c2.getBirthdayDate())) {
                    return -1;
                }
                return 0;
            }
        });

        for(int i = 0; i<3; i++){
            if(tempList.size() > i){
                onlyNext3Bds.add(tempList.get(i));
            }
        }

        compCalendarView.removeAllEvents();
        for(int i = 0; i<contactList.size(); i++){
            Date tempBD = contactList.get(i).getBirthdayDate();
            Calendar calNextBD = Calendar.getInstance();
            Calendar calBD = Calendar.getInstance();
            calBD.setTime(tempBD);
            calNextBD.set(Calendar.getInstance().get(Calendar.YEAR), calBD.get(Calendar.MONTH), calBD.get(Calendar.DAY_OF_MONTH));
            Event event = new Event(Color.rgb(222,114,164), calNextBD.getTimeInMillis(), contactList.get(i));
            compCalendarView.addEvent(event);
        }

        birthdayContactAdapter.notifyDataSetChanged();
        birthdayContactAdapter = new NextBirthdaysListAdapter(this, onlyNext3Bds);
        contactListView.setAdapter(birthdayContactAdapter);


    }

    public void openProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
/*
    public void openContactActivity(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }*/

    public void addContactToDatabase (Contactdata contact){
        db.addContact(contact);
    }

    public  ArrayList  getAllContactsFromDatabase(){
        ArrayList<Contactdata> listOfContacts = new ArrayList<>(db.getAllContacts().size());
        listOfContacts.addAll(db.getAllContacts());
        return  listOfContacts;
    }

    public void openContactListActivity(){
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void openZodiacsigns(){
        Intent intent = new Intent(this, ZodiacsignsActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    private void OrderContactsByBirthday(){
        for(int i=0; i<=contactList.size(); i++){

        }
    }

    public void openContactActivity(Contactdata _contact){
        Intent intent = new Intent(this, ContactActivity.class);
        Bundle bundle = new Bundle();

        //Contact daten mitschicken
        bundle.putSerializable("CONTACTDATA", _contact);
        intent.putExtras(bundle);

        //Activity starten
        startActivity(intent);

        //Animation überschreiben
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private String CalculateStarSign(Date date){
        String starSign = "NoStarsignFound";

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if(date.after(new Date(date.getYear(),11,21))
                && date.before(new Date(date.getYear(),11, 32))) {
            starSign = "Capricorn";
            return starSign;
        }
        if(date.after(new Date(date.getYear(),0,0))
                && date.before(new Date(date.getYear(),0, 21))) {
            starSign = "Capricorn";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),0,20))
                && date.before(new Date(date.getYear(),1, 20))){
            starSign = "Aquarius";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),1,19))
                && date.before(new Date(date.getYear(),2, 21))){
            starSign = "Pisces";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),2,20))
                && date.before(new Date(date.getYear(),3, 21))){
            starSign = "Aries";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),3,20))
                && date.before(new Date(date.getYear(),4, 21))){
            starSign = "Taurus";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),4,20))
                && date.before(new Date(date.getYear(),5, 22))){
            starSign = "Gemini";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),5,21))
                && date.before(new Date(date.getYear(),6, 23))){
            starSign = "Cancer";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),6,22))
                && date.before(new Date(date.getYear(),7, 24))){
            starSign = "Leo";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),7,23))
                && date.before(new Date(date.getYear(),8, 24))){
            starSign = "Virgo";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),8,23))
                && date.before(new Date(date.getYear(),9, 24))){
            starSign = "Libra";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),9,23))
                && date.before(new Date(date.getYear(),10, 23))){
            starSign = "Scorpio";
            return starSign;
        }
        else if(date.after(new Date(date.getYear(),10,22))
                && date.before(new Date(date.getYear(),11, 22))){
            starSign = "Sagittarius";
            return starSign;
        }
        else {
            starSign = "Kacke";
            return starSign;
        }

    }

}
