package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;

public class ContactListActivity extends AppCompatActivity {

    ArrayList<Contactdata> contactList;

    public static ContactListAdapter contactAdapter;

    Button addButton;

    ListView lv;

    int tempPos;

    MySQLHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        //Datenbank helfer erstellen
        db = new MySQLHelper(this);

        //Contakte aus der db in conactList einfügen
        ArrayList<Contactdata> listOfContacts = new ArrayList<>(db.getAllContacts().size());
        listOfContacts.addAll(db.getAllContacts());
        contactList = listOfContacts;

        /*
        Window window = this.getWindow();
        // Anzeigeleiste Oben ändern
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(this.getResources().getColor(R.color.cardview_shadow_end_color));
        */

        //Kontakte hinzufügen mit Add Button

        addButton = findViewById(R.id.addContact_Btn_ID);

        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Kontakt aus Telefon Kontakten hinzufügen
                // AddContact();

                // Popup window um Kontakt hinzufügt
                openAddContactPopupWindow(v);
            }
        };

        addButton.setOnClickListener(addListener);

        //Kontakt liste anzeigen

        lv = (ListView) findViewById(R.id.contactListView_ID);

        contactAdapter = new ContactListAdapter( this, contactList);

        lv.setAdapter(contactAdapter);

        //Klick auf Kontakt -> Kontakt öffnen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //MainActivity.transmittedContact = contactList.get(i);

                openContactActivity(contactList.get(i));
            }
        });

        //Langes Antippen auf Kontakt -> Context Menü öffnen
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                tempPos = position;
                registerForContextMenu(lv);

                //contactAdapter.remove(contactList.get(position));

                contactAdapter.notifyDataSetChanged();
                return false;
            }
        });
        lv.setAdapter(contactAdapter);
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
            Toast.makeText(this, "Profil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }

        if(id == R.id.contact_settings){
            Toast.makeText(this, "contacts", Toast.LENGTH_SHORT).show();

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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Manage contact");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(1, v.getId(), 0, "Cancel");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle() + item.getGroupId(), Toast.LENGTH_SHORT).show();
        if(item.getGroupId() == 0){
            //Löschen des Kontakts
            db.deleteContacts(contactList.get(tempPos));
            contactAdapter.remove(contactList.get(tempPos));

        }
        if(item.getGroupId() == 1){
            return true;
        }
        return true;
    }

    protected void onStart() {
        super.onStart();

        //transmittedContact = null;
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //contactAdapter.notifyDataSetChanged();

        ArrayList<Contactdata> listOfContacts = new ArrayList<>(db.getAllContacts().size());
        listOfContacts.addAll(db.getAllContacts());
        contactList = listOfContacts;

        contactAdapter = new ContactListAdapter( this, contactList);

        lv.setAdapter(contactAdapter);
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

    public void openAddContactPopupWindow(View view){

        AddContactDialog addContactDialog = new AddContactDialog();
        addContactDialog.show(getSupportFragmentManager(), "Add Contact");

    }





}
