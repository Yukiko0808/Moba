package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView contactText;
    String contactInfo;
    public static Contact transmittedContact;
    public static int transmittedContactPosition;
    public static MainActivity mainActivity;

    Button addContactButton;
    ImageButton addProfileButton;

    ListView contactListView;
    public static ArrayList<Contact> contactList;
    ContactListAdapter mainAdapter;



    //ArrayAdapter adapter;

    //DatabaseHelper db = new DatabaseHelper(this);

    private MySQLHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;

        setContentView(R.layout.activity_main);
        // createContactButtons();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 10);

        }


        // wenn man sich in einem Fragment befindet, wird nicht "this" übergeben sondern "getContext()"
        db = new MySQLHelper(this);

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


       // contactList = null;
        contactListView = findViewById(R.id.ContantList_ID);

        if(contactList == null) contactList = new ArrayList<>();    //Falls Permissions abgelehnt wurden, eine leere Liste erstellen.

        //ContactListAdapter adapter = new ContactListAdapter(this, R.layout.adapter_view_layout, contactList);
        ContactListAdapter adapter = new ContactListAdapter(this, contactList);
        contactListView.setAdapter(adapter);
        mainAdapter = adapter;

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, ""+ contactList.get(i).getName(), Toast.LENGTH_SHORT).show();
                transmittedContact = contactList.get(i);
                transmittedContactPosition = i;
                openContactActivity();
            }
        });

        contactText = findViewById(R.id.textView_ID);


        addContactButton = (Button) findViewById(R.id.AddContactButton_ID);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Open Contact list", Toast.LENGTH_SHORT).show();
                openContactListActivity();
                //AddContact();
            }
        });

        /*addProfileButton = (ImageButton) findViewById(R.id.ProfileButton_ID);

        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Zum Profil", Toast.LENGTH_SHORT).show();
                openProfile();
            }
        });*/

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
            Toast.makeText(MainActivity.this, "Profil", Toast.LENGTH_SHORT).show();
            openProfile();
        }

        if(id == R.id.contact_settings){
            Toast.makeText(MainActivity.this, "Kontakte", Toast.LENGTH_SHORT).show();
            openContactListActivity();
        }

        if(id == R.id.calendar_settings){
            Toast.makeText(MainActivity.this, "Kalender", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //transmittedContact = null;
        mainAdapter.notifyDataSetChanged();
    }

    public void openProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void openContactActivity(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
/*
    public void AddContact() {
        Contact sampleContact = new Contact(contactList.size(),"Name", new Date(2000,00,01));
        contactList.add(sampleContact);
        transmittedContact = sampleContact;
        transmittedContactPosition = contactList.size()-1;
        Log.d("TransmittedContactPostition", Integer.toString(contactList.size()));
        openContactActivity();
    }*/

    public void openContactListActivity(){
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void OrderContactsByBirthday(){
        for(int i=0; i<=contactList.size(); i++){

        }
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

}
