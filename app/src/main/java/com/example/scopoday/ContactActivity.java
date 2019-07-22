package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContactActivity extends AppCompatActivity {

    TextView contactNameText;
    TextView contactBirthday;
    TextView contactAge;
    SimpleDateFormat sdf = new SimpleDateFormat("dd_mm_yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactNameText = findViewById(R.id.ContactName_TV_ID);
        contactNameText.setText(MainActivity.transmittedContact.getName());

        contactBirthday = findViewById(R.id.ContactBirthday_TV_ID);
        contactBirthday.setText(sdf.format(MainActivity.transmittedContact.getBirthdate()));

        contactAge = findViewById(R.id.ContactAlter_TV_ID);
        String ageString = Integer.toString(CalculateAge());
        contactAge.setText(ageString);



     }

     private int CalculateAge(){

        Date today = Calendar.getInstance().getTime();

        int age =  today.getYear() - MainActivity.transmittedContact.getBirthdate().getYear();


        return age;
     }
}
