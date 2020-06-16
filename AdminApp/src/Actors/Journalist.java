package Actors;

public class Journalist {

    private int journalistID;
    private String firstName;
    private String lastName;
    private String email;

    public Journalist( String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Journalist(){}

    public int getJournalistID() {
        return journalistID;
    }

    public void setJournalistID(int journalistID) {
        this.journalistID = journalistID;
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
