package com.example.lucas.lucasvanberkel_pset6.classes;

public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String address;

    public User(String username, String firstname, String lastname, String address) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }
}
