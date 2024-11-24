package com.example.javafx;

import com.mysql.cj.log.Log;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;


public class yourBookController {
    @FXML
    private HBox borrowed;

    @FXML
    private HBox favorite;
    @FXML
    private AnchorPane yourBookPane;
    @FXML
    private HBox nearestBook;

    @FXML
    public void initialize() throws Exception {
        resultBookShow.setParentPane(yourBookPane);
        loadBorrowed();
        loadFavorite();
        loadNearestBook();
    }

    public static int borrowedBookAmount;
    public static int favoriteBookAmount;
    @FXML
    public void loadBorrowed() throws Exception {
        borrowed.getChildren().clear();
        String Query = "SELECT books.* FROM books INNER JOIN user_books ON user_books.idbooks = books.idbooks" +
                " AND user_books.idusers = ? AND user_books.borrow = 1";
        Task<List<Books>> task = MultiThread.YourBooks(Query, LoginController.user.getId());
        task.setOnSucceeded(event -> {
            try {
                List<Books> borrowedBooks = task.getValue();
                if(borrowedBooks.size() > 0) {
                    showLoad.intoBox(borrowed, borrowedBooks);
                    borrowedBookAmount = borrowedBooks.size();
                } else {
                    borrowedBookAmount = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        new Thread(task).start();
    }

    @FXML
    public void loadFavorite() throws Exception {
        favorite.getChildren().clear();
        String Query = "SELECT books.* FROM books INNER JOIN user_books ON user_books.idbooks = books.idbooks" +
                " AND user_books.idusers = ? AND user_books.is_favourite = 1";
        Task<List<Books>> task = MultiThread.YourBooks(Query, LoginController.user.getId());
        task.setOnSucceeded(event -> {
            try {
                List<Books> favoriteBooks = task.getValue();
                if(favoriteBooks.size() > 0) {
                    showLoad.intoBox(favorite, favoriteBooks);
                    favoriteBookAmount = favoriteBooks.size();
                } else {
                    favoriteBookAmount = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        new Thread(task).start();
    }

    @FXML
    public void loadNearestBook() throws Exception {
        nearestBook.getChildren().clear();
        String Query = "SELECT books.*, user_books.currentTime FROM books INNER JOIN user_books" +
                " ON user_books.idbooks = books.idbooks AND user_books.idusers = ?" +
                " ORDER BY user_books.currentTime DESC";
        Task<List<Books>> task = MultiThread.YourBooks(Query, LoginController.user.getId());
        task.setOnSucceeded(event -> {
           try {
               List<Books> nearestBooks = task.getValue();
               if(nearestBooks.size() > 0) {
                   showLoad.intoBox(nearestBook, nearestBooks);
               } else {
                   System.out.println("no nearest found");
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
        });
        new Thread(task).start();
    }
}
