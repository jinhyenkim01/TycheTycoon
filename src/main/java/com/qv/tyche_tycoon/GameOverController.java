package com.qv.tyche_tycoon;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOverController implements Initializable {

  @FXML private Label moneyLabel;
  @FXML private Label gamesPlayedLabel;
  @FXML private Label probLabel;
  @FXML private Button exitButton;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateStats();
  }

  private void updateStats() {
    moneyLabel.setText("돈: " + PlayerStatistics.getMoney());
    gamesPlayedLabel.setText(
        "현재 턴: "
            + PlayerStatistics.getTotalGamesPlayed()
            + "/"
            + PlayerStatistics.getMaxGamesPlayed());
    probLabel.setText("카지노에 쓴 돈: " + PlayerStatistics.getMoneySpent());
  }

  @FXML
  private void exitApplication() {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();
  }
}
