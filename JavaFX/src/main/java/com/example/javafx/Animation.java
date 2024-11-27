package com.example.javafx;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Animation {
    /**
     * fade animation.
     */
    public static void fadeAnimation(Label label) {
        FadeTransition fadeInTransition = new FadeTransition();
        fadeInTransition.setNode(label);  // Gán Label vào hiệu ứng
        fadeInTransition.setDuration(Duration.seconds(1));  // Thời gian fade in
        fadeInTransition.setFromValue(0);  // Từ độ trong suốt là 0 (không nhìn thấy)
        fadeInTransition.setToValue(1);    // Đến độ trong suốt là 1 (hoàn toàn nhìn thấy)

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));

        FadeTransition fadeOutTransition = new FadeTransition();
        fadeOutTransition.setNode(label);  // Gán Label vào hiệu ứng
        fadeOutTransition.setDuration(Duration.seconds(1));  // Thời gian fade in
        fadeOutTransition.setFromValue(1);  // Từ độ trong suốt là 1 ( nhìn thấy)
        fadeOutTransition.setToValue(0);    // Đến độ trong suốt là 0 (khong nhìn thấy)

        SequentialTransition sequentialTransition = new SequentialTransition(fadeInTransition, pauseTransition, fadeOutTransition);
        sequentialTransition.play();
    }

    public static void translateAnimation(AnchorPane pane) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(pane);
        translateTransition.setDuration(Duration.seconds(0.25));
        translateTransition.setFromX(-200);
        translateTransition.setToX(0);
        translateTransition.play();
    }

}
