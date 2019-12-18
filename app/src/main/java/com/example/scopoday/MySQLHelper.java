package com.example.scopoday;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MySQLHelper extends SQLiteOpenHelper {


    private static final String TABLE_CONTACTDATA = "contactdata";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_ZODIACSIGN = "zodiacsign";



    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_BIRTHDAY, KEY_ZODIACSIGN};

    private static final int DATABSE_VERSION = 4;
    private static final String DATABSE_NAME = "ContactDB";


    public MySQLHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABSE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARM_TABLE = "CREATE TABLE " + TABLE_CONTACTDATA +
                " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_BIRTHDAY + " TEXT, " +
                KEY_ZODIACSIGN + " TEXT " +
                " )";
        db.execSQL(CREATE_ALARM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropIfEx = "DROP TABLE IF EXISTS " + TABLE_CONTACTDATA;
        db.execSQL((dropIfEx));

        this.onCreate((db));
    }

    public void addContact(Contactdata contactdata) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contactdata.getName());
        values.put(KEY_BIRTHDAY, contactdata.getBirthday());
        values.put(KEY_ZODIACSIGN, contactdata.getZodiacsign());



        db.insert(TABLE_CONTACTDATA,
                null,
                values);
        db.close();
    }

    public List<Contactdata> getAllContacts() {
        List<Contactdata> contacts = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_CONTACTDATA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Contactdata allContacts;

        if (cursor.moveToFirst()) {
            do {
                allContacts = new Contactdata();
                allContacts.setId(Integer.parseInt(cursor.getString(0)));
                allContacts.setName(cursor.getString(1));
                allContacts.setBirthday(cursor.getString(2));
                allContacts.setZodiacsign(cursor.getString(3));


                contacts.add(allContacts);
            } while (cursor.moveToNext());

        }
        return contacts;
    }

// NAME
    // GEBURTSDATUM
    // STERNZEICHEN

    public void deleteContacts(Contactdata contactdata) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTDATA,
                KEY_ID + " = ?",
                new String[]{String.valueOf(contactdata.getId())});
        db.close();
    }

    public Contactdata getOneContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_CONTACTDATA,
                        COLUMNS,
                        "id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null
                );
        Contactdata contactdata = new Contactdata();

        if (cursor != null) {
            cursor.moveToFirst();
            contactdata.setId(Integer.parseInt(cursor.getString(0)));
            contactdata.setName(cursor.getString(1));
            contactdata.setBirthday(cursor.getString(2));
            contactdata.setZodiacsign(cursor.getString(3));

        }

        return contactdata;
    }
}