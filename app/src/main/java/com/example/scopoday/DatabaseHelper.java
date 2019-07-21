package com.example.scopoday;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    //private static final String KEY_BITRTHDAY = "birthday";
    //private static final String KEY_BIRTHMONTH = "birthmonth";
    //private static final String KEY_BIRTHYEAR = "birthyear";
    private static final String KEY_BIRTHDATE = "birthdate";
    private static final String KEY_COLOR = "color";
    private static final String KEY_PLANET = "planet";
    private static final String KEY_STARSIGN = "starsign";

    private static final String TAG = "DatabaseHelper";

    DateFormat theFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    public DatabaseHelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACNTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + "INTEGER PRIMARY KEY," + KEY_NAME + "TEXT"
                + KEY_BIRTHDATE +"TEXT" +KEY_COLOR + "TEXT"
                + KEY_PLANET +"TEXT" + KEY_STARSIGN + "TEXT"
                + ")";
        db.execSQL(CREATE_CONTACNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE student ADD COLUMN student_rollno INTEGER DEFAULT 0");
        }

        onCreate(db);
    }

    boolean addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_BIRTHDATE, contact.getBirthdate().toString());
        long result = db.insert(TABLE_CONTACTS, null, values);
        Log.d(TAG, "appData: Adding " + contact.getName() + " to " + TABLE_CONTACTS);

        db.close();

        if (result == -1){
            Log.d(TAG, "added contact FAILED -1");
            return false;
        }else{
            Log.d(TAG, "added contact SUCCESSFULL 0");
            return true;
        }
    }

    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_NAME, KEY_BIRTHDATE, KEY_STARSIGN, KEY_PLANET, KEY_COLOR}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        Contact contact = new Contact();
        try {
            contact = new Contact(cursor.getString(1), theFormat.parse(cursor.getString(2)) );

        }catch (ParseException e){
            e.printStackTrace();
        }
        return contact;

    }


    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                try{
                    contact.setBirthdate(theFormat.parse(cursor.getString(2)));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return  contactList;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(KEY_NAME, contact.getName());
        value.put(KEY_BIRTHDATE, contact.getBirthdate().toString());

        return db.update(TABLE_CONTACTS, value, KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public int getContactsCount(){
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}

