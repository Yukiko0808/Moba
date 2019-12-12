package com.example.scopoday;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AddContactDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //View mView = getLayoutInflater().inflate(R.layout.add_contact_popup_window, null);
        //builder.setView(R.layout.add_contact_popup_window);
        //View mView = new View(builder.getContext());

        View mView = getLayoutInflater().inflate(R.layout.add_contact_popup_window, null);

        final EditText mContactName = (EditText)  mView.findViewById(R.id.insertName_ET_ID);
        final EditText mBirthday  = (EditText) mView.findViewById(R.id.selectBirthday_ET_ID);

        builder.setView(mView);

        builder.setTitle("Add a contact")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("Name", mContactName.getText().toString());
                    }
                });

        return null;
        */

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
                dialog.dismiss();

                // Überprüfung ob Einfabe richtiges Format hat
                if(mBirthday.getText().toString().matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")){

                    // Neuer Kontakt erstellen und zur Datenbank hinzufügen
                    MySQLHelper database = new MySQLHelper(getContext());
                    Contactdata newContact = new Contactdata( mContactName.getText().toString(), mBirthday.getText().toString());
                    database.addContact(newContact);

                    ContactListActivity.contactAdapter.add(newContact);

                    Log.i("Name", mContactName.getText().toString());
                }
                else{
                    Toast.makeText(ContactListActivity.contactAdapter.getContext(), "Wrong date format, try: DD.MM.YYYY", Toast.LENGTH_LONG);
                    Log.i("Hinzufügen fehlgeschlagen, falsches Format", mContactName.getText().toString());
                }

            }
        });

        return dialog;
    }
}
