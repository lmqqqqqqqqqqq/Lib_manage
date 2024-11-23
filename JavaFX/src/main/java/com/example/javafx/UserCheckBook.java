package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class UserCheckBook {
    @FXML
    private TableView<BorrowRS> tableView;
    @FXML
    private TableColumn<BorrowRS, String> nameColumn;
    @FXML
    private TableColumn<BorrowRS, String> authorColumn;
    @FXML
    private TableColumn<BorrowRS, String> idColumn;
    @FXML
    private TableColumn<BorrowRS, LocalDate> returnColumn;
    @FXML
    private TableColumn<BorrowRS, LocalDate> borrowColumn;
    @FXML
    private TableColumn<BorrowRS, ImageView> imageColumn;
    @FXML
    private TextField userId;

    DatabaseConnect db = new DatabaseConnect();
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, String>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, String>("author"));
        idColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, String>("bookId"));
        borrowColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, LocalDate>("borrowDate"));
        returnColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, LocalDate>("dueDate"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, ImageView>("image"));
    }

    private ObservableList<BorrowRS> loadFromDatabase(String userid) {
        ObservableList<BorrowRS> books = FXCollections.observableArrayList();
        String query = "select b.idbooks, b.image, b.title, b.author, ub.borrow_date, ub.due_date from books b join " +
                "user_books ub on ub.idbooks = b.idbooks where ub.idusers = ? and borrow = 1     ";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String idbooks = rs.getString("idbooks");
                String title = rs.getString("title");
                String author = rs.getString("author");
                LocalDate borrowDate = LocalDate.parse( rs.getString("borrow_date"));
                LocalDate returnDate = LocalDate.parse( rs.getString("due_date"));
                String imageLink = rs.getString("image");
                books.add(new BorrowRS(idbooks, title, author, borrowDate, returnDate, imageLink));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public void findingOnAction() {
        ObservableList<BorrowRS> books = loadFromDatabase(userId.getText());
        tableView.setItems(books);
    }
}
