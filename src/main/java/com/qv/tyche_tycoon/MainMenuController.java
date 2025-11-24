package com.qv.tyche_tycoon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainMenuController {

  @FXML private Label moneyLabel;
  @FXML private Label gamesPlayedLabel;
  @FXML private Label moneySpentLabel;
  @FXML private Label info;

  @FXML
  public void initialize() {
    moneyLabel.setText("돈: " + PlayerStatistics.getMoney());
    gamesPlayedLabel.setText(
        "현재 턴: "
            + PlayerStatistics.getTotalGamesPlayed()
            + "/"
            + PlayerStatistics.getMaxGamesPlayed());
    moneySpentLabel.setText("카지노에 쓴 돈: " + PlayerStatistics.getMoneySpent());
    resetInfo();
  }

  public void resetInfo() {
    info.setText(
        "카지노에 쓴 돈이 커질 수록, 당신의 운 또한 상승합니다.\n" + "게임 시작 버튼에 마우스를 올리면\n" + "운이 어떻게 작용하는지 확인할 수 있습니다.");
  }

  @FXML
  public void lottoEnter() {
    info.setText("티케의 가호:\n" + "본인이 선택한 번호가 당첨될 확률이 상승합니다.");
  }

  @FXML
  public void lottoExit() {
    resetInfo();
  }

  @FXML
  public void diceEnter() {
    info.setText("티케의 가호:\n" + "주사위가 큰 숫자를 굴릴 확률이 상승합니다.");
  }

  @FXML
  public void diceExit() {
    resetInfo();
  }

  @FXML
  public void raceEnter() {
    info.setText("티케의 가호:\n" + "본인이 선택한 경주마가 전진할 확률이 상승합니다.");
  }

  @FXML
  public void raceExit() {
    resetInfo();
  }

  private void openGameMenu(String gameName) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("selectGame.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage) moneyLabel.getScene().getWindow();
    Scene currentScene = stage.getScene();

    currentScene.setRoot(root);

    SelectGameController controller = loader.getController();
    controller.setGame(gameName);
  }

  @FXML
  private void openLottery() throws Exception {
    openGameMenu("Lottery");
  }

  @FXML
  private void openDice() throws Exception {
    openGameMenu("Dice");
  }

  @FXML
  private void openRacingHorse() throws Exception {
    openGameMenu("RacingHorse");
  }
}
