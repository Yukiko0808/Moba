package com.example.scopoday;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddContactDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Dialog Fenster erstellen
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_contact_popup_window);

        //Input Felder finden
        final EditText mContactName = (EditText)  dialog.findViewById(R.id.insertName_ET_ID);
        final EditText mBirthday  = (EditText) dialog.findViewById(R.id.selectBirthday_ET_ID);

        // Button
        Button okButton = dialog.findViewById(R.id.button_dialog_ok);
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                if(mBirthday.getText().toString().matches("") || mContactName.getText().toString().matches("")){

                }
                else
                {
                    int day = Integer.parseInt(mBirthday.getText().toString().split("[.]")[0]);
                    int month =  Integer.parseInt(mBirthday.getText().toString().split("[.]")[1]);

                    if(day > 31 || month > 12){
                        Toast.makeText(getContext(), "Date is not possible", Toast.LENGTH_LONG).show();
                    }


                    else if(mBirthday.getText().toString().matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")){

                        Log.i("Name", mContactName.getText().toString());
                        if(mContactName.getText().toString() == "Me"){
                            Toast.makeText(getContext(), "It's not possible to take 'Me' as Name", Toast.LENGTH_LONG).show();
                        }
                        else{

                            // Neuer Kontakt erstellen und zur Datenbank hinzufügen
                            MySQLHelper database = new MySQLHelper(getContext());
                            Contactdata newContact = new Contactdata( mContactName.getText().toString(), mBirthday.getText().toString());
                            database.addContact(newContact);

                            ContactListActivity.contactAdapter.add(newContact);

                            //Dialog schließen
                            dialog.dismiss();
                        }

                    }
                    else{
                        Toast.makeText(getContext(), "Wrong date format try: DD.MM.YYYY", Toast.LENGTH_LONG).show();
                        Log.i("Hinzufügen fehlgeschlagen, falsches Format", mContactName.getText().toString());
                    }
                }


            }
        });

        return dialog;
    }
}
