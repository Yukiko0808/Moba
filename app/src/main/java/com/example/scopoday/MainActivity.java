package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    Button addContactButton;

    public ArrayList<Contact> contactList = new ArrayList<>();



    //ArrayAdapter adapter;

    //DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // createContactButtons();
        setContentView(R.layout.activity_main);



        ListView contactListView = findViewById(R.id.ContantList_ID);

        Contact john = new Contact("John", new Date(1999,05,03));
        Contact lisa = new Contact("Lisa", new Date(2000,8,04));
        Contact markus = new Contact("Markus", new Date(1990,03,03));
        Contact lukas = new Contact("Lukas", new Date(2001,07,24));
        Log.d("Date of John", Integer.toString(john.getBirthdate().getDate()));



        final ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(john);
        contactArrayList.add(lisa);
        contactArrayList.add(markus);
        contactArrayList.add(lukas);

        contactList = contactArrayList;

        ContactListAdapter adapter = new ContactListAdapter(this, R.layout.adapter_view_layout, contactList);
        contactListView.setAdapter(adapter);


        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, ""+ contactList.get(i).getName(), Toast.LENGTH_SHORT).show();
                transmittedContact = contactList.get(i);
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

    public void openContactActivity(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    public void AddContact() {
        //this.contactList;
        //Contact timo = new Contact("Timo", new Date(1995,12,9));
        //contactList.add(timo);

        //irgendwie nicht direkt ändern sondern mit sowas da ->notifyDataSetChanged()

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
