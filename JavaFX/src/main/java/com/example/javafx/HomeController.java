package com.example.javafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HomeController {
    DatabaseConnect Connect = new DatabaseConnect();
    @FXML
    private AnchorPane homeScene;
    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane suggestPane;
    @FXML
    private TextField searchPaneInMain;
    @FXML
    private ListView<Books> suggest;
    @FXML
    private HBox res;
    @FXML
    private Label error;
    @FXML
    private HBox newPane;
    @FXML
    private HBox borowedPane;
    @FXML
    private Label playLabel;
    User user = LoginController.user;


    public void initialize() throws Exception {
        resultBookShow.setParentPane(homeScene);
        loadNewBook();
        loadTrending();
        welcomeText.setText("Welcome user " + user.getLastname() + " " + user.getFirstname() + "! It's been " + numberOfDay() + " since the first time!" );
        suggest.setDisable(true);
        suggest.setVisible(false);
        homeScene.setOnMouseClicked(event -> {
            // Nếu click không nằm trong `searchPaneInMain` hoặc `suggest`, ẩn `suggest`
            if (!searchPaneInMain.isHover() && !suggest.isHover()) {
                suggest.setDisable(true);
                suggest.setVisible(false);
            }
        });
        error.setVisible(false);
    }

    public String numberOfDay() {
        LocalDate date = LocalDate.now();
        LocalDate signUpDate = LocalDate.parse(user.getDayIn());
        long days = ChronoUnit.DAYS.between(signUpDate, date);
        return days + " days";
    }


    @FXML
    public void searchClick() throws Exception {
        String key = searchPaneInMain.getText();
        if (key != null && !key.equals("")) {
            String query = "SELECT * FROM books WHERE author LIKE ? OR TITLE LIKE ? LIMIT 10";
            List<Books> result = AdvancedSearch.search(query, List.of("%" + key + "%", "%" + key + "%"), Connect.connect());
            if (result.isEmpty()) {
                error.setVisible(true);
                error.setText("No results found");
            } else {
                showLoad.intoBox(res, result);
            }
        } else {
            error.setVisible(true);
            error.setStyle("-fx-text-fill: red");
            error.setText("Please enter a valid search!!!!!!!");
        }
    }

    public void loadNewBook() throws Exception {
        User user = LoginController.user;
        try {
            showLoad.intoBox(newPane, user.getNewBooks());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadTrending() {
        String query = "SELECT * FROM books ORDER BY views DESC LIMIT 7";
        Task<List<Books>> task = MultiThread.nParamsQ(query);
        task.setOnSucceeded(event -> {
            try {
                List<Books> trendingBooks = task.getValue();
                showLoad.intoBox(borowedPane, trendingBooks);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        new Thread(task).start();
    }

    @FXML
    public void showSuggest() {
        error.setVisible(false);
        suggest.setVisible(true);
        suggest.setDisable(false);
    }

    public void playClick() throws IOException {
        SceneSwitcher.switchPage(homeScene, "wordle.fxml");
    }

    public void playEntered() {
        playLabel.setStyle(" -fx-text-fill: #23ff00; " +
                            "  -fx-underline: true; " +
                            "-fx-cursor: hand;" );
    }

    public void playExited() {
        playLabel.setStyle(null);
    }

    private Timeline debounceTimer = new Timeline();
    @FXML
    public void handleKey() {
        String key = searchPaneInMain.getText();
        debounceTimer.stop();
        if (key.isEmpty()) {
            suggest.getItems().clear();
            return;
        }
        // sau 300ms moi chay task
        debounceTimer = new Timeline(new KeyFrame(Duration.millis(300), event -> runINPUT(key)));
        debounceTimer.setCycleCount(1);
        debounceTimer.play();
    }
    private void runINPUT(String key) {
        String Query = "SELECT * FROM books WHERE author LIKE ? OR title LIKE ? LIMIT 7";
        Task<List<Books>> task = MultiThread.keyType(Query, key);
        task.setOnSucceeded(event -> {
            List<Books> result = task.getValue();
            suggest.getItems().setAll(result);
            setSuggestCell();
        });
        // if errror
        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            exception.printStackTrace();
        });
        new Thread(task).start();
    }
    private void setSuggestCell() {
        suggest.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Books book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox box = new HBox(10);
                    box.setAlignment(Pos.CENTER_LEFT);

                    ImageView bookImage = new ImageView();
                    LoadImage.loadBookImage(book.getImageLinks(), bookImage);
                    bookImage.setFitWidth(50);
                    bookImage.setFitHeight(50);
                    bookImage.setPreserveRatio(true);

                    VBox textContainer = new VBox(5);
                    Label bookTitle = new Label(book.getTitle());
                    bookTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                    Label bookAuthor = new Label(book.getAuthor());
                    bookAuthor.setStyle("-fx-font-size: 14px;");
                    textContainer.getChildren().addAll(bookTitle, bookAuthor);
                    box.getChildren().addAll(bookImage, textContainer);

                    box.setOnMouseClicked(event -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookDetails.fxml"));
                        AnchorPane newContent = null;
                        try {
                            newContent = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        BookDetailsController controller = loader.getController();
                        controller.initialize(book, newContent);
                        homeScene.getChildren().add(newContent);
                    });

                    setGraphic(box);
                }
            }
        });
    }

}
