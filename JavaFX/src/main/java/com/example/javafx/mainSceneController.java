package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
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
}
