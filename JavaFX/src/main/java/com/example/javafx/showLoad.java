package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.util.List;

public class showLoad {
    /**
     * use for Box.
     * @param target
     * @param result
     * @throws IOException
     */
    @FXML
    public static void intoBox(HBox target, List<Books> result) throws IOException {
        for (Books b : result) {
            FXMLLoader loader;
            if(DarkModeController.darkMode.get()) {
                loader = new FXMLLoader(showLoad.class.getResource("/com/example/javafx/DarkresultBookShow.fxml"));
                AnchorPane bookPane = loader.load();
                resultBookShow controller = loader.getController();
                controller.setOutputData(b.getImageLinks(), b.getTitle(), b.getAuthor(), b.getId(), b.getRating(), b);
                bookPane.getStyleClass().add("left_but");
                target.setSpacing(40);
                target.getChildren().add(bookPane);
            }
            else {
                loader = new FXMLLoader(showLoad.class.getResource("/com/example/javafx/resultBookShow.fxml"));
                AnchorPane bookPane = loader.load();
                resultBookShow controller = loader.getController();
                controller.setOutputData(b.getImageLinks(), b.getTitle(), b.getAuthor(), b.getId(), b.getRating(), b);
                bookPane.getStyleClass().add("left_but");
                target.setSpacing(40);
                target.getChildren().add(bookPane);
            }
        }
    }

    /**
     * use for title pane
     * @param target
     * @param result
     * @throws IOException
     */
    @FXML
    public static void intoBox(TilePane target, List<Books> result) throws IOException {
        for (Books b : result) {
            FXMLLoader loader;
            if (DarkModeController.darkMode.get()) {
                loader = new FXMLLoader(showLoad.class.getResource("/com/example/javafx/DarkresultBookShow.fxml"));
            }
            else {
                loader = new FXMLLoader(showLoad.class.getResource("/com/example/javafx/resultBookShow.fxml"));
            }
            AnchorPane bookPane = loader.load();
            resultBookShow controller = loader.getController();
            controller.setOutputData(b.getImageLinks(), b.getTitle(), b.getAuthor(), b.getId(), b.getRating(), b);
            bookPane.getStyleClass().add("left_but");
            target.getChildren().add(bookPane);
        }
    }
}
