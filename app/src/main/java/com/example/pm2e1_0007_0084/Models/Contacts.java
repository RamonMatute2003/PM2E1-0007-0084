package com.example.pm2e1_0007_0084.Models;

public class Contacts {
    private Integer id_country;
    private Integer id_contact;
    private String name;
    private Integer phone;
    private String note;
    private String photo;

    public Integer getId_country() {
        return id_country;
    }

    public void setId_country(Integer id_country) {
        this.id_country = id_country;
    }

    public Integer getId() {
        return id_contact;
    }

    public void setId(Integer id) {
        this.id_contact = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

