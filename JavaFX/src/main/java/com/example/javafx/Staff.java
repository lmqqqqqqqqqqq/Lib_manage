package com.example.javafx;

import java.util.List;

public class Staff extends User {
    public Staff(int id, String firstname, String lastname, String username, String password, int dayOfBirth, int monthOfBirth, int yearOfBirth, String recoveryCode, String avatarLink, String dayIn, Integer isSave, List<Books> newBooks) {
        super(id, firstname, lastname, username, password, dayOfBirth, monthOfBirth, yearOfBirth, recoveryCode, avatarLink, dayIn, isSave, newBooks);
    }
    public int getId() {
        return super.getId();
    }
    public String getFirstname() {
        return super.getFirstname();
    }
    public String getLastname() {
        return super.getLastname();
    }
    public String getUsername() {
        return super.getUsername();
    }
    public String getPassword() {
        return super.getPassword();
    }
    public int getDayOfBirth() {
        return super.getDayOfBirth();
    }
    public int getMonthOfBirth() {
        return super.getMonthOfBirth();
    }
    public int getYearOfBirth() {
        return super.getYearOfBirth();
    }
    public String getRecoveryCode() {
        return super.getRecoveryCode();
    }
    public String getDayIn() {
        return super.getDayIn();
    }
    public List<Books> getNewBooks() {
        return super.getNewBooks();
    }
    public String getAvatarLink() {
        return super.getAvatarLink();
    }
    public void setAvatarLink(String avatarLink) {
        super.setAvatarLink(avatarLink);
    }
    public Integer getIsSave() {
        return super.getIsSave();
    }
    public void setIsSave(Integer isSave) {
        super.setIsSave(isSave);
    }
    @Override
    public String toString() {
        return "Staff[" + super.toString() + "]";
    }
}