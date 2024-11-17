package com.example.javafx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class addBookController {

    DatabaseConnect Connect = new DatabaseConnect();

    @FXML
    private TableView tableView;

    @FXML
    public void initialize() throws Exception {
        ObservableList<Books> show = (ObservableList<Books>) AdvancedSearch.getAllBooks(Connect.connect());
        tableView.setItems(show);
    }
}
