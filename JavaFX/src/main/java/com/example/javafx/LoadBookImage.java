package com.example.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadBookImage {

    public static void loadBookImage(String imagepath, ImageView image) {
        if(imagepath.equals("No image available") || imagepath.isEmpty()) {
            Image defaultImage = new Image(ProfileController.class.getResource("/com/example/image/book.png").toExternalForm());
            image.setImage(defaultImage);
            image.setPreserveRatio(false);
        } else {
            try {//load trong api
                image.setImage(new Image(imagepath));
                System.out.println(imagepath + "==========");
                image.setPreserveRatio(false);
            } catch (Exception e) { //load trong database
                Image defaultImage = new Image(ProfileController.class.getResource(imagepath).toExternalForm());
                image.setImage(defaultImage);
                image.setPreserveRatio(false);
            }
        }
    }
}
