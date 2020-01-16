package com.example.scopoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NextBirthdaysListAdapter extends RecyclerView.Adapter<NextBirthdaysListAdapter._ViewHolder> {


    public static class _ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View theView;
        public _ViewHolder(View v) {
            super(v);
            theView = v;
        }
    }



    private Context mContext;
/*
    public NextBirthdaysListAdapter(Context context, ArrayList<Contactdata> objects) {
        super(context, 0, objects);
        mContext = context;
    }*/

    private  List<Contactdata> dataset;

    public  NextBirthdaysListAdapter (Context context, List<Contactdata> data){
        dataset = data;
    }


     /*
        Contactdata contact = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_phone_contact_list_adapter, parent, false); //activity_phone_contact_list_adapter, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        // tvName.setText(contact.name);

        return convertView;
        */

    @NonNull
    @Override
    public _ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_phone_contact_list_adapter, parent, false);

        _ViewHolder vh = new _ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull _ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*Contactdata phContact = getItem(position);
        Log.i("SetName", phContact.name);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_phone_contact_list_adapter, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        tvName.setText(phContact.name);

        return convertView;*/
    //}*/


}
