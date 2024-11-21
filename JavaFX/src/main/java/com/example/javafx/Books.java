package com.example.javafx;

public class Books implements OBJECT {
    private String id;
    private String title;
    private String description;
    private String author;
    private String genre;
    private String publisher;
    private String isbn;
    private String language;
    private String year;
    private String imageLinks;
    private String rating;
    private boolean fromAPI;

    public Books(String id, String title, String description, String author, String genre, String publisher, String isbn, String language, String year, String imageLinks, String rating, boolean fromAPI) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.language = language;
        this.year = year;
        this.description = description;
        this.imageLinks = imageLinks;
        this.rating = rating;
        this.fromAPI = fromAPI;
    }
    public Books(String id, String title, String description, String author, String genre, String publisher, String isbn, String language, String year, String imageLinks) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.language = language;
        this.year = year;
        this.imageLinks = imageLinks;
        this.description = description;
    }
    public Books(String title, String description, String author, String genre, String publisher, String isbn, String language, String year, String imageLinks) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.language = language;
        this.year = year;
        this.imageLinks = imageLinks;
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isFromAPI() {
        return fromAPI;
    }

    public void setFromAPI(boolean fromAPI) {
        this.fromAPI = fromAPI;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn='" + isbn + '\'' +
                ", language='" + language + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
