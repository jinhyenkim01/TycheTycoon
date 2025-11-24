package com.qv.tyche_tycoon;

import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RacingHorseController implements ApplySettings {

  private final Random random = new Random();
  private final List<ProgressBar> horseBars = new ArrayList<>();
  private final List<HBox> horseRows = new ArrayList<>();
  private final List<Integer> finishedRankings = new ArrayList<>();

  @FXML private GridPane raceGrid;
  @FXML private TilePane selectionPane;
  @FXML private Button exit;
  @FXML private Label moneyLabel;
  @FXML private Label gamesPlayedLabel;
  @FXML private Label probInfo;
  @FXML private Label myHorseInfo;
  @FXML private Label triesInfo;
  @FXML private Label rewardInfo;

  private int[] horsePositions;
  private int entryCost;
  private int numHorses;
  private int raceDistance;
  private int[] rankThresholds;
  private int[] winnings;

  private int myHorseIndex = -1;
  private int currentWinning = 0;
  private boolean raceInProgress = false;
  private double luckChance = 0.0;

  @FXML
  public void initialize() {
    exit.setDisable(true);
  }

  @Override
  public void applySettings(Difficulty settings) {
    this.entryCost = settings.getPrice();
    this.numHorses = settings.getHorseCount();
    this.raceDistance = settings.getHorseLength();
    this.rankThresholds = settings.getHorseGoal();
    this.winnings = settings.getHorsePayout();

    luckChance = PlayerStatistics.calculateLuck(settings);
    entryFee();
    setupScreen();
  }

  private void entryFee() {
    PlayerStatistics.subtractMoney(this.entryCost);
    PlayerStatistics.incrementGamesPlayed();
    updateUIRight();
  }

  private void setupScreen() {
    selectionPane.setVisible(true);
    raceGrid.setVisible(false);
    selectionPane.getChildren().clear();

    for (int i = 0; i < numHorses; i++) {
      final int index = i;
      Button horseBtn = new Button((i + 1) + "번 말");

      horseBtn.setPrefWidth(100);
      horseBtn.setPrefHeight(60);
      horseBtn.setStyle("-fx-font-size: 16px;");

      horseBtn.setOnAction(e -> select(index));
      selectionPane.getChildren().add(horseBtn);
    }
  }

  private void select(int index) {
    if (raceInProgress) return;
    this.myHorseIndex = index;

    selectionPane.setVisible(false);
    raceGrid.setVisible(true);
    getHorse();

    updateUILeft("선택: " + (myHorseIndex + 1) + "번\n경기 진행 중...");
    raceInProgress = true;
    animateRace();
  }

  private void getHorse() {
    raceGrid.getChildren().clear();
    horseBars.clear();
    horseRows.clear();
    horsePositions = new int[numHorses];
    finishedRankings.clear();

    for (int i = 0; i < numHorses; i++) {
      HBox container = new HBox(10);
      container.setAlignment(Pos.CENTER_LEFT);
      container.setPadding(new javafx.geometry.Insets(5));
      container.setPrefWidth(350);

      container.setStyle("-fx-border-color: transparent; -fx-border-width: 2px;");
      if (i == myHorseIndex) {
        container.setStyle(
            "-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: #ffe6e6;");
      }

      ProgressBar pb = new ProgressBar(0.0);
      pb.setPrefWidth(300);
      pb.setPrefHeight(20);
      pb.setStyle(
          "-fx-accent: black; -fx-control-inner-background: white; -fx-text-box-border: black;");

      container.getChildren().addAll(pb);
      horseBars.add(pb);
      horseRows.add(container);

      raceGrid.add(container, i % 2, i / 2);
    }
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

  private void updateUILeft(String status) {
    triesInfo.setText("상태:\n" + status);
    rewardInfo.setText("현재 수령 가능한 보상:\n" + currentWinning);
    if (myHorseIndex != -1) {
      myHorseInfo.setText("당신의 말:\n" + (myHorseIndex + 1) + "번 말");
      return;
    }
    myHorseInfo.setText("당신의 말:\n선택 안함");
  }

  private void animateRace() {
    Timeline timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);

    KeyFrame kf =
        new KeyFrame(
            Duration.millis(100),
            e -> {
              if (finishedRankings.size() >= 2) {
                timeline.stop();
                finalizeRace();
                return;
              }
              moveHorses();
            });

    timeline.getKeyFrames().add(kf);
    timeline.play();
  }

  private void moveHorses() {
    List<Integer> potentialFinishers = calculateMovements();
    processRankings(potentialFinishers);
    updateProgressBars();
  }

  private List<Integer> calculateMovements() {
    List<Integer> finishers = new ArrayList<>();

    for (int i = 0; i < numHorses; i++) {
      if (finishedRankings.contains(i)) continue;
      boolean moved = tryMoveHorse(i);
      if (moved && horsePositions[i] >= raceDistance) {
        finishers.add(i);
      }
    }
    return finishers;
  }

  private boolean tryMoveHorse(int i) {
    if (random.nextDouble() < 0.40) {
      horsePositions[i]++;
      return true;
    }
    if (i == myHorseIndex && random.nextDouble() < luckChance) {
      horsePositions[i]++;
      return true;
    }
    return false;
  }

  private void processRankings(List<Integer> potentialFinishers) {
    if (potentialFinishers.isEmpty()) return;
    if (finishedRankings.isEmpty()) {
      resolveTie(potentialFinishers);
    } else if (finishedRankings.size() == 1) {
      resolveTie(potentialFinishers);
    }
  }

  private void updateProgressBars() {
    for (int i = 0; i < numHorses; i++) {
      double progress = (double) horsePositions[i] / raceDistance;
      if (finishedRankings.contains(i)) {
        progress = 1.0;
      } else if (progress >= 1.0) {
        progress = 0.95;
      }
      horseBars.get(i).setProgress(progress);
    }
  }

  private void resolveTie(List<Integer> candidates) {
    if (candidates.size() == 1) {
      int winner = candidates.get(0);
      finishedRankings.add(winner);
    } else {
      int winnerIndex = random.nextInt(candidates.size());
      int winner = candidates.get(winnerIndex);

      finishedRankings.add(winner);

      for (int i = 0; i < candidates.size(); i++) {
        int horseIdx = candidates.get(i);
        if (horseIdx != winner) {
          horsePositions[horseIdx]--;
        }
      }
    }
  }

  private void finalizeRace() {
    raceInProgress = false;

    int firstPlace = finishedRankings.get(0);
    int secondPlace = finishedRankings.get(1);

    int myRank = -1;
    if (myHorseIndex == firstPlace) myRank = 1;
    else if (myHorseIndex == secondPlace) myRank = 2;

    String resultText = "1등: " + (firstPlace + 1) + "번\n2등: " + (secondPlace + 1) + "번";

    horseBars.get(firstPlace).setStyle("-fx-accent: gold; -fx-control-inner-background: white;");
    horseBars.get(secondPlace).setStyle("-fx-accent: silver; -fx-control-inner-background: white;");

    calculateReward(myRank);
    updateUILeft(resultText);

    exit.setDisable(false);
  }

  private void calculateReward(int rank) {
    currentWinning = 0;
    if (rank == -1) return;

    int maxWin = 0;
    for (int i = 0; i < rankThresholds.length; i++) {
      if (rank <= rankThresholds[i]) {
        if (winnings[i] > maxWin) {
          maxWin = winnings[i];
        }
      }
    }
    currentWinning = maxWin;
    rewardInfo.setText("최종 보상:\n" + currentWinning);
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
