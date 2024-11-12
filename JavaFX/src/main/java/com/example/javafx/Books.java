package com.example.javafx;

public class Books implements OBJECT {
    private String id;
    private String title;
    private String description;
    private String author;
    private String subject;
    private String publisher;
    private String isbn;
    private String language;
    private String year;

    public Books(String id, String title, String description, String author, String subject, String publisher, String isbn, String language, String year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publisher = publisher;
        this.isbn = isbn;
        this.language = language;
        this.year = year;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", subject='" + subject + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn='" + isbn + '\'' +
                ", language='" + language + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
