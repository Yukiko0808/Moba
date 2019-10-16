package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
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

    Button addContactButton;
    ImageButton addProfileButton;

    public static ArrayList<Contact> contactList = new ArrayList<>();
    ContactListAdapter mainAdapter;

    Contact john = new Contact(1,"John", new Date(1999,8,17));
    Contact lisa = new Contact(2,"Lisa", new Date(2000,8,25));
    Contact markus = new Contact(3,"Markus", new Date(1990,9,01));
    Contact lukas = new Contact(4,"Lukas", new Date(2001,01,1));


    //ArrayAdapter adapter;

    //DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
       // createContactButtons();
        setContentView(R.layout.activity_main);

        ListView contactListView = findViewById(R.id.ContantList_ID);

        final ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(john);
        contactArrayList.add(lisa);
        contactArrayList.add(markus);
        contactArrayList.add(lukas);

        contactList = contactArrayList;

        ContactListAdapter adapter = new ContactListAdapter(this, R.layout.adapter_view_layout, contactList);
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
        contactText.setText("Alle Kontakte:");

        addContactButton = (Button) findViewById(R.id.AddContactButton_ID);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Neuen Kontakt hinzufügen", Toast.LENGTH_SHORT).show();
                AddContact();
            }
        });

        addProfileButton = (ImageButton) findViewById(R.id.ProfileButton_ID);

        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Zum Profil", Toast.LENGTH_SHORT).show();
                openProfile();
            }
        });


        /*contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, ""+ contactList.get(i).getName(), Toast.LENGTH_SHORT).show();
                transmittedContact = contactList.get(i);
                transmittedContactPosition = i;
                openContactActivity();
            }
        });*/


        //contactList.add(new Contact("Beispiel Name", new Date(2000,04,04)));

        //AddContact(new Contact("Tester", new Date(2000,04,04)));

       // List<Contact> contactList = db.getAllContacts();

           // Log.d("mip", contactList.toString());

        //contactInfo = contactList.get(0).getName();
        //contactText.setText(contactInfo);


        //String nameOfContact = contactList.get(1).getName();
        //Toast.makeText(getApplicationContext(), nameOfContact, Toast.LENGTH_LONG).show();

        //Foreach
        //??? irgend ein fehler mit api veraltet oder so @.@
        /*
        for(Contact c : contactList){
            String log = "NAME: " + c.getName() + "\n";

            contactInfo = contactInfo + log;
            Log.d("test", "ausgabe");
        }*/


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

    public void AddContact() {
        Contact sampleContact = new Contact(contactList.size(),"Name", new Date(2000,00,01));
        contactList.add(sampleContact);
        transmittedContact = sampleContact;
        transmittedContactPosition = contactList.size()-1;
        Log.d("TransmittedContactPostition", Integer.toString(contactList.size()));
        openContactActivity();
    }



    /*
    public void AddContact(Contact contact){

        boolean insertData = db.addContact(contact);

        if(insertData){
            Toast.makeText(getApplicationContext(),
                    "Contact added - in theory", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Contact added FAILED", Toast.LENGTH_LONG).show();
        }
        //db.addContact(contact); //new Contact(name,Date(Jahr,monat,tag))

        //inserting contacts
        //db. addContact(new Contact("TestName", new Date(2000,03,03)));
       //db. addContact(new Contact("Tester", new Date(2000,04,04)));
       // db. addContact(new Contact("TestMan",new Date(2000,05,05)));

    }*/

}
