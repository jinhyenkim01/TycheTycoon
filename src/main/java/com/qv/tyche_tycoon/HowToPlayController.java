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

public class HowToPlayController implements Initializable {

  @FXML private Label title;
  @FXML private Label howToPlay;
  @FXML private Button button;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateUI();
    stall();
  }

  private void updateUI() {
    title.setText("How To Play\t\t게임 설명");
    howToPlay.setText(
        "Your prayers have been answered!\n"
            + "From the Pantheons of Greek Mythology descends Tyche,\n"
            + "\tthe Goddess of Fortune who governs the whims of fate itself. \n"
            + "In the past, she was revered for her miracles that defy the most unlikely of odds\n"
            + "\tthat could save an entire civilization from the brink of ruin...\n"
            + "So naturally, you decide to abuse her power by hitting the Casino and getting filthy rich. Capitalism!\n\n"
            + "당신의 기도가 드디어 빛을 발합니다!\n"
            + "운명의 변덕 자체를 다스리는 그리스 신화의 행운의 여신, 티케가 만신전에서 당신 앞에 현신했습니다.\n"
            + "그녀의 초월적인 행운이 초래하는 기적들은 과거 수많은 왕국을 멸망으로부터 구원할 정도로 강력하죠...\n"
            + "당연히 오늘날 당신이 할 일은 정해져 있습니다. 카지노에서 대박을 터트리고 평생 떵떵거리며 삽시다! 자본주의여!");
  }

  private void nextText() {
    howToPlay.setText(
        "You start the game with "
            + PlayerStatistics.getMoney()
            + " money.\n"
            + "You can only play a maximum of "
            + PlayerStatistics.getMaxGamesPlayed()
            + " games.\n"
            + "The casino offers three different games: Lottery, Dice, and Horse Racing.\n\n"
            + "In Lottery, you buy multiple copies of a lottery ticket and calculate your winnings.\n"
            + "In Dice, you roll the dice and sum up your faces and get winnings if you roll above a certain threshold.\n"
            + "In Horse Racing, you need to place your bet on one horse that you believe will win the race.\n\n\n"
            + "게임 시작 시, "
            + PlayerStatistics.getMoney()
            + "의 돈이 주어집니다.\n"
            + "카지노에서는 게임을 총 "
            + PlayerStatistics.getMaxGamesPlayed()
            + "번 참여할 수 있습니다.\n"
            + "카지노는 세 종류의 게임 - 로또, 주사위, 경마 - 을 제공합니다.\n\n"
            + "로또에서는 로또 티켓을 여러 개 구매한 후 다른 로또 당첨 번호와 본인의 티켓을 비교하여 상금을 계산합니다.\n"
            + "주사위에서는 여러 6면 주사위를 굴린 후, 주사위의 숫자를 더한 값이 특정 값 이상이면 상금을 받습니다.\n"
            + "경마에서는 한 경주마에게 베팅한 후 그 경주마가 우승하면 상금을 받습니다.");
  }

  private void nextText1() {
    howToPlay.setText(
        "For all of these games, the odds are stacked against your favor.\n"
            + "You will lose most of your games in the beginning.\n"
            + "But every time you lose, your luck will increase thanks to the blessings of Tyche.\n\n"
            + "As you lose more money, your odds of earning money through casino games will increase.\n"
            + "Once your luck is high enough, you can attempt higher difficulties to raise more profit.\n"
            + "Be careful, however - Running out of money will result in a game over!\n\n\n"
            + "카지노 게임들은 모두 플레이어한테 불리하게 설계되어 있습니다.\n"
            + "초반에는 대부분의 게임에서 돈을 잃게 될 것입니다.\n"
            + "하지만 게임에서 질 때마다, 티케의 가호로 인해 당신의 운이 상승하게 될 것입니다.\n\n"
            + "돈을 잃을 때마다 게임에서 상금을 탈 확률이 증가하게 됩니다.\n"
            + "당신의 운이 특정 값 이상 상승하게 되면, 더 어려운 카지노 게임에 참여해 더 큰 상금을 노려볼 수 있게 됩니다.\n"
            + "하지만 주의하십시오 - 돈을 전부 잃게 되면 게임 오버입니다!");
  }

  private void stall() {
    PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
    pause.setOnFinished(e -> button.setDisable(false));
    button.setDisable(true);
    pause.play();
  }

  @FXML
  private void next() {
    stall();
    nextText();
    button.setText("다음");
    button.setOnAction(
        e -> {
          try {
            next1();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });
  }

  private void next1() {
    stall();
    nextText1();
    button.setText("시작");
    button.setOnAction(
        e -> {
          try {
            start();
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });
  }

  @FXML
  private void start() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
    Parent root = loader.load();

    Stage stage = (Stage) title.getScene().getWindow();
    Scene currentScene = stage.getScene();

    currentScene.setRoot(root);
  }
}
