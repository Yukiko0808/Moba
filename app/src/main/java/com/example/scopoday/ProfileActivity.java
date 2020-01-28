package com.example.scopoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem tempItem = menu.getItem(0) ;

        SpannableString spanString = new SpannableString(tempItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0,     spanString.length(), 0); //fix the color to white

        tempItem.setTitle(spanString);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.profil_settings){

        }

        if(id == R.id.contact_settings){
            Toast.makeText(this, "contacts", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactListActivity.class);
            startActivity(intent);

        }

        if(id == R.id.zodiacsign_settings){
            Toast.makeText(this, "zodiacsigns", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ZodiacsignsActivity.class);
            startActivity(intent);
        }


        if(id == R.id.calendar_settings){
            Toast.makeText(this, "calendar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}
