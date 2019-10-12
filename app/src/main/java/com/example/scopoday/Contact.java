package com.example.scopoday;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;



public class Contact {

    int id;
    String name;
    DateFormat theFormat = new SimpleDateFormat("yyyy-mm-dd");
    Date birthdate;

    String color;
    String planet;
    String starSign;


/*
    public  Contact(String contactName, String bd){
        this.name = contactName;
        DateFormat theFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            this.birthdate = theFormat.parse(bd);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }*/

    public Contact(int id, String contactName, Date bd){
        this.id = id;
        this.name = contactName;
        this.birthdate = bd;
    }

    public Contact(String contactName, Date bd){
        this.name = contactName;
        this.birthdate = bd;
    }

    public Contact(){
        //Error Meldung Bitte Name und Geburtsdatum eintragen
    }

    //Getter und Setter Methoden

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getStarSign() {
        return starSign;
    }

    public void setStarSign(String starSign) {
        this.starSign = starSign;
    }
}
