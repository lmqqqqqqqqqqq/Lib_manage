package com.example.javafx;

public class User implements OBJECT {
    private int id;
    private String firstname;
    private String lastname;
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;
    private String username;
    private String password;
    private String recoveryCode;
    private String avatarLink;
    private String dayIn;
    private Integer isSave;

    public User(int id, String firstname, String lastname, String username, String password,
                int dayOfBirth, int monthOfBirth, int yearOfBirth, String recoveryCode, String avatarLink, String dayIn, Integer isSave) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
        this.recoveryCode = recoveryCode;
        this.avatarLink = avatarLink;
        this.dayIn = dayIn;
        this.isSave = isSave;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getDayIn() {
        return dayIn;
    }

    public Integer getIsSave() {
        return isSave;
    }

    public void setIsSave(Integer isSave) {
        this.isSave = isSave;
    }

    @Override
    public String toString() {
        return "User[ id = " + id + ", firstname = " + firstname + ", lastname = " + lastname
                + ", dayOfBirth = " + dayOfBirth + ", monthOfBirth = " + monthOfBirth
                + ", yearOfBirth = " + yearOfBirth + ", recoveryCode = " + recoveryCode
                + ", avatarLink = " + avatarLink + ", dayIn = " + dayIn + "]";
    }
}