package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import android.view.View;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import android.view.inputmethod.InputMethodManager;

public class ContactActivity extends AppCompatActivity {

    EditText contactNameText;
    TextView contactAge;
    TextView contactBirthdayTV;

    //SimpleDateFormat sdf = new SimpleDateFormat("dd_mm_yyyy hh:mm:ss");
    private DatePickerDialog.OnDateSetListener mdateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactNameText = findViewById(R.id.ContactName_TV_ID);
        contactNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactNameText.selectAll();
            }
        });
        contactNameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE){
                    Log.d("CONTACT_CHANGE_NAME", "neuer name:" + contactNameText.getText().toString());
                    SetNameInMain(contactNameText.getText().toString());
                    handled = true;
                    InputMethodManager imm = (InputMethodManager)getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(contactNameText.getWindowToken(), 0);
                }
                return handled;
            }
        });

        contactNameText.setText(MainActivity.transmittedContact.getName());

        contactAge = findViewById(R.id.ContactAlter_TV_ID);
        String ageString = Integer.toString(CalculateAge());
        contactAge.setText(ageString);

        //Datepicker

        contactBirthdayTV = (TextView) findViewById(R.id.contactDatepicker_TV_ID);

        contactBirthdayTV.setText(Integer.toString(MainActivity.transmittedContact.getBirthdate().getDate()) + "."
                + Integer.toString(MainActivity.transmittedContact.getBirthdate().getMonth()+1) + "."
                + Integer.toString(MainActivity.transmittedContact.getBirthdate().getYear())
        );

        contactBirthdayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                //int year = cal.get(Calendar.YEAR);
                int year = MainActivity.transmittedContact.birthdate.getYear();
                int month = MainActivity.transmittedContact.birthdate.getMonth();
                int day = MainActivity.transmittedContact.birthdate.getDate();
               // int month = cal.get(Calendar.MONTH);
                //int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ContactActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mdateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day +"."+ month +"."+ year;
                contactBirthdayTV.setText(date);
                SetBirthdateInMain(new Date(year,month-1,day));
            }
        };
     }

     private int CalculateAge(){

        Log.d("Date of Contact", Integer.toString(MainActivity.transmittedContact.getBirthdate().getYear()));

        //hier müsste eigentlich noch das genaue datum berüchsichtigt werden
         int age =  Calendar.getInstance().get(Calendar.YEAR) - MainActivity.transmittedContact.getBirthdate().getYear();

        return age;
    }

    private void SetBirthdateInMain(Date d){
        Contact newContact = MainActivity.transmittedContact;
        newContact.setBirthdate(d);
        MainActivity.contactList.set(MainActivity.transmittedContactPosition, newContact);
        String ageString = Integer.toString(CalculateAge());
        contactAge.setText(ageString);
        Log.d("CONTACT", "changed contact:" + MainActivity.transmittedContact.name);
    }

    private void SetNameInMain(String n){
        Contact newContact = MainActivity.transmittedContact;
        newContact.setName(n);
        MainActivity.contactList.set(MainActivity.transmittedContactPosition, newContact);

    }





}
