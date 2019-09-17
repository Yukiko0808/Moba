package com.example.scopoday;

import android.content.Context;
import android.util.Log;
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
        tvCountdown.setText(Integer.toString(CalculateDaysTillBD(birthdate)));

        return convertView;

    }

    private int CalculateDaysTillBD (Date bd){

        int days = 0;

        Calendar calendar = Calendar.getInstance();
        Date fromToday = calendar.getTime();
        Date toFutureBD = new Date(fromToday.getYear(), bd.getMonth(), bd.getDate());

        if(toFutureBD.getMonth() <= fromToday.getMonth()){
            toFutureBD = new Date(fromToday.getYear() + 1, bd.getMonth(), bd.getDate());
        }

        //calendar.setTimeInMillis(toFutureBD.getTime());
        calendar.setTime(toFutureBD);             //set calender auf Geburtstag
        int toYear = calendar.get(Calendar.YEAR); // toYear =  GeburtstagsJahr
        days += calendar.get(Calendar.DAY_OF_YEAR); // tage + Tage des Jahres
                                                    // tage = anfang Jahr bis Geburtstag im Geburtstasjahr

        calendar.setTimeInMillis(fromToday.getTime()); //set calender auf  heute
        days -= calendar.get(Calendar.DAY_OF_YEAR);     // tage - Tage des Jahres

        while (calendar.get(Calendar.YEAR) < toYear) {
            days += calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            calendar.add(Calendar.YEAR, 1);
        }
        return  days;
    }

    public static int numDaysBetween(final Calendar c, final long fromTime, final long toTime) {
        int result = 0;
        if (toTime <= fromTime) return result;

        c.setTimeInMillis(toTime);
        final int toYear = c.get(Calendar.YEAR);
        result += c.get(Calendar.DAY_OF_YEAR);

        c.setTimeInMillis(fromTime);
        result -= c.get(Calendar.DAY_OF_YEAR);

        while (c.get(Calendar.YEAR) < toYear) {
            result += c.getActualMaximum(Calendar.DAY_OF_YEAR);
            c.add(Calendar.YEAR, 1);
        }

        return result;
    }



}
