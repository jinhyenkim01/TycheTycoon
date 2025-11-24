package com.qv.tyche_tycoon;

import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LotteryController implements ApplySettings {

  private final List<ToggleButton> toggleButtons = new ArrayList<>();
  private final Random random = new Random();

  @FXML private GridPane buttonGrid;
  @FXML private Button confirmButton;
  @FXML private Button exit;
  @FXML private Label moneyLabel;
  @FXML private Label gamesPlayedLabel;
  @FXML private Label probInfo;
  @FXML private Label pickInfoLabel;
  @FXML private Label statusLabel;
  @FXML private Label rewardInfo;

  private int totalBalls;
  private int pickCount;
  private int entryCost;
  private int[] thresholds;
  private int[] winnings;

  private double luckChance = 0.0;
  private int totalWinnings = 0;
  private boolean isGameFinished = false;

  @FXML
  public void initialize() {
    exit.setDisable(true);
    confirmButton.setDisable(true);
  }

  @Override
  public void applySettings(Difficulty settings) {
    this.entryCost = settings.getPrice();
    this.totalBalls = settings.getLotteryCount();
    this.pickCount = settings.getLotteryPick();
    this.thresholds = settings.getLotteryGoal();
    this.winnings = settings.getLotteryPayout();

    luckChance = PlayerStatistics.calculateLuck(settings);
    entryFee();
    createButton();
    updateUILeft(0);
  }

  private void entryFee() {
    PlayerStatistics.subtractMoney(this.entryCost);
    PlayerStatistics.incrementGamesPlayed();
    updateUIRight();
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

  private void updateUILeft(int currentSelected) {
    pickInfoLabel.setText("선택해야 할 번호:\n" + currentSelected + " / " + pickCount);
    if (!isGameFinished) {
      statusLabel.setText("상태:\n번호 선택 중");
      rewardInfo.setText("총 당첨금:\n...");
    } else {
      statusLabel.setText("상태:\n추첨 완료");
      rewardInfo.setText("총 당첨금:\n" + totalWinnings);
    }
  }

  private void createButton() {
    buttonGrid.getChildren().clear();
    toggleButtons.clear();

    int columns = 5;
    double btnWidth = 80;
    double btnHeight = 35;

    if (totalBalls > 45) {
      btnHeight = 28;
    }

    for (int i = 0; i < totalBalls; i++) {
      ToggleButton tb = new ToggleButton(String.valueOf(i + 1));
      tb.setPrefSize(btnWidth, btnHeight);
      tb.setStyle("-fx-base: white; -fx-font-weight: bold;");

      tb.setOnAction(e -> handleToggle(tb));

      toggleButtons.add(tb);
      buttonGrid.add(tb, i % columns, i / columns);
    }
  }

  private void handleToggle(ToggleButton clickedButton) {
    long selectedCount = toggleButtons.stream().filter(ToggleButton::isSelected).count();

    if (clickedButton.isSelected()) {
      if (selectedCount > pickCount) {
        clickedButton.setSelected(false);
      } else {
        clickedButton.setStyle("-fx-base: gray; -fx-font-weight: bold;");
      }
    } else {
      clickedButton.setStyle("-fx-base: white; -fx-font-weight: bold;");
    }

    long currentCount = toggleButtons.stream().filter(ToggleButton::isSelected).count();
    updateUILeft((int) currentCount);
    confirmButton.setDisable(currentCount != pickCount);
  }

  @FXML
  private void onConfirm() {
    List<Integer> userNumbers = new ArrayList<>();
    for (int i = 0; i < toggleButtons.size(); i++) {
      if (toggleButtons.get(i).isSelected()) {
        userNumbers.add(i + 1);
      }
    }
    Collections.sort(userNumbers);

    confirmButton.setDisable(true);
    buttonGrid.setDisable(true);
    simulate(userNumbers);
  }

  private void simulate(List<Integer> userNumbers) {
    totalWinnings = 0;
    VBox resultsContainer = new VBox(10);
    resultsContainer.setAlignment(Pos.TOP_CENTER);
    resultsContainer.setPadding(new Insets(10));

    List<Integer> allNumbers = new ArrayList<>();
    for (int i = 1; i <= totalBalls; i++) allNumbers.add(i);
    List<Integer> unchosenOrigin = new ArrayList<>(allNumbers);
    unchosenOrigin.removeAll(userNumbers);

    Label userNumLabel = new Label("나의 번호: " + userNumbers);
    userNumLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    resultsContainer.getChildren().addAll(userNumLabel);

    for (int i = 1; i <= 10; i++) {
      List<Integer> userNumsCopy = new ArrayList<>(userNumbers);
      List<Integer> unchosenNumsCopy = new ArrayList<>(unchosenOrigin);
      Collections.shuffle(userNumsCopy);
      Collections.shuffle(unchosenNumsCopy);

      List<Integer> winningTicket = new ArrayList<>();
      for (int k = 0; k < pickCount; k++) {
        double numerator = pickCount - k;
        double denominator = totalBalls - k;
        double probability = numerator / denominator;

        boolean isSuccess = false;

        if (random.nextDouble() < probability) {
          isSuccess = true;
        } else if (random.nextDouble() < luckChance) {
          isSuccess = true;
        }

        if (isSuccess) {
          if (!userNumsCopy.isEmpty()) {
            winningTicket.add(userNumsCopy.remove(0));
          } else {
            winningTicket.add(unchosenNumsCopy.remove(0));
          }
        } else {
          winningTicket.add(unchosenNumsCopy.remove(0));
        }
      }

      Collections.sort(winningTicket);

      int matches = 0;
      for (Integer num : userNumbers) {
        if (winningTicket.contains(num)) matches++;
      }

      int roundPrize = 0;
      for (int m = thresholds.length - 1; m >= 0; m--) {
        if (matches >= thresholds[m]) {
          roundPrize = winnings[m];
          break;
        }
      }
      totalWinnings += roundPrize;

      String colorStyle = "-fx-text-fill: black;";
      String bgStyle = "";

      if (matches >= 6) {
        colorStyle = "-fx-text-fill: #ff6b6b;";
        bgStyle = "-fx-font-weight: bold; -fx-font-size: 16px;";
      } else if (matches == 5) {
        colorStyle = "-fx-text-fill: #ffb84d;";
        bgStyle = "-fx-font-weight: bold; -fx-font-size: 15px;";
      } else if (matches == 4) {
        colorStyle = "-fx-text-fill: #5cd65c;";
        bgStyle = "-fx-font-weight: bold;";
      } else if (matches == 3) {
        colorStyle = "-fx-text-fill: #4da6ff;";
        bgStyle = "-fx-font-weight: bold;";
      }

      HBox row = new HBox(10);
      row.setAlignment(Pos.CENTER_LEFT);
      row.setStyle("-fx-padding: 5; -fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");

      Label roundLbl = new Label(i + "회차:");
      roundLbl.setPrefWidth(40);

      Label winNumsLbl = new Label(winningTicket.toString());
      winNumsLbl.setPrefWidth(260);
      winNumsLbl.setStyle(colorStyle + bgStyle);

      Label matchLbl = new Label(matches + "개 일치");
      matchLbl.setPrefWidth(80);

      Label prizeLbl = new Label("+" + roundPrize);
      prizeLbl.setStyle("-fx-text-fill: #333333;");

      row.getChildren().addAll(roundLbl, winNumsLbl, matchLbl, prizeLbl);
      resultsContainer.getChildren().add(row);
    }

    isGameFinished = true;
    updateUILeft(pickCount);
    exit.setDisable(false);

    ScrollPane scrollPane = new ScrollPane(resultsContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background-color: transparent;");

    BorderPane parent = (BorderPane) buttonGrid.getParent();
    parent.setCenter(scrollPane);
  }

  @FXML
  private void onExit() throws Exception {
    PlayerStatistics.addMoney(totalWinnings);
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
