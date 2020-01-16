package com.example.scopoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NextBirthdaysListAdapter extends RecyclerView.Adapter<NextBirthdaysListAdapter._ViewHolder> {

    TextView contactName;
    TextView daysCounter;

    Context context;

    private  List<Contactdata> dataset;


    public static class _ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View theView;
        public _ViewHolder(View v) {
            super(v);
            theView = v;
        }
    }

    public  NextBirthdaysListAdapter (Context _context, List<Contactdata> data){
        context = _context;
        dataset = data;
    }

    @NonNull
    @Override
    public _ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_phone_contact_list_adapter, parent, false);


        contactName = v.findViewById(R.id.textViewName);
        daysCounter = v.findViewById(R.id.textViewDays);

        _ViewHolder vh = new _ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull _ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("CONTACTDATA", dataset.get(position));
                intent.putExtras(bundle);

                //Activity starten
                context.startActivity(intent);
            }
        });

        Contactdata tempContact  = dataset.get(position);
        contactName.setText(tempContact.name);
        Date bd = tempContact.getBirthdayDate();
        Long days = tempContact.CalculateDaysTillBD(tempContact.getBirthdayDate());

        daysCounter.setText(Long.toString(days));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }




}
