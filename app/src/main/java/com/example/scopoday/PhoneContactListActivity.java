package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PhoneContactListActivity extends AppCompatActivity {

    ArrayList<PhoneContacts> phoneContacts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contact_list);


        if(ContextCompat.checkSelfPermission(MainActivity.mainActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            Log.i("Error", "Hat keine  Permissions");
            NextActivity();
        }
        else{
            //Weiter zu Kontakt anlegen
            Log.i("Error", "Hat Permissions");
            phoneContacts = getContactsListFromMobilePhone();
        }

        if(phoneContacts == null){
            Log.i("Error", "PhoneContacts is null");
            NextActivity();
        }

        ArrayList<String> contactNames = new ArrayList<>();
        for(int i = 0; i < phoneContacts.size(); i++){
            contactNames.add(phoneContacts.get(i).name);
        }

        ListView listView = (ListView)findViewById(R.id.phoneContactListID);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contactdata con = new Contactdata(phoneContacts.get(i).name);
                MainActivity.mainActivity.addContactToDatabase(con);
                Log.d("!!! Contact Liste Database", MainActivity.mainActivity.getAllContactsFromDatabase().get(i).toString());

            }
        });

        PhoneContactListAdapter adapter;
        adapter = new PhoneContactListAdapter(this, phoneContacts);
        listView.setAdapter(adapter);

    }

    public ArrayList<PhoneContacts> getContactsListFromMobilePhone(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);

        if(cur == null){
            return null;
        }

        ArrayList<PhoneContacts> contacts = new ArrayList<PhoneContacts>();

        if (cur.getCount()  > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                /*
                Log.i("Contact ID", id);
                Log.i("Contact name", name);*/

                contacts.add(new PhoneContacts(id, name));
            }

            cur.close();
        }

        return contacts;
    }

    public void NextActivity(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }
}
