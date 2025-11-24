package com.qv.tyche_tycoon;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TitleController implements Initializable {
  @FXML private Label title;
  @FXML private Label subtitle;
  @FXML private Button start;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateUI();
    intro();
  }

  private void updateUI() {
    title.setText("TycheTycoon\n티케타이쿤");
    subtitle.setText("May the Goddess of Fortune smile upon you...\n행운의 여신이 당신과 함께하길...");
    subtitle.setOpacity(0);
    start.setDisable(true);
  }

  private void intro() {
    title.setTranslateX(-1000);

    TranslateTransition slideEng = new TranslateTransition(Duration.seconds(2), title);
    slideEng.setToX(0);
    slideEng.setInterpolator(Interpolator.EASE_OUT);

    FadeTransition fadeSubtitle = new FadeTransition(Duration.seconds(2), subtitle);
    fadeSubtitle.setFromValue(0);
    fadeSubtitle.setToValue(1);

    PauseTransition half = new PauseTransition(Duration.seconds(1));
    half.setOnFinished(e -> start.setDisable(false));

    ParallelTransition parallel = new ParallelTransition(fadeSubtitle, half);

    SequentialTransition sequence = new SequentialTransition(slideEng, parallel);
    sequence.play();
  }

  @FXML
  private void start() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("howToPlay.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage) title.getScene().getWindow();
    Scene currentScene = stage.getScene();

    currentScene.setRoot(root);
  }
}
