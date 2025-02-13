package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.javafx.LoginController.user;

public class addBookController {

    DatabaseConnect Connect = new DatabaseConnect();


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
        idColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("id"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("description"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("genre"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("publisher"));
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("year"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("language"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("isbn"));
        imageLinksColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("imageLinks"));

        errormess.setVisible(false);
        tableView.setItems(book);
        loadbooks();
        changeImage.setDisable(false);
        changeImage.setVisible(true);

        addMode();
    }

    public void loadbooks() {
        book.clear();
        tableView.getItems().clear();
        try {
            List<Books> bookList = AdvancedSearch.getAllBooks(Connect.connect());
            book.addAll(bookList);
        } catch (Exception e) {
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
     *
     * @throws Exception is exception.
     */
    @FXML
    private void add() throws Exception {
        if (!checkEMPTY()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System notifications");
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
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
                error.append(" ISBN already exists ");
                return;
            }
        }
        if(!isValidDate(Year)) {
            error.append("Please enter a valid date");
        }
        if (!error.isEmpty()) {
            errormess.setText(error.toString());
            errormess.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System notifications");
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        } else {
            Books b = new Books(Title, Description, Author, Genre, Publisher, ISBN, Language, Year, ImageLinks);
            book.add(b);
            addDB(Connect.connect());
            reset();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System notifications");
            alert.setHeaderText(null);
            alert.setContentText("Successful");
            alert.showAndWait();
        }
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Không cho phép ngày không hợp lệ, như 30/02
        try {
            dateFormat.parse(date); // Thử phân tích chuỗi
            return true; // Chuỗi hợp lệ
        } catch (ParseException e) {
            return false; // Chuỗi không hợp lệ
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
            errormess.setVisible(false);
        } catch (SQLException e) {
            System.out.println("add failed database");
            e.printStackTrace();
        }
    }

    /**
     * delete with id.
     *
     * @throws Exception is exception.
     */
    @FXML
    private void delete() throws Exception {
//        if (!checkEMPTY()) {
//            errormess.setVisible(true);
//            errormess.setText("Something missing please check and then delete again");
//            return;
//        }
        StringBuilder error = new StringBuilder();
        if (id.getText().isEmpty()) {
            System.out.println("ID is empty!");
            error.append("ID is empty!");
            errormess.setText(error.toString());
            errormess.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System notifications");
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        } else {
            String targetid = "";
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
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("System notifications");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful");
                    alert.showAndWait();
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
     *
     * @throws Exception
     */
    @FXML
    private void update() throws Exception {
        if (!checkEMPTY()) {
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
        if(!isValidDate(publisherDate.getText())) {
            errormess.setText("Please enter a valid date");
            errormess.setVisible(true);
            return;
        }
        for (Books bok : book) {
            if (isbn.getText().equals(bok.getIsbn())) {
                errormess.setText(" ISBN already exists ");
                errormess.setVisible(true);
                return;
            }
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System notifications");
        alert.setHeaderText(null);
        alert.setContentText("Successful");
        alert.showAndWait();

    }

    private void upDB(Connection connect) {
        StringBuilder Q = new StringBuilder("Update books set title = ?, author = ?, description = ?" +
                ", genre = ?, publisher = ?, created_date = ?, language = ?, ISBN = ? WHERE idbooks = ?");
        try (PreparedStatement stm = connect.prepareStatement(Q.toString())) {
            stm.setString(1, title.getText());
            stm.setString(2, author.getText());
            stm.setString(3, description.getText());
            stm.setString(4, genre.getText());
            stm.setString(5, publisher.getText());
            stm.setString(6, publisherDate.getText());
            stm.setString(7, language.getText());
            stm.setString(8, isbn.getText());
            stm.setString(9, id.getText());
            stm.executeUpdate();
            System.out.println("update sucessfully on database");
            System.out.println("update sucessfully on database");
            errormess.setVisible(false);
        } catch (SQLException e) {
            System.out.println("update failed on database");
            e.printStackTrace();
        }
    }

    private boolean checkEMPTY() {
        boolean check = true;
        if (title.getText().isEmpty() || author.getText().isEmpty() || isbn.getText().isEmpty()
                || language.getText().isEmpty() || publisherDate.getText().isEmpty()) {
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

            LoadImage.loadBookImage(selectedBook.getImageLinks(), image);
            imlink.setText(selectedBook.getImageLinks());
        }
    }

    @FXML
    private Button Action;
    @FXML
    private Button ADDMODE;
    @FXML
    private Button DELETEMODE;
    @FXML
    private Button UPDATEMODE;
    @FXML
    private HBox container;
    @FXML
    private Button changeImage;

    @FXML
    public void addMode() {
        changeImage.setVisible(true);
        changeImage.setDisable(false);
        Action.setText("Add Book");
        Action.setOnAction(event -> {
            try {
                add();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        ADDMODE.getStyleClass().setAll("modeselected");
        handleSelectedMode(ADDMODE, DELETEMODE, UPDATEMODE);
        DELETEMODE.getStyleClass().setAll("button");
        UPDATEMODE.getStyleClass().setAll("button");
    }

    @FXML
    public void deleteMode() {
        changeImage.setVisible(false);
        changeImage.setDisable(true);
        Action.setText("Delete Book");
        Action.setOnAction(event -> {
            try {
                delete();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        DELETEMODE.getStyleClass().setAll("modeselected");
        handleSelectedMode(DELETEMODE, ADDMODE, UPDATEMODE);
        ADDMODE.getStyleClass().setAll("button");
        UPDATEMODE.getStyleClass().setAll("button");
    }

    @FXML
    public void updateMode() {
        changeImage.setVisible(true);
        changeImage.setDisable(false);
        Action.setText("Update Book");
        Action.setOnAction(event -> {
            try {
                update();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        UPDATEMODE.getStyleClass().setAll("modeselected");
        handleSelectedMode(UPDATEMODE, DELETEMODE, ADDMODE);
        DELETEMODE.getStyleClass().setAll("button");
        ADDMODE.getStyleClass().setAll("button");
    }

    /**
     * handle mode button like a tab.
     *
     * @param c  selected button.
     * @param u1 unselected.
     * @param u2 unselected.
     */
    private void handleSelectedMode(Button c, Button u1, Button u2) {
        u1.setPrefHeight(c.getPrefHeight());
        u2.setPrefHeight(c.getPrefHeight());
        c.setPrefHeight(c.getPrefHeight() + (container.getPrefHeight() - c.getPrefHeight()));
    }

    public void changeImageOnAction() {
        // Mở hộp thoại chọn file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your avatar");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(changeImage.getScene().getWindow());

        if (selectedFile != null) {
            imlink.setText(selectedFile.toURI().toString());
            LoadImage.loadBookImage(imlink.getText(), image);
        } else {
            LoadImage.loadBookImage(imlink.getText(), image);
        }
    }
}
