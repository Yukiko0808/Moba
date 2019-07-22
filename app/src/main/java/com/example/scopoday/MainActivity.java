package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView contactText;
    String contactInfo;
    public static Contact transmittedContact;



    //ArrayAdapter adapter;

    //DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // createContactButtons();
        Log.d(TAG, "onCreate: Started.");
        ListView contactListView = findViewById(R.id.ContantList_ID);


        Contact john = new Contact("John", new Date(1999,05,03));
        Contact lisa = new Contact("Lisa", new Date(2000,04,04));
        Contact markus = new Contact("Markus", new Date(1990,03,03));
        Contact lukas = new Contact("Lukas", new Date(2001,07,24));

        final ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(john);
        contactArrayList.add(lisa);
        contactArrayList.add(markus);
        contactArrayList.add(lukas);


        ContactListAdapter adapter = new ContactListAdapter(this, R.layout.adapter_view_layout, contactArrayList);
        contactListView.setAdapter(adapter);


        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, ""+ contactArrayList.get(i).getName(), Toast.LENGTH_SHORT).show();
                transmittedContact = contactArrayList.get(i);
                openContactActivity();
            }
        });

        contactText = findViewById(R.id.textView_ID);
        contactText.setText("Alle Kontakte:");

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

    public void AddContact(Contact contact){

        /*
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

        */

        //inserting contacts
        //db. addContact(new Contact("TestName", new Date(2000,03,03)));
       //db. addContact(new Contact("Tester", new Date(2000,04,04)));
       // db. addContact(new Contact("TestMan",new Date(2000,05,05)));


    }

    private void createContactButtons(){
        /*Button userButton = findViewById(R.id.bt_contact_ID);
        userButton.setText(getResources().getString(R.string.userName));*/

    }
}
