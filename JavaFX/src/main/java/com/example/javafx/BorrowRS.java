package com.example.javafx;

import java.time.LocalDate;

public class BorrowRS implements OBJECT {
    private int userId;
    private String userName;
    private int bookId;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public BorrowRS() {}
    public BorrowRS(int userId, int bookId, String bookTitle, LocalDate borrowDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public long getRemainingDays() {
        return LocalDate.now().until(dueDate).getDays();
    }

    public boolean isOverDue() {
        return LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return "BorrowRS[" +
                "userId=" + userId +
                ", userName=" + userName +
                ", bookId=" + bookId +
                ", bookTitle=" + bookTitle +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", remainingDays=" + getRemainingDays() +
                ", overdue=" + isOverDue() +
                ']';
    }

}
