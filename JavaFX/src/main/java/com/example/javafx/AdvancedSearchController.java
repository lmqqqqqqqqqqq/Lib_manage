package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdvancedSearchController {

    DatabaseConnect databaseConnect = new DatabaseConnect();

    @FXML
    private TextField Title;
    @FXML
    private TextField Author;
    @FXML
    private TextField Genre;
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
    private AnchorPane advancedSearchPane;
    @FXML
    public void initialize() {
        resultBookShow.setParentPane(advancedSearchPane);
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
        String genre = Genre.getText();
        String publisher = Publisher.getText();
        String isbn = ISBN.getText();
        String year = Year.getText();
        String language = Language.getText();
        String sortBy = SortBy.getText();

        // process input to show the result

        List<Object> params = new ArrayList<>();

        StringBuilder Q = new StringBuilder(AdvancedSearch.process(title, author, genre, publisher, isbn, language, year, sortBy, params));
        List<Books> result = AdvancedSearch.search(Q.toString(), params, databaseConnect.connect());
        ConnectAPI api = new ConnectAPI();
        String Q1 = api.createQuery(title, author, genre, publisher, isbn, language);
        List<Books> result1 = api.getBooks(Q1, year);
        resultpane.getChildren().clear();
        if (result.isEmpty() && result1.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy sách nào phù hợp với từ khóa tìm kiếm!");
            alert.showAndWait();
            System.out.println("No results found.");
        } else {
            showLoad.intoBox(resultpane, result1);
            showLoad.intoBox(resultpane, result);
        }
    }

    @FXML
    public void onResetClick() throws Exception {
        Title.setText("");
        Author.setText("");
        Genre.setText("");
        Publisher.setText("");
        ISBN.setText("");
        Year.setText("");
        Language.setText("Language");
        SortBy.setText("Sort By");
    }
}