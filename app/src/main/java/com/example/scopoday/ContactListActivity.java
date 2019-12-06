package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    ArrayList<Contactdata> contactList;

    ContactListAdapter contactAdapter;

    Button addButton;

    ListView lv;


    int tempPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(this.getResources().getColor(R.color.cardview_shadow_end_color));

        addButton = findViewById(R.id.addContact_Btn_ID);


        lv = (ListView) findViewById(R.id.contactListView_ID);

        contactList = MainActivity.mainActivity.getAllContactsFromDatabase();

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

                tempPos = position;
                registerForContextMenu(lv);

                //contactAdapter.remove(contactList.get(position));

                contactAdapter.notifyDataSetChanged();
                return false;
            }
        });

        addButton.setOnClickListener(addListener);
        lv.setAdapter(contactAdapter);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(1, v.getId(), 0, "Cancel");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle() + item.getGroupId(), Toast.LENGTH_SHORT).show();
        if(item.getGroupId() == 0){
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

    public void AddContact() {
        Intent intent = new Intent(this, PhoneContactListActivity.class);
        startActivity(intent);
    }

    public void openContactActivity(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }




}
