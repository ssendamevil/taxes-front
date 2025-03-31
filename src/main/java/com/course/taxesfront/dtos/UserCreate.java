package com.course.taxesfront.dtos;

import java.time.LocalDateTime;

public class UserCreate {
    private String username;
    private String firstname;
    private String lastname;
    private String middlename;
    private String email;
    private String password;

    public UserCreate(String username, String firstname, String lastname, String middlename, String email, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
