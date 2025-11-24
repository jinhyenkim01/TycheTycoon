package com.qv.tyche_tycoon;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameOver extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Font.loadFont(
        Objects.requireNonNull(getClass().getResourceAsStream("fonts/BMJUA_ttf.ttf")), 14);

    Parent root = FXMLLoader.load(getClass().getResource("gameOver.fxml"));
    primaryStage.setTitle("게임 오버");
    Scene scene = new Scene(root);

    scene
        .getStylesheets()
        .add(
            Objects.requireNonNull(getClass().getResource("styles/tyche_tycoon.css"))
                .toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
