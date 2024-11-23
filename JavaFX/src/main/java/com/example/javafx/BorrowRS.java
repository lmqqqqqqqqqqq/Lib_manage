package com.example.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

public class BorrowRS implements OBJECT {
    private int userId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private String name;
    private ImageView image = new ImageView();
    private String author;

    public BorrowRS() {}
    public BorrowRS(int userId, String bookId, LocalDate borrowDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public BorrowRS(String bookId, String name, String author, LocalDate borrowDate, LocalDate dueDate, String imageLink) {
        this.bookId = bookId;
        this.name = name;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.author = author;
        LoadImage.loadBookImage(imageLink, this.image);
        this.image.setFitWidth(90); // Chiều rộng mong muốn
        this.image.setFitHeight(90); // Chiều cao mong muốn
        this.image.setPreserveRatio(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
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
