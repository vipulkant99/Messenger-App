package com.example.bca_messenger;

public class testModel {
    String username,usermail,message,time,date,about;
    int imageResource;

    public testModel() {
    }

    public testModel(String username, String usermail, String message, String time, String date, int imageResource) {
        this.username = username;
        this.usermail = usermail;
        this.message = message;
        this.time = time;
        this.date = date;
        this.imageResource = imageResource;
    }

    public testModel(String username, String usermail, String message, String time, String date) {
        this.username = username;
        this.usermail = usermail;
        this.message = message;
        this.time = time;
        this.date = date;
    }

    public testModel(String username, String usermail, String about, int imageResource) {
        this.username = username;
        this.usermail = usermail;
        this.about = about;
        this.imageResource = imageResource;
    }

    public testModel(String nm, String mail, String ab, String s) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
