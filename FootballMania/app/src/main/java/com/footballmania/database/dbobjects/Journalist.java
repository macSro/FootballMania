package com.footballmania.database.dbobjects;

public class Journalist {

    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public Journalist(int id, String firstName, String lastName, String email){
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
