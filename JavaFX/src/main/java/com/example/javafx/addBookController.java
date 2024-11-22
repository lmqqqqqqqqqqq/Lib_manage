package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class addBookController {

    DatabaseConnect Connect = new DatabaseConnect();
    @FXML
    private Pane decor;

    @FXML
    private TableView<Books> tableView;
    ObservableList<Books> book = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Books, String> authorColumn;
    @FXML
    private TableColumn<Books, String> idColumn;
    @FXML
    private TableColumn<Books, String> titleColumn;
    @FXML
    private TableColumn<Books, String> descriptionColumn;
    @FXML
    private TableColumn<Books, String> genreColumn;
    @FXML
    private TableColumn<Books, String> publisherColumn;
    @FXML
    private TableColumn<Books, String> publishedDateColumn;
    @FXML
    private TableColumn<Books, String> languageColumn;
    @FXML
    private TableColumn<Books, String> isbnColumn;
    @FXML
    private TableColumn<Books, String> imageLinksColumn;
    @FXML
    public void initialize() throws Exception {
        idColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("id"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("description"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("genre"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("publisher"));
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("year"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("language"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("isbn"));
        imageLinksColumn.setCellValueFactory(new PropertyValueFactory<Books,String>("imageLinks"));

        errormess.setVisible(false);
        tableView.setItems(book);
        loadbooks();

        decor.setStyle("-fx-background-color: black");
    }
    public void loadbooks() {
        book.clear();
        tableView.getItems().clear();
        try {
            List<Books> bookList = AdvancedSearch.getAllBooks(Connect.connect());
            book.addAll(bookList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private TextField id;
    @FXML
    private TextField author;
    @FXML
    private TextField title;
    @FXML
    private TextField description;
    @FXML
    private TextField genre;
    @FXML
    private TextField publisher;
    @FXML
    private TextField publisherDate;
    @FXML
    private TextField language;
    @FXML
    private TextField isbn;
    @FXML
    private ImageView image;
    @FXML
    private TextField imlink;
    @FXML
    private Label errormess;

    /**
     * only add books information not id.
     * co check trung` isbn
     * @throws Exception
     */
    @FXML
    private void add() throws Exception {
        if(!checkEMPTY()) {
            errormess.setVisible(true);
            errormess.setText("Something missing please check and then add again");
            return;
        }
        String Author = author.getText();
        String Title = title.getText();
        String Description = description.getText();
        String Genre = genre.getText();
        String Publisher = publisher.getText();
        String ISBN = isbn.getText();
        String Language = language.getText();
        String Year = publisherDate.getText();

        String ImageLinks = imlink.getText();

        StringBuilder error = new StringBuilder();
        for (Books bok : book) {
            if (ISBN.equals(bok.getIsbn())) {
                error.append(" ISBN " + bok.getIsbn() + " already exists ");
            }
        }
        if (!error.isEmpty()) {
            errormess.setText(error.toString());
            errormess.setVisible(true);
        } else {
            Books b = new Books(Title, Description, Author, Genre, Publisher, ISBN, Language, Year, ImageLinks);
            book.add(b);
            addDB(Connect.connect());
            reset();
        }
    }
    private void addDB(Connection conn) {
        StringBuilder Q = new StringBuilder("Insert into books(title,author,created_date,description,genre,publisher,ISBN,language,image)" +
                "values ( ? , ? , ? , ? , ? , ? , ? , ?, ?)");
        String Author = author.getText();
        String Title = title.getText();
        String Description = description.getText();
        String Genre = genre.getText();
        String Publisher = publisher.getText();
        String ISBN = isbn.getText();
        String Language = language.getText();
        String pubDate = publisherDate.getText();

        String imageLinks = imlink.getText();

        try (PreparedStatement stm = conn.prepareStatement(Q.toString())) {
            stm.setString(1, Title);
            stm.setString(2, Author);
            stm.setString(3, pubDate);
            stm.setString(4, Description);
            stm.setString(5, Genre);
            stm.setString(6, Publisher);
            stm.setString(7, ISBN);
            stm.setString(8, Language);
            stm.setString(9, imageLinks);
            stm.executeUpdate();
            System.out.println("Book added successfully database!");
        } catch (SQLException e) {
            System.out.println("add failed database");
            e.printStackTrace();
        }
    }

    /**
     * delete with id.
     * @throws Exception
     */
    @FXML
    private void delete() throws Exception {
        if(!checkEMPTY()) {
            errormess.setVisible(true);
            errormess.setText("Something missing please check and then delete again");
            return;
        }
        StringBuilder error = new StringBuilder();
        if (id.getText().isEmpty()) {
            System.out.println("ID is empty!");
            error.append("ID is empty!");
            errormess.setText(error.toString());
            errormess.setVisible(true);
        } else {
            String targetid="";
            Books c = tableView.getSelectionModel().getSelectedItem();
            if (c == null) {
                targetid = id.getText();
            } else {
                targetid = c.getId();
            }
            Iterator<Books> it = book.iterator();
            while (it.hasNext()) {
                Books book = it.next();
                if (book.getId().equals(targetid)) {
                    it.remove();
                    delDB(Connect.connect());
                    reset();
                }
            }
        }
    }
    private void delDB(Connection conn) {
        StringBuilder Q = new StringBuilder("Delete from books where idbooks = ?");

        try (PreparedStatement stm = conn.prepareStatement(Q.toString())) {
            stm.setString(1, id.getText());
            stm.executeUpdate();
            System.out.println("delete sucessfully database");
        } catch (SQLException e) {
            System.out.println("delete failed database");
            e.printStackTrace();
        }
    }

    /**
     * update everything except id book.
     * @throws Exception
     */
    @FXML
    private void update() throws Exception {
        if(!checkEMPTY()) {
            errormess.setVisible(true);
            errormess.setText("Something missing please check and then update again");
            return;
        }
        Books c = tableView.getSelectionModel().getSelectedItem();
        if (c == null) {
            System.out.println("unselected");
            errormess.setText("there are nothing to update");
            errormess.setVisible(true);
            return;
        }
        String Author = author.getText();
        String Title = title.getText();
        String Description = description.getText();
        String Genre = genre.getText();
        String Publisher = publisher.getText();
        String PublishedDate = publisherDate.getText();
        String Language = language.getText();
        String Isbn = isbn.getText();
        String ImageLink = image.getImage().getUrl();
        c.setAuthor(Author);
        c.setTitle(Title);
        c.setDescription(Description);
        c.setGenre(Genre);
        c.setPublisher(Publisher);
        c.setIsbn(Isbn);
        c.setLanguage(Language);
        c.setYear(PublishedDate);
        c.setImageLinks(ImageLink);
        tableView.refresh();
        upDB(Connect.connect());
    }
    private void upDB(Connection connect) {
        StringBuilder Q = new StringBuilder("Update books set title = ?, author = ?, description = ?" +
                ", genre = ?, publisher = ?, created_date = ?, language = ?, ISBN = ? WHERE idbooks = ?");
        try (PreparedStatement stm = connect.prepareStatement(Q.toString())) {
            stm.setString(1,title.getText());
            stm.setString(2,author.getText());
            stm.setString(3,description.getText());
            stm.setString(4,genre.getText());
            stm.setString(5,publisher.getText());
            stm.setString(6,publisherDate.getText());
            stm.setString(7,language.getText());
            stm.setString(8,isbn.getText());
            stm.setString(9,id.getText());
            stm.executeUpdate();
            System.out.println("update sucessfully on database");
        } catch (SQLException e) {
            System.out.println("update failed on database");
            e.printStackTrace();
        }
    }

    private boolean checkEMPTY() {
        boolean check = true;
        if (title.getText().isEmpty() || author.getText().isEmpty() || description.getText().isEmpty()
                || genre.getText().isEmpty() || genre.getText().isEmpty() || publisher.getText().isEmpty()
                || isbn.getText().isEmpty() || language.getText().isEmpty() || publisherDate.getText().isEmpty()) {
            check = false;
            return check;
        }
        return check;
    }
    @FXML
    public void reset() {
        id.setText("");
        title.setText("");
        description.setText("");
        genre.setText("");
        publisher.setText("");
        isbn.setText("");
        language.setText("");
        author.setText("");
        publisherDate.setText("");
        image.setImage(null);
        imlink.setText("");
        errormess.setVisible(false);
        tableView.getSelectionModel().clearSelection();
        loadbooks();
    }
    @FXML
    public void handleRowClick() {
        Books selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            id.setText(selectedBook.getId());
            title.setText(selectedBook.getTitle());
            description.setText(selectedBook.getDescription());
            genre.setText(selectedBook.getGenre());
            publisher.setText(selectedBook.getPublisher());
            isbn.setText(selectedBook.getIsbn());
            language.setText(selectedBook.getLanguage());
            author.setText(selectedBook.getAuthor());
            publisherDate.setText(selectedBook.getYear());

            LoadBookImage.loadBookImage(selectedBook.getImageLinks(), image);
            imlink.setText(selectedBook.getImageLinks());
        }
    }

    @FXML
    private Button test;
    @FXML
    public void addMode() {
        test.setText("Add Book");
        test.setOnAction(event -> {
            try {
                add();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    public void deleteMode() {
        test.setText("Delete Book");
        test.setOnAction(event -> {
            try {
                delete();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    @FXML
    public void updateMode() {
        test.setText("Update Book");
        test.setOnAction(event -> {
            try {
                update();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
