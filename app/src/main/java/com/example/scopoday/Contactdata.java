package com.example.scopoday;

public class Contactdata {

        private int id;
        protected String name;
        protected String birthday;
        protected String zodiacsign;


        public Contactdata() {

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

    }


