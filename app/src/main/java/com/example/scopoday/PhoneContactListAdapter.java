package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PhoneContactListAdapter extends  ArrayAdapter<PhoneContacts> {


    public PhoneContactListAdapter(Context context, ArrayList<PhoneContacts> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhoneContacts phContact = getItem(position);
        Log.i("SetName", phContact.name);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_phone_contact_list_adapter, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        tvName.setText(phContact.name);

        return convertView;
    }
}
