package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BorrowManagement {
    DatabaseConnect Connect = new DatabaseConnect();
    @FXML
    private TextField idUser;
    @FXML
    private TextField UserName;
    @FXML
    private TextField Role;
    @FXML
    private TextField BorrowDate;
    @FXML
    private TextField DueDate;
    @FXML
    private TextField idBook;
    @FXML
    private TextField ISBN;
    @FXML
    private TextField Title;
    @FXML
    private TextField Author;
    @FXML
    private TableColumn<BorrowRS, String> UserID;
    @FXML
    private TableColumn<BorrowRS, String> BookID;
    @FXML
    private TableColumn<BorrowRS, Integer> Borrow;
    @FXML
    private TableColumn<BorrowRS, LocalDate> Borrowdate;
    @FXML
    private TableColumn<BorrowRS, LocalDate> Duedate;
    @FXML
    private TableView<BorrowRS> BorrowTable;
    ObservableList<BorrowRS> borr = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        UserID.setCellValueFactory(new PropertyValueFactory<BorrowRS, String>("id"));
        BookID.setCellValueFactory(new PropertyValueFactory<BorrowRS, String>("bookId"));
        Borrow.setCellValueFactory(new PropertyValueFactory<BorrowRS, Integer>("borrow"));
        Borrowdate.setCellValueFactory(new PropertyValueFactory<BorrowRS, LocalDate>("borrowDate"));
        Duedate.setCellValueFactory(new PropertyValueFactory<BorrowRS, LocalDate>("dueDate"));

    }

    @FXML
    public void handleROW() {
        BorrowRS borrow = BorrowTable.getSelectionModel().getSelectedItem();
        if (borrow != null) {

        }
    }

    private User getUser(int id) throws Exception {
        StringBuilder Q = new StringBuilder("Select username,staff from users where idusers = ?");
        PreparedStatement ps = DatabaseConnect.getconnect().prepareStatement(Q.toString());
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String UserName = rs.getString("username");
            if (rs.getBoolean("staff")) {
                return new Staff(id, UserName);
            } else {
                return new Members(id, UserName);
            }
        } else {
            System.out.println("No such user");
            return null;
        }
    }

    private Books getBook(int id) throws Exception {
        StringBuilder Q = new StringBuilder("Select ISBN,title,author from books where idbooks = ?");
        PreparedStatement ps = DatabaseConnect.getconnect().prepareStatement(Q.toString());
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String ISBN = rs.getString("ISBN");
            String Title = rs.getString("title");
            String Author = rs.getString("author");
            return new Books(ISBN, Title, Author);
        } else {
            System.out.println("No such book");
            return null;
        }
    }

    private List<BorrowRS> getAllrs() throws Exception {
        StringBuilder Q = new StringBuilder("Select * from user_books");
        PreparedStatement ps = DatabaseConnect.getconnect().prepareStatement(Q.toString());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int IDUSER = rs.getInt("idusers");
            int IDBOOK = rs.getInt("idbooks");
            boolean borrow
        }
    }
}
