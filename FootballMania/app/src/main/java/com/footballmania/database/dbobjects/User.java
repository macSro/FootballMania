package com.footballmania.database.dbobjects;

public class User {

    private int id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private long lastUpdateDate;
    private long lastArticleUpdateDate;


    public User(int id, String login, String password, String email, String firstName, long lastUpdateDate, long lastArticleUpdateDate){
        setId(id);
        setLogin(login);
        setPassword(password);
        setEmail(email);
        setFirstName(firstName);
        setLastUpdateDate(lastUpdateDate);
        setLastArticleUpdateDate(lastArticleUpdateDate);
    }

    public long getLastArticleUpdateDate() {
        return lastArticleUpdateDate;
    }

    public void setLastArticleUpdateDate(long lastArticleUpdateDate) {
        this.lastArticleUpdateDate = lastArticleUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
