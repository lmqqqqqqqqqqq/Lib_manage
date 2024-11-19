package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    User user = LoginController.user;


    public void initialize() {
        resultBookShow.setParentPane(homeScene);
        welcomeText.setText("Welcome user " + user.getLastname() + " " + user.getFirstname() + "! It's been " + numberOfDay() + " since the first time!" );
        suggest.setDisable(true);
        suggest.setVisible(false);
        homeScene.setOnMouseClicked(event -> {
            if (event.getSource() != searchPaneInMain) {
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
            String query = "SELECT * FROM books WHERE author LIKE ? OR TITLE LIKE ?";
            List<Books> result = AdvancedSearch.search(query, List.of("%" + key + "%", "%" + key + "%"), Connect.connect());
            showLoad.intoBox(res, result);
        } else {
            error.setVisible(true);
            error.setStyle("-fx-text-fill: red");
            error.setText("Please enter a valid search!!!!!!!");
        }
    }

    @FXML
    public void showSuggest() {
        error.setVisible(false);
        suggest.setVisible(true);
        suggest.setDisable(false);
    }

    @FXML
    public void handleKey() throws Exception {
        System.out.println("key press");
        String key = searchPaneInMain.getText();
        if (key.isEmpty()) {
            suggest.getItems().clear();
            return;
        }
        String query = "SELECT * FROM books WHERE author LIKE ? OR title LIKE ?";
        List<Books> result;
        result = AdvancedSearch.search(query, List.of("%" + key + "%", "%" + key + "%"), Connect.connect());
        suggest.getItems().setAll(result);
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
                    Image defaultImage = new Image(ProfileController.class.getResource(book.getImageLinks()).toExternalForm());
                    bookImage.setImage(defaultImage); // Đường dẫn ảnh
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
                        controller.setBook(book, newContent);
                        homeScene.getChildren().add(newContent);
                    });
                    setGraphic(box);
                }
            }
        });
    }
}
