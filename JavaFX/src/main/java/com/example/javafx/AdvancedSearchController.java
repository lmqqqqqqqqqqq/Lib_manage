package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchController {

    DatabaseConnect databaseConnect = new DatabaseConnect();

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
    private MenuButton SortBy;
    @FXML
    private MenuButton Language;
    @FXML
    private TilePane resultpane;
    @FXML
    public void initialize() {
        Language.getItems().clear();
        for(String a : AdvancedSearch.lang) {
            MenuItem item = new MenuItem(a);
            item.setOnAction(event -> Language.setText(item.getText()));
            Language.getItems().add(item);
        }

        SortBy.getItems().clear();
        for(String a : AdvancedSearch.SortBy) {
            MenuItem item = new MenuItem(a);
            item.setOnAction(event -> SortBy.setText(item.getText()));
            SortBy.getItems().add(item);
        }
    }
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

        // process input to show the result
        AdvancedSearch Search = new AdvancedSearch();
        List<Object> params = new ArrayList<>();

        StringBuilder Q = new StringBuilder(Search.process(title, author, subject, publisher, isbn, language, year, sortBy, params));
        List<Books> result = Search.search(Q.toString(), params, databaseConnect.connect());

        resultpane.getChildren().clear();
        if (result.isEmpty()) {
            System.out.println("No results found.");
        } else {
            showLoad.intoBox(resultpane, result);
        }
    }
}