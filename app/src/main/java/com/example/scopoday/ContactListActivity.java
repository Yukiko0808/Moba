package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class ContactListActivity extends AppCompatActivity {

    ArrayList<Contact> contactList;

    ContactListAdapter contactAdapter;

    Button addButton;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        lv = (ListView) findViewById(R.id.contactListView_ID);

        addButton = findViewById(R.id.addContact_Btn_ID);

        contactList = MainActivity.contactList;

        contactAdapter = new ContactListAdapter( ContactListActivity.this, contactList);

        lv.setAdapter(contactAdapter);

        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
               // contactList.add()
                AddContact();
            }
        };

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.transmittedContact = contactList.get(i);
                openContactActivity();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                /*SparseBooleanArray positionchecker = lv.getCheckedItemPositions();
                int count = lv.getCount();
                for(int item = count-1;item>=0;item--){
                    if(positionchecker.get(item)){
                        Log.d("Deleted Item", Integer.toString(item));
                        contactAdapter.remove(contactList.get(item));
                        Toast.makeText(ContactListActivity.this, "Item delete Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                positionchecker.clear(); */

                contactAdapter.remove(contactList.get(position));

                contactAdapter.notifyDataSetChanged();
                return false;
            }
        });

        addButton.setOnClickListener(addListener);
        lv.setAdapter(contactAdapter);
    }

    protected void onStart() {
        super.onStart();
        //transmittedContact = null;
        contactAdapter.notifyDataSetChanged();
    }

    public void AddContact() {
        Contact sampleContact = new Contact(contactList.size(),"Name", new Date(2000,00,01));
        contactList.add(sampleContact);
        MainActivity.transmittedContact = sampleContact;
        MainActivity.transmittedContactPosition = contactList.size()-1;
        openContactActivity();
    }

    public void openContactActivity(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
