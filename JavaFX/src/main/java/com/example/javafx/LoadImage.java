package com.example.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class LoadImage {

    public static void loadBookImage(String imagepath, ImageView image) {
        if(imagepath.equals("No image available") || imagepath.isEmpty()) {
            Image defaultImage = new Image(ProfileController.class.getResource("/com/example/image/book.png").toExternalForm());
            image.setImage(defaultImage);
            image.setPreserveRatio(false);
        } else {
            try {//load trong api
                image.setImage(new Image(imagepath));
                image.setPreserveRatio(false);
            } catch (Exception e) { //load trong database
                Image defaultImage = new Image(ProfileController.class.getResource(imagepath).toExternalForm());
                image.setImage(defaultImage);
                image.setPreserveRatio(false);
            }
        }
    }

    /**
     * load image to imageview
     * @param avatarImage is .
     * @param link is.
     */
    public static void loadAvatarImage(ImageView avatarImage, String link) {
        try {
            if (!link.equals("/com/example/image/user.jpg")) {
                Image image = new Image(link);
                avatarImage.setImage(image);
            } else {
                Image defaultImage = new Image(ProfileController.class.getResource("/com/example/image/user.jpg").toExternalForm());
                avatarImage.setImage(defaultImage);
            }
            double radius = Math.min(avatarImage.getFitWidth(), avatarImage.getFitHeight()) / 2;
            Circle circle = new Circle(avatarImage.getFitWidth() / 2, avatarImage.getFitHeight() / 2, radius);
            avatarImage.setPreserveRatio(false);
            avatarImage.setStyle("-fx-border-width: 5px; -fx-border-color: #000000; -fx-border-style: solid;");
            avatarImage.setClip(circle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load in book-user(not include setClip)
     * @param avatarImage is image
     * @param link is link
     */
    public static void loadBUImage(ImageView avatarImage, String link) {
        try {
            if (!link.equals("/com/example/image/user.jpg")) {
                Image image = new Image(link);
                avatarImage.setImage(image);
            } else {
                Image defaultImage = new Image(ProfileController.class.getResource("/com/example/image/user.jpg").toExternalForm());
                avatarImage.setImage(defaultImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
