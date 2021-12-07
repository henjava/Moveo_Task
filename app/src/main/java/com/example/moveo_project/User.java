package com.example.moveo_project;

import androidx.room.Entity;


public class User {

    private String email;
    private String firstName;
    private String lastName;
    private String pin;

    public User() {
    }

    public User(String email, String firstName, String lastName, String pin) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
