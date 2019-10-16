package com.example.scopoday;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Handler;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    private static final String TAG = "ContactListAdapter";

    private Context mContext;
    int mResource;
    private int lastPosition = -1;
    DateFormat theFormat = new SimpleDateFormat("dd-mm-yyyy");


    TextView txtDaysTillBD;
    private Handler handler;
    private Runnable runnable;

    public ContactListAdapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        Date birthdate = getItem(position).getBirthdate();

        Contact contact = new Contact(name, birthdate);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageButton deleteBtn = (ImageButton) convertView.findViewById(R.id.delete_contact);
        deleteBtn.setOnClickListener( new AdapterView.OnClickListener() {
            public void onClick(View v) {
                Log.d("contact deleted", "1");
            }
        });


        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView tvBirthdate = (TextView) convertView.findViewById(R.id.textViewDate);
        TextView tvCountdown = (TextView) convertView.findViewById(R.id.textViewDays);

        tvName.setText(name);
        tvBirthdate.setText(Integer.toString(birthdate.getDate()) + "." +Integer.toString(birthdate.getMonth()+1) + "." + Integer.toString(birthdate.getYear()));
        tvCountdown.setText(Long.toString(CalculateDaysTillBD(birthdate)));
        return convertView;


    }

    private long CalculateDaysTillBD (Date bd){

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        Date birthDate = new Date(0000, bd.getMonth(), bd.getDate());
        Date todayDate = new Date(0000, today.getMonth(), today.getDate());

        if(birthDate.getTime()<todayDate.getTime()){
            birthDate = new Date(1, birthDate.getMonth(), birthDate.getDate());
        }

        long days = birthDate.getTime() - todayDate.getTime();
        days = days / (24 * 60 * 60 * 1000);
        /*
        Log.d("First date", birthDate.toString());
        Log.d("Second Date", todayDate.toString());
        Log.d("Days?" , Long.toString(days));*/

        return  days;
    }


}
