package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContactActivity extends AppCompatActivity {

    TextView contactNameText;
    TextView contactBirthday;
    TextView contactAge;
    SimpleDateFormat sdf = new SimpleDateFormat("dd_mm_yyyy hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactNameText = findViewById(R.id.ContactName_TV_ID);
        contactNameText.setText(MainActivity.transmittedContact.getName());

        contactBirthday = findViewById(R.id.ContactBirthday_TV_ID);
        contactBirthday.setText(Integer.toString(MainActivity.transmittedContact.getBirthdate().getDate()) + "."
                                + Integer.toString(MainActivity.transmittedContact.getBirthdate().getMonth()) + "."
                                + Integer.toString(MainActivity.transmittedContact.getBirthdate().getYear())
        );

        contactAge = findViewById(R.id.ContactAlter_TV_ID);
        String ageString = Integer.toString(CalculateAge());
        contactAge.setText(ageString);

     }

     private int CalculateAge(){

        Log.d("Date of Contact", Integer.toString(MainActivity.transmittedContact.getBirthdate().getYear()));

        //hier müsste eigentlich noch das genaue datum berüchsichtigt werden
         int age =  Calendar.getInstance().get(Calendar.YEAR) - MainActivity.transmittedContact.getBirthdate().getYear();

        return age;
     }
}
