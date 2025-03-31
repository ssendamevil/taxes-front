package com.course.taxesfront.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.json.JSONObject;

public class UserDto {
    private String username;
    private String firstname;
    private String lastname;
    private String middlename;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDto() {
    }

    public UserDto(String jsonRes) {
        JSONObject jsonObject = new JSONObject(jsonRes);

        this.username = jsonObject.getString("username");
        this.firstname = jsonObject.getString("firstname");
        this.lastname = jsonObject.getString("lastname");
        this.middlename = jsonObject.getString("middlename");
        this.email = jsonObject.getString("email");

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.createdAt = LocalDateTime.parse(jsonObject.getString("createdAt"), formatter);
        this.updatedAt = LocalDateTime.parse(jsonObject.getString("updatedAt"), formatter);
    }
    public UserDto(String username, String firstname, String lastname, String middlename, String email, LocalDateTime createdAt, LocalDateTime updatedAt, List<Object> incomes, List<Object> taxes) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
