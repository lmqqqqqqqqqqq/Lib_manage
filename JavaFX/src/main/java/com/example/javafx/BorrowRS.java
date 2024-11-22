package com.example.javafx;

import java.time.LocalDate;

public class BorrowRS implements OBJECT {
    private int userId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public BorrowRS() {}
    public BorrowRS(int userId, String bookId, LocalDate borrowDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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
                ", bookId=" + bookId +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", remainingDays=" + getRemainingDays() +
                ", overdue=" + isOverDue() +
                ']';
    }

}
