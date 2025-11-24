package com.qv.tyche_tycoon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SelectGameController {

  private String selectedGame;

  @FXML private Label gameTitleLabel;
  @FXML private Label easyDescLabel;
  @FXML private Label normalDescLabel;
  @FXML private Label hardDescLabel;
  @FXML private Label moneyLabel;
  @FXML private Label gamesPlayedLabel;
  @FXML private Label moneySpentLabel;
  @FXML private Label info;

  @FXML private Button easyEnter;
  @FXML private Button normalEnter;
  @FXML private Button hardEnter;

  @FXML
  public void initialize() {
    resetInfo();
  }

  public void setGame(String game) {
    moneyLabel.setText("돈: " + PlayerStatistics.getMoney());
    gamesPlayedLabel.setText(
        "현재 턴: "
            + PlayerStatistics.getTotalGamesPlayed()
            + "/"
            + PlayerStatistics.getMaxGamesPlayed());
    this.selectedGame = game;
    moneySpentLabel.setText("카지노에 쓴 돈: " + PlayerStatistics.getMoneySpent());
    updateUI();
    disableEntry();
  }

  private void disableEntry() {
    if (selectedGame.equals("Lottery")) {
      if (PlayerStatistics.getMoney() < (long) GameStatistics.lotteryHardCost) {
        hardEnter.setDisable(true);
      }
      if (PlayerStatistics.getMoney() < (long) GameStatistics.lotteryNormalCost) {
        normalEnter.setDisable(true);
      }
      if (PlayerStatistics.getMoney() < (long) GameStatistics.lotteryEasyCost) {
        easyEnter.setDisable(true);
      }
      return;
    }

    if (selectedGame.equals("Dice")) {
      if (PlayerStatistics.getMoney() < (long) GameStatistics.diceHardCost) {
        hardEnter.setDisable(true);
      }
      if (PlayerStatistics.getMoney() < (long) GameStatistics.diceNormalCost) {
        normalEnter.setDisable(true);
      }
      if (PlayerStatistics.getMoney() < (long) GameStatistics.diceEasyCost) {
        easyEnter.setDisable(true);
      }
      return;
    }

    if (selectedGame.equals("RacingHorse")) {
      if (PlayerStatistics.getMoney() < (long) GameStatistics.horseHardCost) {
        hardEnter.setDisable(true);
      }
      if (PlayerStatistics.getMoney() < (long) GameStatistics.horseNormalCost) {
        normalEnter.setDisable(true);
      }
      if (PlayerStatistics.getMoney() < (long) GameStatistics.horseEasyCost) {
        easyEnter.setDisable(true);
      }
    }
  }

  public void resetInfo() {
    info.setText(
        "카지노에 쓴 돈이 커질 수록, 당신의 운 또한 상승합니다.\n" + "게임 시작 버튼에 마우스를 올리면\n" + "운이 어떻게 작용하는지 확인할 수 있습니다.");
  }

  @FXML
  public void easyEnter() {
    if (selectedGame.equals("Lottery")) {
      info.setText(
          "티케의 가호:\n"
              + "본인이 선택하지 않은 번호가 "
              + String.format("%.1f", PlayerStatistics.lotteryLuck("Easy") * 100)
              + "% 확률로\n본인의 번호 중 하나로 바뀝니다.");
      return;
    }

    if (selectedGame.equals("Dice")) {
      info.setText(
          "티케의 가호:\n"
              + "6이 아닌 주사위가 "
              + String.format("%.1f", PlayerStatistics.diceLuck("Easy") * 100)
              + "% 확률로\n6으로 변경됩니다.");
      return;
    }

    if (selectedGame.equals("RacingHorse")) {
      info.setText(
          "티케의 가호:\n"
              + "본인이 선택한 경마가 전진하지 못하면,\n"
              + String.format("%.1f", PlayerStatistics.horseLuck("Easy") * 100)
              + "% 확률로 무시하고 전진합니다.");
    }
  }

  @FXML
  public void easyExit() {
    resetInfo();
  }

  @FXML
  public void normalEnter() {
    if (selectedGame.equals("Lottery")) {
      info.setText(
          "티케의 가호:\n"
              + "본인이 선택하지 않은 번호가 "
              + String.format("%.1f", PlayerStatistics.lotteryLuck("Normal") * 100)
              + "% 확률로\n본인의 번호 중 하나로 바뀝니다.");
      return;
    }

    if (selectedGame.equals("Dice")) {
      info.setText(
          "티케의 가호:\n"
              + "6이 아닌 주사위가 "
              + String.format("%.1f", PlayerStatistics.diceLuck("Normal") * 100)
              + "% 확률로\n6으로 변경됩니다.");
      return;
    }

    if (selectedGame.equals("RacingHorse")) {
      info.setText(
          "티케의 가호:\n"
              + "본인이 선택한 경마가 전진하지 못하면,\n"
              + String.format("%.1f", PlayerStatistics.horseLuck("Normal") * 100)
              + "% 확률로 무시하고 전진합니다.");
    }
  }

  @FXML
  public void normalExit() {
    resetInfo();
  }

  @FXML
  public void hardEnter() {
    if (selectedGame.equals("Lottery")) {
      info.setText(
          "티케의 가호:\n"
              + "본인이 선택하지 않은 번호가 "
              + String.format("%.1f", PlayerStatistics.lotteryLuck("Hard") * 100)
              + "% 확률로\n본인의 번호 중 하나로 바뀝니다.");
      return;
    }

    if (selectedGame.equals("Dice")) {
      info.setText(
          "티케의 가호:\n"
              + "6이 아닌 주사위가 "
              + String.format("%.1f", PlayerStatistics.diceLuck("Hard") * 100)
              + "% 확률로\n6으로 변경됩니다.");
      return;
    }

    if (selectedGame.equals("RacingHorse")) {
      info.setText(
          "티케의 가호:\n"
              + "본인이 선택한 경마가 전진하지 못하면,\n"
              + String.format("%.1f", PlayerStatistics.horseLuck("Hard") * 100)
              + "% 확률로 무시하고 전진합니다.");
    }
  }

  @FXML
  public void hardExit() {
    resetInfo();
  }

  private void updateUI() {
    if (selectedGame == null) return;

    if (selectedGame.equals("Lottery")) {
      gameTitleLabel.setText("로또\t\tLottery");

      easyDescLabel.setText(
          "번호 갯수: "
              + GameStatistics.lotteryEasyCount
              + "개\n"
              + "선택: "
              + GameStatistics.lotteryEasyPick
              + "개\n"
              + GameStatistics.lotteryEasyGoal[0]
              + "개 동일 보상: "
              + GameStatistics.lotteryEasyMult[0]
              + "x\n"
              + GameStatistics.lotteryEasyGoal[1]
              + "개 동일 보상: "
              + GameStatistics.lotteryEasyMult[1]
              + "x\n"
              + GameStatistics.lotteryEasyGoal[2]
              + "개 동일 보상: "
              + GameStatistics.lotteryEasyMult[2]
              + "x\n"
              + GameStatistics.lotteryEasyGoal[3]
              + "개 동일 보상: "
              + GameStatistics.lotteryEasyMult[3]
              + "x\n\n"
              + "가격: "
              + GameStatistics.lotteryEasyCost);

      normalDescLabel.setText(
          "번호 갯수: "
              + GameStatistics.lotteryNormalCount
              + "개\n"
              + "선택: "
              + GameStatistics.lotteryNormalPick
              + "개\n"
              + GameStatistics.lotteryNormalGoal[0]
              + "개 동일 보상: "
              + GameStatistics.lotteryNormalMult[0]
              + "x\n"
              + GameStatistics.lotteryNormalGoal[1]
              + "개 동일 보상: "
              + GameStatistics.lotteryNormalMult[1]
              + "x\n"
              + GameStatistics.lotteryNormalGoal[2]
              + "개 동일 보상: "
              + GameStatistics.lotteryNormalMult[2]
              + "x\n"
              + GameStatistics.lotteryNormalGoal[3]
              + "개 동일 보상: "
              + GameStatistics.lotteryNormalMult[3]
              + "x\n\n"
              + "가격: "
              + GameStatistics.lotteryNormalCost);

      hardDescLabel.setText(
          "번호 갯수: "
              + GameStatistics.lotteryHardCount
              + "개\n"
              + "선택: "
              + GameStatistics.lotteryHardPick
              + "개\n"
              + GameStatistics.lotteryHardGoal[0]
              + "개 동일 보상: "
              + GameStatistics.lotteryHardMult[0]
              + "x\n"
              + GameStatistics.lotteryHardGoal[1]
              + "개 동일 보상: "
              + GameStatistics.lotteryHardMult[1]
              + "x\n"
              + GameStatistics.lotteryHardGoal[2]
              + "개 동일 보상: "
              + GameStatistics.lotteryHardMult[2]
              + "x\n"
              + GameStatistics.lotteryHardGoal[3]
              + "개 동일 보상: "
              + GameStatistics.lotteryHardMult[3]
              + "x\n\n"
              + "가격: "
              + GameStatistics.lotteryHardCost);
      return;
    }

    if (selectedGame.equals("Dice")) {
      gameTitleLabel.setText("주사위\t\tDice Roll");

      easyDescLabel.setText(
          "주사위 갯수: "
              + GameStatistics.diceEasyCount
              + "개\n"
              + "시도 횟수: "
              + GameStatistics.diceEasyMax
              + "번\n"
              + GameStatistics.diceEasyGoal[0]
              + " 이상 보상: "
              + GameStatistics.diceEasyMult[0]
              + "x\n"
              + GameStatistics.diceEasyGoal[1]
              + " 이상 보상: "
              + GameStatistics.diceEasyMult[1]
              + "x\n"
              + GameStatistics.diceEasyGoal[2]
              + " 이상 보상: "
              + GameStatistics.diceEasyMult[2]
              + "x\n\n"
              + "가격: "
              + GameStatistics.diceEasyCost);

      normalDescLabel.setText(
          "주사위 갯수: "
              + GameStatistics.diceNormalCount
              + "개\n"
              + "시도 횟수: "
              + GameStatistics.diceNormalMax
              + "번\n"
              + GameStatistics.diceNormalGoal[0]
              + " 이상 보상: "
              + GameStatistics.diceNormalMult[0]
              + "x\n"
              + GameStatistics.diceNormalGoal[1]
              + " 이상 보상: "
              + GameStatistics.diceNormalMult[1]
              + "x\n"
              + GameStatistics.diceNormalGoal[2]
              + " 이상 보상: "
              + GameStatistics.diceNormalMult[2]
              + "x\n\n"
              + "가격: "
              + GameStatistics.diceNormalCost);

      hardDescLabel.setText(
          "주사위 갯수: "
              + GameStatistics.diceHardCount
              + "개\n"
              + "시도 횟수: "
              + GameStatistics.diceHardMax
              + "번\n"
              + GameStatistics.diceHardGoal[0]
              + " 이상 보상: "
              + GameStatistics.diceHardMult[0]
              + "x\n"
              + GameStatistics.diceHardGoal[1]
              + " 이상 보상: "
              + GameStatistics.diceHardMult[1]
              + "x\n"
              + GameStatistics.diceHardGoal[2]
              + " 이상 보상: "
              + GameStatistics.diceHardMult[2]
              + "x\n\n"
              + "가격: "
              + GameStatistics.diceHardCost);
      return;
    }

    if (selectedGame.equals("RacingHorse")) {
      gameTitleLabel.setText("경마\t\tHorse Racing");

      easyDescLabel.setText(
          "총 경주마: "
              + GameStatistics.horseEasyCount
              + "마리\n"
              + "경주로 길이: "
              + GameStatistics.horseEasyLength
              + "\n"
              + GameStatistics.horseEasyGoal[0]
              + "등 보상: "
              + GameStatistics.horseEasyMult[0]
              + "x\n"
              + GameStatistics.horseEasyGoal[1]
              + "등 보상: "
              + GameStatistics.horseEasyMult[1]
              + "x\n\n"
              + "가격: "
              + GameStatistics.horseEasyCost);

      normalDescLabel.setText(
          "총 경주마: "
              + GameStatistics.horseNormalCount
              + "마리\n"
              + "경주로 길이: "
              + GameStatistics.horseNormalLength
              + "\n"
              + GameStatistics.horseNormalGoal[0]
              + "등 보상: "
              + GameStatistics.horseNormalMult[0]
              + "x\n"
              + GameStatistics.horseNormalGoal[1]
              + "등 보상: "
              + GameStatistics.horseNormalMult[1]
              + "x\n\n"
              + "가격: "
              + GameStatistics.horseNormalCost);

      hardDescLabel.setText(
          "총 경주마: "
              + GameStatistics.horseHardCount
              + "마리\n"
              + "경주로 길이: "
              + GameStatistics.horseHardLength
              + "\n"
              + GameStatistics.horseHardGoal[0]
              + "등 보상: "
              + GameStatistics.horseHardMult[0]
              + "x\n"
              + GameStatistics.horseHardGoal[1]
              + "등 보상: "
              + GameStatistics.horseHardMult[1]
              + "x\n\n"
              + "가격: "
              + GameStatistics.horseHardCost);
    }
  }

  private void loadDifficulty(Difficulty settings, ActionEvent event) throws Exception {
    String temp = "lottery.fxml";
    if (selectedGame.equals("Lottery")) {
      temp = "lottery.fxml";
    }
    if (selectedGame.equals("Dice")) {
      temp = "dice.fxml";
    }
    if (selectedGame.equals("RacingHorse")) {
      temp = "racingHorse.fxml";
    }
    FXMLLoader loader = new FXMLLoader(getClass().getResource(temp));
    Parent root = loader.load();

    Object controller = loader.getController();
    if (controller instanceof ApplySettings) {
      ((ApplySettings) controller).applySettings(settings);
    }

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.getScene().setRoot(root);
  }

  @FXML
  private void returnMenu() throws Exception {
    easyEnter.setDisable(false);
    normalEnter.setDisable(false);
    hardEnter.setDisable(false);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage) gameTitleLabel.getScene().getWindow();
    Scene currentScene = stage.getScene();

    currentScene.setRoot(root);
  }

  private Difficulty createSettings(String difficultyLevel) {
    if (selectedGame.equals("Lottery")) {
      if (difficultyLevel.equals("Easy")) {
        return Difficulty.forLottery(
            "Easy",
            GameStatistics.lotteryEasyCost,
            GameStatistics.lotteryEasyCount,
            GameStatistics.lotteryEasyPick,
            GameStatistics.lotteryEasyGoal,
            GameStatistics.lotteryEasyPayout);
      }
      if (difficultyLevel.equals("Normal")) {
        return Difficulty.forLottery(
            "Normal",
            GameStatistics.lotteryNormalCost,
            GameStatistics.lotteryNormalCount,
            GameStatistics.lotteryNormalPick,
            GameStatistics.lotteryNormalGoal,
            GameStatistics.lotteryNormalPayout);
      }
      if (difficultyLevel.equals("Hard")) {
        return Difficulty.forLottery(
            "Hard",
            GameStatistics.lotteryHardCost,
            GameStatistics.lotteryHardCount,
            GameStatistics.lotteryHardPick,
            GameStatistics.lotteryHardGoal,
            GameStatistics.lotteryHardPayout);
      }
    }

    if (selectedGame.equals("Dice")) {
      if (difficultyLevel.equals("Easy")) {
        return Difficulty.forDice(
            "Easy",
            GameStatistics.diceEasyCost,
            GameStatistics.diceEasyCount,
            GameStatistics.diceEasyMax,
            GameStatistics.diceEasyGoal,
            GameStatistics.diceEasyPayout);
      }
      if (difficultyLevel.equals("Normal")) {
        return Difficulty.forDice(
            "Normal",
            GameStatistics.diceNormalCost,
            GameStatistics.diceNormalCount,
            GameStatistics.diceNormalMax,
            GameStatistics.diceNormalGoal,
            GameStatistics.diceNormalPayout);
      }
      if (difficultyLevel.equals("Hard")) {
        return Difficulty.forDice(
            "Hard",
            GameStatistics.diceHardCost,
            GameStatistics.diceHardCount,
            GameStatistics.diceHardMax,
            GameStatistics.diceHardGoal,
            GameStatistics.diceHardPayout);
      }
    }

    if (selectedGame.equals("RacingHorse")) {
      if (difficultyLevel.equals("Easy")) {
        return Difficulty.forHorse(
            "Easy",
            GameStatistics.horseEasyCost,
            GameStatistics.horseEasyCount,
            GameStatistics.horseEasyLength,
            GameStatistics.horseEasyGoal,
            GameStatistics.horseEasyPayout);
      }
      if (difficultyLevel.equals("Normal")) {
        return Difficulty.forHorse(
            "Normal",
            GameStatistics.horseNormalCost,
            GameStatistics.horseNormalCount,
            GameStatistics.horseNormalLength,
            GameStatistics.horseNormalGoal,
            GameStatistics.horseNormalPayout);
      }
      if (difficultyLevel.equals("Hard")) {
        return Difficulty.forHorse(
            "Hard",
            GameStatistics.horseHardCost,
            GameStatistics.horseHardCount,
            GameStatistics.horseHardLength,
            GameStatistics.horseHardGoal,
            GameStatistics.horseHardPayout);
      }
    }
    return null;
  }

  @FXML
  private void chooseEasy(ActionEvent event) throws Exception {
    Difficulty settings = createSettings("Easy");
    if (settings != null) loadDifficulty(settings, event);
  }

  @FXML
  private void chooseNormal(ActionEvent event) throws Exception {
    Difficulty settings = createSettings("Normal");
    if (settings != null) loadDifficulty(settings, event);
  }

  @FXML
  private void chooseHard(ActionEvent event) throws Exception {
    Difficulty settings = createSettings("Hard");
    if (settings != null) loadDifficulty(settings, event);
  }
}
