package com.example.scopoday;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        View v = null;
        switch (viewType){
            case 0:
                Log.d("VIEW", "Type 0");
                v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contact_calender_adapter, parent, false);
                contactName = v.findViewById(R.id.textViewName);
                daysCounter = v.findViewById(R.id.textViewDays);
                break;
            case 1:
                Log.d("VIEW", "Type 1");
                v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contact_calendar_no_birthdays_adapter, parent, false);
                break;
        }

        _ViewHolder vh = new _ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull _ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                Log.d("VIEW", "geburtstag anzeigen");
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
                break;
            case 1:
                Log.d("VIEW", "keine geburtstag heute anzeigen");
                break;

        }

    }

    @Override
    public int getItemViewType(int position)
    {
        if(dataset.get(position).getBirthdayDate() != null)//position%2==0)       // Even position
            return 0;
        else                   // Odd position
            return 1;
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }




}
