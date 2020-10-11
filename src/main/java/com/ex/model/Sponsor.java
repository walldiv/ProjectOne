package com.ex.model;

/**
 * This class acts as a data entity mainly for Database transactions
 * @param name - the name of the sponsor
 * @param phone - the phone number of the sponsor
 * @param email - email of the sponsor
 */
public class Sponsor {
    private String name;
    private String phone;
    private String email;

    public Sponsor() {
        this.name = "DEFAULT";
        this.phone = "DEFAULT";
        this.email = "DEFAULT";
    }

    public Sponsor(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "'{\"name\":\""+name+"\", \"phone\":\""+phone+"\", \"email\":\""+email+"\"}'";
    }
}
