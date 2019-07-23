package com.example.scopoday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    private static final String TAG = "ContactListAdapter";

    private Context mContext;
    int mResource;
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

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvBirthdate = (TextView) convertView.findViewById(R.id.textView2);

        tvName.setText(name);
        //tvBirthdate.setText(birthdate.toString());
        tvBirthdate.setText(theFormat.format(birthdate));

        return convertView;

    }
}
