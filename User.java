package com.example.zukhruf;

public class User {

    public String name, email, password, date, gender;
    public int bed, black_chair, hanger, nightstand, office_table, tables;

    public User () {
    }

    public User (String name, String email, String password, String date, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.date = date;
        this.gender = gender;
        bed = 0;
        black_chair = 0;
        hanger = 0;
        nightstand = 0;
        office_table = 0;
        tables = 0;
    }
}
