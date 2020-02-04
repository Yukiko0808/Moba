package com.example.scopoday;

import java.io.Serializable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Contactdata implements Serializable {

        private int id;
        protected String name;
        protected String birthday;
        protected Date birthdayDate;
        protected String zodiacsign;
       protected boolean isYou = false;


        public Contactdata() {

        }

        public void setIsYou(int you){
            if(you == 0){
                isYou = false;
            }
            else{
                isYou = true;
            }
            //isYou = you;
        }

        public boolean getIsYou(){
            return isYou;
        }


    public Contactdata(String _name){
        name = _name;
    }

        public Contactdata(String _name, Date _birthdayDate){
            name = _name;
            birthdayDate = _birthdayDate;
        }

        public Contactdata(String _name, String _birthday) {
            name = _name;
            birthday = _birthday;
            //zodiacsign = calculateZodiacsign();
        }

        public Contactdata(String _name, String _birthday, String _zodiacsign) {
            name = _name;
            birthday = _birthday;
            zodiacsign = _zodiacsign;
        }

        public int getId() {return id;}
        public void setId(int id) {this.id = id;}

        public String getName() {return name;}
        public void setName(String name) {this.name = name;}

        public String getBirthday() {return birthday;}
        public void setBirthday(String birthday) {this.birthday = birthday;}

        public String getZodiacsign() {return zodiacsign;}
        public void setZodiacsign(String zodiacsign) {this.zodiacsign = zodiacsign;}

        public Date getBirthdayDate(){
         Date date = new Date();
            try{
            SimpleDateFormat birthdaytodate = new SimpleDateFormat("dd.MM.yyyy");
            Date date1 = birthdaytodate.parse(birthday);
            date = date1;
            }
            catch (Exception e){
                return null;
            }
            birthdayDate = date;
            return date;
        }



        public void setBirthdayDateToString(Date newBirthday){
            this.birthday = this.birthdayDate.toString();
            Log.d("Datum zu string:" , birthday );
        }

        public String calculateZodiacsign(){
            String zodiac = "";
            //alles aus Kontakt seite für bestimmung des Sternzeichens

            return zodiac;
        }

    public long CalculateDaysTillBD (Date bd){

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        Date birthDate = new Date(0000, bd.getMonth(), bd.getDate());
        Date todayDate = new Date(0000, today.getMonth(), today.getDate());

        if(birthDate.getTime()<todayDate.getTime()){
            birthDate = new Date(1, birthDate.getMonth(), birthDate.getDate());
        }

        long days = birthDate.getTime() - todayDate.getTime();
        days = days / (24 * 60 * 60 * 1000);
        /*
        Log.d("First date", birthDate.toString());
        Log.d("Second Date", todayDate.toString());
        Log.d("Days?" , Long.toString(days));*/

        return  days;
    }

    }


