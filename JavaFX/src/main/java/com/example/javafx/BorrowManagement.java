package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class BorrowManagement {
    @FXML
    private Label message;
    @FXML
    private TextField idUser;
    @FXML
    private TextField UserName;
    @FXML
    private TextField Role;
    @FXML
    private ImageView userImage;
    @FXML
    private ImageView bookImage;
    @FXML
    private TextField borrowDate;
    @FXML
    private TextField dueDate;
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
    private TableColumn<BorrowRS, LocalDate> Borrowdate;
    @FXML
    private TableColumn<BorrowRS, LocalDate> Duedate;
    @FXML
    private TableView<BorrowRS> BorrowTable;
    ObservableList<BorrowRS> borr = FXCollections.observableArrayList();
    @FXML
    public void initialize() throws Exception {
        UserID.setCellValueFactory(new PropertyValueFactory<BorrowRS, String>("userId"));
        BookID.setCellValueFactory(new PropertyValueFactory<BorrowRS, String>("bookId"));
        Borrowdate.setCellValueFactory(new PropertyValueFactory<BorrowRS, LocalDate>("borrowDate"));
        Duedate.setCellValueFactory(new PropertyValueFactory<BorrowRS, LocalDate>("dueDate"));

        message.setVisible(false);
        BorrowTable.setItems(borr);
        List<BorrowRS> res = getAllrs();
        borr.addAll(res);
    }

    @FXML
    public void handleROW() throws Exception {
        BorrowRS borrow = BorrowTable.getSelectionModel().getSelectedItem();
        if (borrow != null) {
            User u = getUser(borrow.getUserId());
            Books b = getBook(borrow.getBookId());
            LoadImage.loadAvatarImage(userImage, u.getAvatarLink());
            idUser.setText(String.valueOf(borrow.getUserId()));
            UserName.setText(u.getUsername());
            if (u instanceof Members) {
                Role.setText("member");
            } else {
                Role.setText("admin");
            }
            LoadImage.loadBookImage(b.getImageLinks(), bookImage);
            idBook.setText(borrow.getBookId());
            ISBN.setText(b.getIsbn());
            Title.setText(b.getTitle());
            Author.setText(b.getAuthor());
            borrowDate.setText(borrow.getBorrowDate().toString());
            dueDate.setText(borrow.getDueDate().toString());
            message.setText(calDay(borrow));
        }
    }

    private String calDay(BorrowRS borrow) {
        if (borrow.getRemainingDays() < 0) {
            message.setVisible(true);
            message.setStyle("-fx-text-fill: red;");
            return "Over Due!!!";
        } else {
            message.setVisible(true);
            message.setStyle("-fx-text-fill: black;");
            return borrow.getRemainingDays() + " Days left";
        }
    }

    private User getUser(int id) throws Exception {
        StringBuilder Q = new StringBuilder("Select username,staff,avatar from users where idusers = ?");
        PreparedStatement ps = DatabaseConnect.getconnect().prepareStatement(Q.toString());
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String UserName = rs.getString("username");
            String avatar = rs.getString("avatar");
            if (rs.getBoolean("staff")) {
                return new Staff(id, UserName, avatar);
            } else {
                return new Members(id, UserName, avatar);
            }
        } else {
            System.out.println("No such user");
            return null;
        }
    }

    private Books getBook(String id) throws Exception {
        StringBuilder Q = new StringBuilder("Select ISBN,title,author,image from books where idbooks = ?");
        PreparedStatement ps = DatabaseConnect.getconnect().prepareStatement(Q.toString());
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String ISBN = rs.getString("ISBN");
            String Title = rs.getString("title");
            String Author = rs.getString("author");
            String Image = rs.getString("image");
            return new Books(ISBN, Title, Author, Image);
        } else {
            System.out.println("No such book");
            return null;
        }
    }

    private List<BorrowRS> getAllrs() throws Exception {
        StringBuilder Q = new StringBuilder("Select * from user_books where borrow = 1");
        PreparedStatement ps = DatabaseConnect.getconnect().prepareStatement(Q.toString());
        ResultSet rs = ps.executeQuery();
        List<BorrowRS> list = new ArrayList<>();
        while (rs.next()) {
            int IDUSER = rs.getInt("idusers");
            String IDBOOK = rs.getString("idbooks");
            LocalDate BORDATE = LocalDate.parse(rs.getString("borrow_date"));
            LocalDate DUEDATE = LocalDate.parse(rs.getString("due_date"));
            list.add(new BorrowRS(IDUSER, IDBOOK, BORDATE, DUEDATE));
        }
        return list;
    }
}
