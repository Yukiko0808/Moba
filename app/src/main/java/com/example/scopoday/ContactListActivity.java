package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    ArrayList<Contactdata> contactList;

    public static ContactListAdapter contactAdapter;

    Button addButton;

    ListView lv;

    int tempPos;

    MySQLHelper db;

    ArrayList<Contactdata> sortedContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        //Datenbank helfer erstellen
        db = new MySQLHelper(this);

        //Contakte aus der db in conactList einfügen
        ArrayList<Contactdata> listOfContacts = new ArrayList<>(db.getAllContacts().size());
        listOfContacts.addAll(db.getAllContacts());
        /*for (Contactdata contact : listOfContacts) {
            if(contact.getName().equals("Me")){
                listOfContacts.remove(contact);
            }
        }*/
        contactList = listOfContacts;




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

                if(contactList.get(i).getName().equals("Me")){
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                }
                else {
                    openContactActivity(contactList.get(i));
                }

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

        MenuItem tempItem = menu.getItem(2) ;

        SpannableString spanString = new SpannableString(tempItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0,     spanString.length(), 0); //fix the color to white

        tempItem.setTitle(spanString);
/*
        ArrayList<Contactdata> listOfContacts = new ArrayList<>(db.getAllContacts().size());
        listOfContacts.addAll(db.getAllContacts());
        contactList = listOfContacts;
*/

        return true;
    }


    enum sortType{
        BY_NAME,
        BY_DAYS_TO_BD,
        BY_CALENDER_DATE
    }

    public ArrayList<Contactdata> sortListBy(sortType st){

        ArrayList<Contactdata> tempArrayList = new ArrayList<>();

        List<Contactdata> orderdList = contactList;

        // Nach Tagen bis zum Geburtstag sortieren
        if(st == sortType.BY_DAYS_TO_BD){
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
                    sortedContactList.add(orderdList.get(i));
                }
            }
            return sortedContactList;
        }

        // Nach Geburtsdatum Sortieren
        if(st == sortType.BY_CALENDER_DATE){

            final Calendar cal1 = Calendar.getInstance();
            final Calendar cal2  = Calendar.getInstance();


            orderdList.sort(new Comparator<Contactdata>() {
                @Override
                public int compare(Contactdata c1, Contactdata c2) {

                    cal1.setTime(c1.getBirthdayDate());
                    cal2.setTime(c2.getBirthdayDate());

                    if(cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
                        return -1;
                    }
                    return 0;
                }
            });

            for(int i = 0; i<3; i++){
                if(orderdList.size() > i){
                    sortedContactList.add(orderdList.get(i));
                }
            }
            return sortedContactList;
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.profil_settings){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

        if(id == R.id.contact_settings){
            //Toast.makeText(this, "contacts", Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.zodiacsign_settings){
            Intent intent = new Intent(this, ZodiacsignsActivity.class);
            startActivity(intent);
        }


        if(id == R.id.calendar_settings){
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
        //Toast.makeText(this, "Selected Item: " +item.getTitle() + item.getGroupId(), Toast.LENGTH_SHORT).show();
        if(item.getGroupId() == 0){
            //Löschen des Kontakts
            db.deleteContacts(contactList.get(tempPos));
            //contactAdapter.remove(contactList.get(tempPos));
            contactList.remove(contactList.get(tempPos));
            contactAdapter = new ContactListAdapter(this,contactList);
            lv.setAdapter(contactAdapter);
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
        /*for (Contactdata contact : listOfContacts) {
            if(contact.getName().equals("Me")){
                listOfContacts.remove(contact);
            }
        }*/
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
        //overridePendingTransition(android.R.transition.fade, android.R.transition.fade);
    }

    public void openAddContactPopupWindow(View view){

        AddContactDialog addContactDialog = new AddContactDialog();
        addContactDialog.show(getSupportFragmentManager(), "Add Contact");

    }




}
