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

    int luck;
    int job;
    int love;

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
/*
    public String CalculateStarSign(){
        String starSign = "NoStarsignFound";
        Contact actualContact = MainActivity.transmittedContact;
        if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),11,21))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),11, 32))) {
            // Steinbock - capricorn
            starSign = "capricorn";
            return starSign;
        }

        if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),0,0))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),0, 21))) {
            // Steinbock - capricorn
            starSign = "capricorn";
            return starSign;
        }
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),0,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),1, 20))){
            //Wassermann - aquarius
            starSign = "aquarius";
            return starSign;
        }
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),1,19))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),2, 21))){
            //fish
            starSign = "pisces";
            return starSign;
        }
        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),2,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),3, 21))){
            //widder - aries
            starSign = "aries";
            return starSign;

        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),3,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),4, 21))){
            //fish
            starSign = "taurus";
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),4,20))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),5, 22))){
            //fish
            starSign = "gemini";
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),5,21))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),6, 23))){
            //fish
            starSign = "cancer";
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),6,22))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),7, 24))){
            //fish
            starSign = "lio";
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),7,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),8, 24))){
            //fish
            starSign = "virgo";
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),8,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),9, 24))){
            //fish
            starSign = "libra";
            return starSign;
        }


        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),9,23))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),10, 23))){
            //fish
            starSign = "scorpio";
            return starSign;
        }

        else if(actualContact.getBirthdate().after(new Date(actualContact.getBirthdate().getYear(),10,22))
                && actualContact.getBirthdate().before(new Date(actualContact.getBirthdate().getYear(),11, 22))){
            //fish
            starSign = "sagittarius";
            return starSign;
        }
        else {
            return starSign;
        }
    }
*/
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

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck)
    {
        this.luck = luck;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

}
