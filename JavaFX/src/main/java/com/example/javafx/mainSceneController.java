package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class mainSceneController {

    @FXML
    private AnchorPane profilePane;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField searchPaneInMain;
    @FXML
    private Button searchButtonInMain;
    @FXML
    private AnchorPane yourBookPane;
    @FXML
    private AnchorPane advancedSearchPane;

    DatabaseConnect databaseConnect = new DatabaseConnect();


    /**
     * when init show mainPane first.
     */
    @FXML
    public void initialize() {
        showPane(mainPane);
    }

    @FXML
    public void yourBookOnClick() {
        showPane(yourBookPane);
    }
    @FXML
    public void mainPaneOnClick() {
        showPane(mainPane);
    }
    @FXML
    public void advancedSearchPaneOnClick() {
        showPane(advancedSearchPane);
    }
    @FXML
    public void profilePaneOnClick() {
        showPane(profilePane);
    }

    private void showPane(AnchorPane paneToShow) {
        // Ẩn tất cả các `Pane`
        profilePane.setVisible(false);
        mainPane.setVisible(false);
        yourBookPane.setVisible(false);
        advancedSearchPane.setVisible(false);

        // Chỉ hiển thị `Pane` được chọn
        paneToShow.setVisible(true);

        // Ẩn hoặc hiển thị các phần tử bổ sung trong `mainPane`
        boolean showtoppane = (paneToShow == mainPane || paneToShow == yourBookPane);
        searchPaneInMain.setVisible(showtoppane);
        searchButtonInMain.setVisible(showtoppane);
    }

    @FXML
    private TextField Title;
    @FXML
    private TextField Author;
    @FXML
    private TextField Subject;
    @FXML
    private TextField Publisher;
    @FXML
    private TextField ISBN;
    @FXML
    private TextField Year;
    @FXML
    private MenuButton Language;
    @FXML
    private MenuButton SortBy;

    @FXML
    public void onSearchClick() throws Exception {
        String title = Title.getText();
        String author = Author.getText();
        String subject = Subject.getText();
        String publisher = Publisher.getText();
        String isbn = ISBN.getText();
        String year = Year.getText();
        String language = Language.getText();
        String sortBy = SortBy.getText();
        StringBuilder Q = new StringBuilder("SELECT * FROM books WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            Q.append(" AND title LIKE ?");
            params.add("%" + title + "%");
        }
        if (author != null && !author.isEmpty()) {
            Q.append(" AND author LIKE ?");
            params.add("%" + author + "%");
        }
        if (subject != null && !subject.isEmpty()) {
            Q.append(" AND subject LIKE ?");
            params.add("%" + subject + "%");
        }
        if (publisher != null && !publisher.isEmpty()) {
            Q.append(" AND publisher LIKE ?");
            params.add("%" + publisher + "%");
        }
        if (isbn != null && !isbn.isEmpty()) {
            Q.append(" AND isbn = ?");
            params.add(isbn);
        }
//        if (language != null && !language.isEmpty()) {
//            Q.append(" AND language = ?");
//            params.add(language);
//        }
        if (year != null && !year.isEmpty()) {
            Q.append(" AND YEAR(created_date) = ?");
            params.add(year);
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            if(sortBy.equals("Newest first")) {
                Q.append(" ORDER BY created_date DESC");
            } else {
                Q.append(" ORDER BY created_date ASC");
                }
        }
        AdvancedSearch Search = new AdvancedSearch();
        List<Books> res = Search.search(Q.toString(), params, databaseConnect.connect());
        if (res.isEmpty()) {
            System.out.println("No results found.");
        } else {
            for (Books b : res) {
                System.out.println(b.toString());
            }
        }
    }


}
