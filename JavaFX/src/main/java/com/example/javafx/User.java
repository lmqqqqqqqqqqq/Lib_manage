package com.example.javafx;

import java.time.LocalDate;
import java.util.List;

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
    private List<Books> newBooks;
    private LocalDate isBan;
    private String banReason;
    private int coin;

    public User(int id, String firstname, String lastname, String username, String password,
                int dayOfBirth, int monthOfBirth, int yearOfBirth, String recoveryCode, String avatarLink, String dayIn, Integer isSave, List<Books> newBooks, LocalDate isBan, String reason, int coin) {
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
        this.newBooks = newBooks;
        this.isBan = isBan;
        this.banReason = reason;
        this.coin = coin;
    }


    public User(int id, String firstname, String lastname, String username, String password,
                int dayOfBirth, int monthOfBirth, int yearOfBirth, String recoveryCode, String avatarLink, String dayIn, Integer isSave, LocalDate isBan, String reason, Integer coin) {
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
        this.isBan = isBan;
        this.banReason = reason;
        this.coin = coin;
    }

    public User(String firstname, String lastname, String username, String password,
                int dayOfBirth, int monthOfBirth, int yearOfBirth, String recoveryCode, String avatarLink, String dayIn, Integer isSave) {
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

    public User(int id, String username, String avatar) {
        this.id = id;
        this.username = username;
        this.avatarLink = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
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

    public void setDayIn(String dayIn) {
        this.dayIn = dayIn;
    }

    public Integer getIsSave() {
        return isSave;
    }

    public void setIsSave(Integer isSave) {
        this.isSave = isSave;
    }

    public List<Books> getNewBooks() {
        return newBooks;
    }

    public LocalDate getIsBan() {
        return isBan;
    }

    public void setIsBan(LocalDate isBan) {
        this.isBan = isBan;
    }

    public String getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        if(coin < 0) {
            coin = 0;
        }
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "User[ id = " + id + ", firstname = " + firstname + ", lastname = " + lastname
                + ", dayOfBirth = " + dayOfBirth + ", monthOfBirth = " + monthOfBirth
                + ", yearOfBirth = " + yearOfBirth + ", recoveryCode = " + recoveryCode
                + ", avatarLink = " + avatarLink + ", dayIn = " + dayIn + "]";
    }
}