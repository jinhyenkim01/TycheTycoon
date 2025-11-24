package com.qv.tyche_tycoon;

public class Difficulty {
  private final GameType gameType;
  private final String difficultyName;
  private final int price;
  private int diceCount;
  private int diceMax;
  private int[] diceGoal;
  private int[] dicePayout;
  private int lotteryCount;
  private int lotteryPick;
  private int[] lotteryGoal;
  private int[] lotteryPayout;
  private int horseCount;
  private int horseLength;
  private int[] horseGoal;
  private int[] horsePayout;

  private Difficulty(GameType type, String name, int price) {
    this.gameType = type;
    this.difficultyName = name;
    this.price = price;
  }

  public static Difficulty forDice(
      String name, int price, int diceCount, int maxTries, int[] thresholds, int[] payout) {
    Difficulty d = new Difficulty(GameType.DICE, name, price);
    d.diceCount = diceCount;
    d.diceMax = maxTries;
    d.diceGoal = thresholds;
    d.dicePayout = payout;
    return d;
  }

  public static Difficulty forLottery(
      String name, int price, int totalNumbers, int pickCount, int[] multipliers, int[] payout) {
    Difficulty d = new Difficulty(GameType.LOTTERY, name, price);
    d.lotteryCount = totalNumbers;
    d.lotteryPick = pickCount;
    d.lotteryGoal = multipliers;
    d.lotteryPayout = payout;
    return d;
  }

  public static Difficulty forHorse(
      String name, int price, int horseCount, int trackLength, int[] multipliers, int[] payout) {
    Difficulty d = new Difficulty(GameType.HORSE, name, price);
    d.horseCount = horseCount;
    d.horseLength = trackLength;
    d.horseGoal = multipliers;
    d.horsePayout = payout;
    return d;
  }

  public GameType getGameType() {
    return gameType;
  }

  public String getDifficultyName() {
    return difficultyName;
  }

  public int getPrice() {
    return price;
  }

  public int getDiceCount() {
    return diceCount;
  }

  public int getDiceMax() {
    return diceMax;
  }

  public int[] getDiceThresholds() {
    return diceGoal;
  }

  public int[] getDicePayout() {
    return dicePayout;
  }

  public int getLotteryCount() {
    return lotteryCount;
  }

  public int getLotteryPick() {
    return lotteryPick;
  }

  public int[] getLotteryGoal() {
    return lotteryGoal;
  }

  public int[] getLotteryPayout() {
    return lotteryPayout;
  }

  public int getHorseCount() {
    return horseCount;
  }

  public int getHorseLength() {
    return horseLength;
  }

  public int[] getHorseGoal() {
    return horseGoal;
  }

  public int[] getHorsePayout() {
    return horsePayout;
  }

  public enum GameType {
    DICE,
    LOTTERY,
    HORSE
  }
}
