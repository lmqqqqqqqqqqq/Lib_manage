package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    @FXML
    public void yourBookOnClick() {
        mainPane.setVisible(false);
        advancedSearchPane.setVisible(false);
        searchPaneInMain.setVisible(true);
        searchButtonInMain.setVisible(true);
        yourBookPane.setVisible(true);
    }
    @FXML
    public void mainPaneOnClick() {
        yourBookPane.setVisible(false);
        advancedSearchPane.setVisible(false);
        searchPaneInMain.setVisible(true);
        searchButtonInMain.setVisible(true);
        mainPane.setVisible(true);
    }
    @FXML
    public void advancedSearchPaneOnClick() {
        mainPane.setVisible(false);
        yourBookPane.setVisible(false);
        searchButtonInMain.setVisible(false);
        searchPaneInMain.setVisible(false);
        advancedSearchPane.setVisible(true);
    }
    @FXML
    public void profilePaneOnClick() {
        mainPane.setVisible(false);
        yourBookPane.setVisible(false);
        advancedSearchPane.setVisible(false);
        profilePane.setVisible(true);
    }

}
