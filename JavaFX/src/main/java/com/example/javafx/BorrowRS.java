package com.example.javafx;

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
    private String userName;
    private String status;

    public BorrowRS() {}
    public BorrowRS(int userId, String bookId, LocalDate borrowDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    /**
     * management book with idUser.
     * @param bookId is bookId return.
     * @param name is nameBook.
     * @param author is book's author.
     * @param borrowDate is borrowDate.
     * @param dueDate is dueDate.
     * @param imageLink is book image.
     */
    public BorrowRS(String bookId, String name, String author, LocalDate borrowDate, LocalDate dueDate, String imageLink, String status) {
        this.bookId = bookId;
        this.name = name;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.author = author;
        LoadImage.loadBookImage(imageLink, this.image);
        this.image.setFitWidth(90);
        this.image.setFitHeight(90);
        this.image.setPreserveRatio(true);
        this.status = status;
    }

    /**
     * management user by book
     * @param userId is id.
     * @param userName is name.
     * @param imageLink is avatar.
     */
    public BorrowRS(int userId, String userName, LocalDate borrowDate, LocalDate dueDate, String imageLink, String status) {
        this.userId = userId;
        this.userName = userName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        try {
            LoadImage.loadBUImage(this.image, imageLink);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.image.setFitWidth(90);
        this.image.setFitHeight(90);
        this.image.setPreserveRatio(true);
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
