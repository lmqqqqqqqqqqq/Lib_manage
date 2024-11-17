package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDetailsController {
    DatabaseConnect db = new DatabaseConnect();

    @FXML
    private AnchorPane BookDetailsScene;
    @FXML
    private Label bookNameLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label descriptionText;
    @FXML
    private Label idLabel;
    @FXML
    private Label createdDateLabel;
    @FXML
    private Label ISBNLabel;

    public void backToSearchOnAction() throws IOException {
        SceneSwitcher.switchBetweenPage(BookDetailsScene, "AdvancedSearchScene.fxml");
    }

    public void initialize() {
        String id = resultBookShow.id_book;
        String query = "select * from books where idbooks = ?";
        try(Connection connection = db.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                bookNameLabel.setText(resultSet.getString("title"));
                authorLabel.setText(resultSet.getString("author"));
                publisherLabel.setText(resultSet.getString("publisher"));
                ISBNLabel.setText(resultSet.getString("ISBN"));
                idLabel.setText(resultSet.getString("idbooks"));
                createdDateLabel.setText(resultSet.getString("created_date"));
                descriptionText.setText(resultSet.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}