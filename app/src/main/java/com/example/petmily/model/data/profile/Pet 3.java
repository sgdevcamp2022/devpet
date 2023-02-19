package com.example.petmily.model.data.profile;

public class Pet {
    String name;
    String division;
    String birth;
    String about;
    String imageUrl;
    String meetDate;

    public Pet(String name, String division, String birth, String about, String imageUrl, String meetDate) {
        this.name = name;
        this.division = division;
        this.birth = birth;
        this.about = about;
        this.imageUrl = imageUrl;
        this.meetDate = meetDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMeetDate() {
        return meetDate;
    }

    public void setMeetDate(String meetDate) {
        this.meetDate = meetDate;
    }
}
