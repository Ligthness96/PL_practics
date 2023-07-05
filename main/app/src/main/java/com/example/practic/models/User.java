package com.example.practic.models;

import java.math.BigInteger;

public class User {
    private static long users_count = 0;
//    private long id;
    private String name, surname, patronymic, post, division, phone,
    mailAndLogin, password;
    public int points;

    public User () {}

    public User(String name, String surname, String patronymic, String post, String division, String phone, String mailAndLogin, String password) {
        User.users_count+=1;
//        this.id = users_count;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.post = post;
        this.division = division;
        this.phone = phone;
        this.mailAndLogin = mailAndLogin;
        this.password = password;
        this.points = 0;
    }

    public static long getUsers_count() {
        return users_count;
    }

    public static void setUsers_count(long users_count) {
        User.users_count = users_count;
    }

//    public long getId() {
//        return id;
//    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMailAndLogin() {
        return mailAndLogin;
    }

    public void setMailAndLogin(String mailAndLogin) {
        this.mailAndLogin = mailAndLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {return points;}

    public void setPoints(int points) {this.points = points;}
}
