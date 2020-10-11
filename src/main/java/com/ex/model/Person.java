package com.ex.model;

/**
 * This class is for the abstraction of a Person entity - to be extended to
 * Player for a more defined set of data.  Coaches will be a Person entity, where
 * players will be of type Player.
 *
 * @param name - the name of the person
 * @param phone - the phone number of the person
 * @param emergencyphone - the emergency contact number for person
 * @param team - the team this person belongs to
 * @param phonecarrier - the phone service for this person - allows for SMS messaging
 * @param allowTxtMsg - to see if this person wants to be allowed to receive Text messages via server system
 * @param team - the team this person belongs to
 */
public class Person {
    private int id;
    private String name;
    private String phone;
    private String emergencyphone;
    private PhoneCarrier phonecarrier;
    private boolean allowTxtMsg;
    private Team team;
    private int userId;

    public Person() {
        this.id = -1;
        this.name = "";
        this.phone = "";
        this.emergencyphone = "";
        this.phonecarrier = PhoneCarrier.TMobile;
        this.allowTxtMsg = false;
        this.team = null;
        this.userId = -1;
    }


    public Person(int id, String name, String phone, String emergencyphone, PhoneCarrier phonecarrier
            , boolean allowTxtMsg, Team team, int userId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.emergencyphone = emergencyphone;
        this.phonecarrier = phonecarrier;
        this.allowTxtMsg = allowTxtMsg;
        this.team = team;
        this.userId = userId;
    }

    /* =================    GET & SET   ======================= */

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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmergencyPhone() {
        return emergencyphone;
    }
    public void setEmergencyPhone(String emergencyphone) {
        this.emergencyphone = emergencyphone;
    }
    public PhoneCarrier getPhonecarrier() {
        return phonecarrier;
    }
    public void setPhonecarrier(PhoneCarrier phonecarrier) {
        this.phonecarrier = phonecarrier;
    }
    public boolean isAllowTxtMsg() {
        return allowTxtMsg;
    }
    public void setAllowTxtMsg(boolean allowTxtMsg) {
        this.allowTxtMsg = allowTxtMsg;
    }
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "'{\"id\":\""+getId()+"\", \"name\":\""+getName()+"\", \"phone\":\""+getPhone()+"\", \"emergencyphone\":\""+
                getEmergencyPhone()+"\", \"phonecarrier\":\""+getPhonecarrier()+"\", \"allowtextmsg\":\""+
                isAllowTxtMsg()+"\", \"team\":\""+getTeam().getName()+"\", \"userid\":\""+getUserId()+"}'";
    }
}
