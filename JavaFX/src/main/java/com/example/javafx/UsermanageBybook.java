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

public class UsermanageBybook {

    @FXML
    private TableView<BorrowRS> tableView;
    @FXML
    private TableColumn<BorrowRS, String> nameColumn;
    @FXML
    private TableColumn<BorrowRS, Integer> idColumn;
    @FXML
    private TableColumn<BorrowRS, LocalDate> returnColumn;
    @FXML
    private TableColumn<BorrowRS, LocalDate> borrowColumn;
    @FXML
    private TableColumn<BorrowRS, ImageView> imageColumn;
    @FXML
    private TextField bookId;

    DatabaseConnect db = new DatabaseConnect();

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, String>("userName"));
        idColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, Integer>("userId"));
        borrowColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, LocalDate>("borrowDate"));
        returnColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, LocalDate>("dueDate"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<BorrowRS, ImageView>("image"));
    }

    private ObservableList<BorrowRS> loadFromDatabase(String bookId) {
        ObservableList<BorrowRS> books = FXCollections.observableArrayList();
        String query = "select u.idusers, u.avatar, u.username, ub.borrow_date, ub.due_date from users u join " +
                "user_books ub on ub.idusers = u.idusers where ub.idbooks = ? and borrow = 1     ";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idusers = rs.getInt("idusers");
                String name = rs.getString("username");
                LocalDate borrowDate = LocalDate.parse(rs.getString("borrow_date"));
                LocalDate returnDate = LocalDate.parse(rs.getString("due_date"));
                String imageLink = rs.getString("avatar");
                books.add(new BorrowRS(idusers, name, borrowDate, returnDate, imageLink));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public void findingOnAction() {
        ObservableList<BorrowRS> books = loadFromDatabase(bookId.getText());
        tableView.setItems(books);
    }
}
