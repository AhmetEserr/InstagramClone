package com.example.instagramclone.Model;

public class User {
    private String id;
    private String ad;
    private String bio;
    private String resimurl;
    private String username;

    public User() {
    }

    public User(String id, String ad, String bio, String resimurl, String username) {
        this.id = id;
        this.ad = ad;
        this.bio = bio;
        this.resimurl = resimurl;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getResimurl() {
        return resimurl;
    }

    public void setResimurl(String resimurl) {
        this.resimurl = resimurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public byte[] getImageUrl() {
        return null;
    }
}
