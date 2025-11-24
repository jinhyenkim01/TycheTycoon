package com.qv.tyche_tycoon;

import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DiceController implements ApplySettings {

  private final List<ImageView> diceImages = new ArrayList<>();
  private final List<Image> faceImages = new ArrayList<>();
  private final Random random = new Random();

  @FXML private GridPane diceGrid;
  @FXML private Button rollButton;
  @FXML private Button exit;

  @FXML private Label moneyLabel;
  @FXML private Label gamesPlayedLabel;
  @FXML private Label probInfo;
  @FXML private Label sumInfo;
  @FXML private Label triesInfo;
  @FXML private Label rewardInfo;
  @FXML private Label lockInfo;

  private int entryCost;
  private int numDice;
  private int currentTries = 0;
  private int maxTries;
  private int[] thresholds;
  private int[] winnings;
  private boolean[] lockedDice;
  private int[] diceValues;
  private int currentWinning = 0;
  private double luckChance = 0.0;

  @FXML
  public void initialize() {
    exit.setDisable(true);
    loadImages();
  }

  private void loadImages() {
    faceImages.clear();
    for (int i = 1; i <= 6; i++) {
      try {
        String path = "/com/qv/tyche_tycoon/images/dice" + i + ".png";
        faceImages.add(new Image(getClass().getResource(path).toExternalForm()));
      } catch (Exception e) {
        System.err.println("Error loading image dice" + i + ": " + e.getMessage());
      }
    }
  }

  @Override
  public void applySettings(Difficulty settings) {
    this.entryCost = settings.getPrice();
    this.numDice = settings.getDiceCount();
    this.maxTries = settings.getDiceMax();
    this.thresholds = settings.getDiceThresholds();
    this.winnings = settings.getDicePayout();

    luckChance = PlayerStatistics.calculateLuck(settings);
    entryFee();
    createDice();
  }

  private void entryFee() {
    PlayerStatistics.subtractMoney(this.entryCost);
    PlayerStatistics.incrementGamesPlayed();
    updateUIRight();
    updateUILeft(numDice);
  }

  private void updateUIRight() {
    moneyLabel.setText("돈:\n" + PlayerStatistics.getMoney());
    gamesPlayedLabel.setText(
        "현재 턴:\n"
            + PlayerStatistics.getTotalGamesPlayed()
            + "/"
            + PlayerStatistics.getMaxGamesPlayed());
    probInfo.setText("카지노에 쓴 돈:\n" + PlayerStatistics.getMoneySpent());
  }

  private void updateUILeft(int sum) {
    sumInfo.setText("현재 주사위 합:\n" + sum);
    triesInfo.setText("시도 횟수:\n" + currentTries + "/" + maxTries);
    rewardInfo.setText("현재 수령 가능한 보상:\n" + currentWinning);
    lockInfo.setText("주사위를 클릭하면\n값을 고정할 수 있습니다.");
  }

  private void createDice() {
    diceGrid.getChildren().clear();
    diceImages.clear();

    lockedDice = new boolean[numDice];
    diceValues = new int[numDice];

    for (int i = 0; i < numDice; i++) {
      if (faceImages.isEmpty()) break;

      int initialFaceIndex = 0;
      diceValues[i] = 1;

      ImageView imgView = new ImageView(faceImages.get(initialFaceIndex));
      imgView.setFitWidth(80);
      imgView.setFitHeight(80);
      imgView.setPreserveRatio(true);

      final int index = i;
      imgView.setOnMouseClicked(e -> toggle(index, imgView));

      diceImages.add(imgView);
      diceGrid.add(imgView, i % 5, i / 5);
    }
  }

  private void toggle(int index, ImageView view) {
    if (rollButton.isDisabled() || currentTries >= maxTries) return;

    lockedDice[index] = !lockedDice[index];

    if (lockedDice[index]) {
      view.setOpacity(0.5);
    } else {
      view.setOpacity(1.0);
    }
  }

  @FXML
  private void onRollDice() {
    if (currentTries >= maxTries) return;
    exit.setDisable(true);
    rollButton.setDisable(true);
    currentTries++;
    animate();
  }

  private void animate() {
    Timeline timeline = new Timeline();
    for (int i = 0; i < 30; i++) {
      timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 50), e -> randomize()));
    }
    timeline.setOnFinished(
        e -> {
          finalizeRoll();
          enableButton();
        });
    timeline.play();
  }

  private void enableButton() {
    if (currentTries < maxTries) {
      rollButton.setDisable(false);
    }
    exit.setDisable(false);
  }

  private void randomize() {
    for (int i = 0; i < diceImages.size(); i++) {
      if (!lockedDice[i]) {
        diceImages.get(i).setImage(faceImages.get(random.nextInt(6)));
      }
    }
  }

  private void finalizeRoll() {
    List<Integer> results = new ArrayList<>();

    for (int i = 0; i < numDice; i++) {
      if (!lockedDice[i]) {
        int val = random.nextInt(6);
        if (val != 5) {
          if (random.nextDouble() < luckChance) {
            val = 5;
          }
        }
        diceValues[i] = val + 1;
        diceImages.get(i).setImage(faceImages.get(val));
      }
      results.add(diceValues[i]);
    }
    updateStats(results);
  }

  private void updateStats(List<Integer> results) {
    int sum = 0;
    for (Integer result : results) {
      sum += result;
    }

    currentWinning = 0;

    for (int i = thresholds.length - 1; i >= 0; i--) {
      if (sum >= thresholds[i]) {
        currentWinning = winnings[i];
        break;
      }
    }

    updateUILeft(sum);
  }

  @FXML
  private void onExit() throws Exception {
    PlayerStatistics.addMoney(currentWinning);
    if (PlayerStatistics.getMoney() <= (long) 1000) {
      gameOver();
      return;
    }
    if (PlayerStatistics.getTotalGamesPlayed() >= PlayerStatistics.getMaxGamesPlayed()) {
      gameEnd();
      return;
    }
    exit();
  }

  private void exit() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage) exit.getScene().getWindow();
    Scene currentScene = stage.getScene();

    currentScene.setRoot(root);
  }

  private void gameOver() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("gameOver.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage) exit.getScene().getWindow();
    Scene currentScene = stage.getScene();

    currentScene.setRoot(root);
  }

  private void gameEnd() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("gameEnd.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage) exit.getScene().getWindow();
    Scene currentScene = stage.getScene();

    currentScene.setRoot(root);
  }
}
