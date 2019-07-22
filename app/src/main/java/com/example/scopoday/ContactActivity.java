package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;

public class ContactActivity extends AppCompatActivity {

    TextView contactNameText;
    TextView contactBirthday;
    SimpleDateFormat sdf = new SimpleDateFormat("dd_mm_yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactNameText = findViewById(R.id.ContactName_TV_ID);
        contactNameText.setText(MainActivity.transmittedContact.getName());

        contactBirthday = findViewById(R.id.ContactBirthday_TV_ID);
        contactBirthday.setText(sdf.format(MainActivity.transmittedContact.getBirthdate()));

     }

     private int CalculateAge(){
        return 1;
     }
}
