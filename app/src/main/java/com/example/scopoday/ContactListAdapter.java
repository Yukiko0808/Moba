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
import android.os.Handler;


public class ContactListAdapter extends ArrayAdapter<Contact> {

    private static final String TAG = "ContactListAdapter";

    private Context mContext;
    private Handler handler;
    private Runnable runnable;
    int mResource;
    long daysuntilbirthday;
    private int lastPosition = -1;
    DateFormat theFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");

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
        tvCountdown.setText((Long.toString(countDownStart(birthdate))));
        return convertView;

    }

    public long countDownStart(Date BD) {
        handler = new Handler();
        final Date futureBDate = BD;
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = futureBDate;
                    Date currentDate = new Date();
                    if (currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();

                        Log.d("Current Date: ", Integer.toString(currentDate.getDate())) ;

                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;

                        daysuntilbirthday = days;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);

        Log.d("Tage: ", Long.toString(daysuntilbirthday)) ;

        return daysuntilbirthday;

    }



    /*private int CalculateDaysTillBD (Date bd){

        int days = 0;

        Calendar calendar = Calendar.getInstance();
        Date fromToday = calendar.getTime();
        Date toFutureBD = new Date(fromToday.getYear(), bd.getMonth(), bd.getDate());


        if(toFutureBD.getMonth() < fromToday.getMonth()){
            toFutureBD = new Date(fromToday.getYear() + 1, bd.getMonth(), bd.getDate());

        }
        else if(toFutureBD.getMonth() == fromToday.getMonth()){


            if(toFutureBD.getDate() <= fromToday.getDate()){
                toFutureBD = new Date (fromToday.getYear() + 1, bd.getMonth(), bd.getDate());
            }

        }


        calendar.setTime(toFutureBD);
        //calendar.setTime(toFutureBD);             //set calender auf Geburtstag
        int toYear = toFutureBD.getYear(); // toYear =  GeburtstagsJahr
        days += calendar.get(Calendar.DAY_OF_YEAR); // tage + Tage des Jahres

                             Log.d("Tage: ", Integer.toString(days)) ;                      // tage = anfang Jahr bis Geburtstag im Geburtstasjahr

        calendar.setTimeInMillis(fromToday.getTime()); //set calender auf  heute
        days -= calendar.get(Calendar.DAY_OF_YEAR);     // tage - Tage des Jahres

        while (calendar.get(Calendar.YEAR) < toYear) {
            days += calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            calendar.add(Calendar.YEAR, 1);
        }

        Log.d("Tage: ", Integer.toString(days)) ;                      // tage = anfang Jahr bis Geburtstag im Geburtstasjahr

        return  days;

    } */



    /*public static int numDaysBetween(final Calendar c, final long fromTime, final long toTime) {
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
    }*/



}
