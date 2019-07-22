package com.example.scopoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    private static final String TAG = "ContactListAdapter";

    private Context mContext;
    int mResource;
    private int lastPosition = -1;
    DateFormat theFormat = new SimpleDateFormat("dd-mm-yyyy");

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

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView tvBirthdate = (TextView) convertView.findViewById(R.id.textViewDate);
        TextView tvCountdown = (TextView) convertView.findViewById(R.id.textViewDays);

        tvName.setText(name);
        tvBirthdate.setText(Integer.toString(birthdate.getDate()) + "." +Integer.toString(birthdate.getMonth()) + "." + Integer.toString(birthdate.getYear()));
        tvCountdown.setText(Long.toString(CalculateDaysTillBD(birthdate)));

        return convertView;

    }

    private long CalculateDaysTillBD (Date bd){

        Calendar today = Calendar.getInstance();
        Date futureBD = new Date(today.get(Calendar.YEAR),bd.getMonth(), bd.getDate());

        if(futureBD.getMonth() <= today.get(Calendar.MONTH)){
            futureBD.setYear(Calendar.YEAR + 1);
        }


        final long mills = futureBD.getTime() - today.getTimeInMillis();

        final long days = (int )mills/86400000; //24*60*60*1000

        return  days;
    }

}
