package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.view.View;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContactActivity extends AppCompatActivity {

    EditText contactNameText;
    TextView contactBirthday;
    TextView contactAge;
    SimpleDateFormat sdf = new SimpleDateFormat("dd_mm_yyyy hh:mm:ss");
    private DatePickerDialog datePickerDialog;

    private int year,month,day;

    Button btDatepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactNameText = findViewById(R.id.ContactName_TV_ID);
        contactNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        contactNameText.setText(MainActivity.transmittedContact.getName());

        contactBirthday = findViewById(R.id.ContactBirthday_TV_ID);
        contactBirthday.setText(Integer.toString(MainActivity.transmittedContact.getBirthdate().getDate()) + "."
                                + Integer.toString(MainActivity.transmittedContact.getBirthdate().getMonth()+1) + "."
                                + Integer.toString(MainActivity.transmittedContact.getBirthdate().getYear())
        );

        contactAge = findViewById(R.id.ContactAlter_TV_ID);
        String ageString = Integer.toString(CalculateAge());
        contactAge.setText(ageString);

        //Datepicker

        //contactBirthday

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        contactBirthday.setText("Datum: " + day + "." + month + "." + year);
                        Log.d("datum gewählt:",  Integer.toString(day)+Integer.toString(month)+Integer.toString(year));
                    }
                }, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));

     }

     private int CalculateAge(){

        Log.d("Date of Contact", Integer.toString(MainActivity.transmittedContact.getBirthdate().getYear()));

        //hier müsste eigentlich noch das genaue datum berüchsichtigt werden
         int age =  Calendar.getInstance().get(Calendar.YEAR) - MainActivity.transmittedContact.getBirthdate().getYear();

        return age;
     }
}
