package com.qv.tyche_tycoon;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameEndController implements Initializable {

  @FXML private Label moneyLabel;
  @FXML private Button exitButton;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateStats();
  }

  private void updateStats() {
    moneyLabel.setText("최종 스코어: " + PlayerStatistics.getMoney());
  }

  @FXML
  private void exitApplication() {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();
  }
}
