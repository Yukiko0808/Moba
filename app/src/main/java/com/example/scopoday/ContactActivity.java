package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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
    TextView starSignText;
    ImageView zodiacsign;

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


        zodiacsign = findViewById(R.id.zodiacsign);


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

        starSignText =  findViewById(R.id.starSign_TV_ID);
        starSignText.setText(CalculateStarSign());

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

    private String CalculateStarSign(){
        String starSign = "NoStarsignFound";
        Contact actualContact = MainActivity.transmittedContact;
        if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),11,21))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),11, 32))) {
            // Steinbock - capricorn
            starSign = "capricorn";
            zodiacsign.setImageResource(R.drawable.capricorn_black);
            return starSign;

        }

        if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),0,0))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),0, 21))) {
            // Steinbock - capricorn
            starSign = "capricorn";
            zodiacsign.setImageResource(R.drawable.capricorn_black);
            return starSign;
        }
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),0,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),1, 20))){
            //Wassermann - aquarius
            starSign = "aquarius";
            zodiacsign.setImageResource(R.drawable.aquarius_black);
            return starSign;
        }
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),1,19))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),2, 21))){
            //fish
            starSign = "pisces";
            zodiacsign.setImageResource(R.drawable.pisces_black);
            return starSign;
        }
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),2,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),3, 21))){
            //widder - aries
            starSign = "aries";
            zodiacsign.setImageResource(R.drawable.aries_black);
            return starSign;

        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),3,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),4, 21))){
            //fish
            starSign = "taurus";
            zodiacsign.setImageResource(R.drawable.taurus_black);
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),4,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),5, 22))){
            //fish
            starSign = "gemini";
            zodiacsign.setImageResource(R.drawable.gemini_black);
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),5,21))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),6, 23))){
            //fish
            starSign = "cancer";
            zodiacsign.setImageResource(R.drawable.cancer_black);
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),6,22))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),7, 24))){
            //fish
            starSign = "lio";
            zodiacsign.setImageResource(R.drawable.leo_black);
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),7,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),8, 24))){
            //fish
            starSign = "virgo";
            zodiacsign.setImageResource(R.drawable.virgo_black);
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),8,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),9, 24))){
            //fish
            starSign = "libra";
            zodiacsign.setImageResource(R.drawable.libra_black);
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),9,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),10, 23))){
            //fish
            starSign = "scorpio";
            zodiacsign.setImageResource(R.drawable.scorpio_black);
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),10,22))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),11, 22))){
            //fish
            starSign = "sagittarius";
            zodiacsign.setImageResource(R.drawable.sagittarius_black);
            return starSign;
        }
        else {
            return starSign;
        }
    }



    private void SetBirthdateInMain(Date d){
        Contact newContact = MainActivity.transmittedContact;
        newContact.setBirthdate(d);
        MainActivity.contactList.set(MainActivity.transmittedContactPosition, newContact);
        String ageString = Integer.toString(CalculateAge());
        contactAge.setText(ageString);
        starSignText.setText(CalculateStarSign());
        Log.d("CONTACT", "changed contact:" + MainActivity.transmittedContact.name);
    }

    private void SetNameInMain(String n){
        Contact newContact = MainActivity.transmittedContact;
        newContact.setName(n);
        MainActivity.contactList.set(MainActivity.transmittedContactPosition, newContact);

    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
