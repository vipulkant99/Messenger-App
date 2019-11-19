package com.example.bca_messenger;

public class account_details {
    String username,usermail,about,imageResource;


    public account_details() {
    }

    public account_details(String username, String usermail, String about, String imageResource) {
        this.username = username;
        this.usermail = usermail;
        this.about = about;
        this.imageResource = imageResource;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }
}
