package com.qv.tyche_tycoon;

public class PlayerStatistics {
  private static final int maxGamesPlayed = 50;
  private static long money = 50000;
  private static int totalGamesPlayed = 0;
  private static long moneySpent = 0;

  public static long getMoney() {
    return money;
  }

  public static int getMaxGamesPlayed() {
    return maxGamesPlayed;
  }

  public static long getMoneySpent() {
    return moneySpent;
  }

  public static int getTotalGamesPlayed() {
    return totalGamesPlayed;
  }

  public static void addMoney(int amount) {
    money += amount;
  }

  public static void subtractMoney(int amount) {
    money -= amount;
    incrementMoneySpent(amount);
  }

  public static void incrementGamesPlayed() {
    totalGamesPlayed++;
  }

  public static void incrementMoneySpent(int spent) {
    moneySpent = moneySpent + spent;
  }

  public static double calculateLuck(Difficulty settings) {
    if (settings.getGameType() == Difficulty.GameType.DICE) {
      return diceLuck(settings.getDifficultyName());
    }
    if (settings.getGameType() == Difficulty.GameType.LOTTERY) {
      return lotteryLuck(settings.getDifficultyName());
    }
    if (settings.getGameType() == Difficulty.GameType.HORSE) {
      return horseLuck(settings.getDifficultyName());
    }
    return 0.0;
  }

  public static double diceLuck(String difficulty) {
    long moneySpent = getMoneySpent();
    if (difficulty.equals("Easy")) {
      if (moneySpent >= ((long) 9 * GameStatistics.diceEasyCost)) {
        return 0.9;
      }
      return (moneySpent / (double) (10 * GameStatistics.diceEasyCost));
    }
    if (difficulty.equals("Normal")) {
      if (moneySpent >= ((long) 16 * GameStatistics.diceNormalCost)) {
        return 0.8;
      }
      return (moneySpent / (double) (20 * GameStatistics.diceNormalCost));
    }
    if (difficulty.equals("Hard")) {
      if (moneySpent >= ((long) 28 * GameStatistics.diceHardCost)) {
        return 0.7;
      }
      return (moneySpent / (double) (40 * GameStatistics.diceHardCost));
    }
    return 0.0;
  }

  public static double lotteryLuck(String difficulty) {
    long moneySpent = getMoneySpent();
    if (difficulty.equals("Easy")) {
      if (moneySpent >= ((long) 9 * GameStatistics.lotteryEasyCost)) {
        return 0.9;
      }
      return (moneySpent / (double) (10 * GameStatistics.lotteryEasyCost));
    }
    if (difficulty.equals("Normal")) {
      if (moneySpent >= ((long) 16 * GameStatistics.lotteryNormalCost)) {
        return 0.8;
      }
      return (moneySpent / (double) (20 * GameStatistics.lotteryNormalCost));
    }
    if (difficulty.equals("Hard")) {
      if (moneySpent >= ((long) 28 * GameStatistics.lotteryHardCost)) {
        return 0.7;
      }
      return (moneySpent / (double) (40 * GameStatistics.lotteryHardCost));
    }
    return 0.0;
  }

  public static double horseLuck(String difficulty) {
    long moneySpent = getMoneySpent();
    if (difficulty.equals("Easy")) {
      if (moneySpent >= ((long) 9 * GameStatistics.horseEasyCost)) {
        return 0.9;
      }
      return (moneySpent / (double) (10 * GameStatistics.horseEasyCost));
    }
    if (difficulty.equals("Normal")) {
      if (moneySpent >= ((long) 16 * GameStatistics.horseNormalCost)) {
        return 0.8;
      }
      return (moneySpent / (double) (20 * GameStatistics.horseNormalCost));
    }
    if (difficulty.equals("Hard")) {
      if (moneySpent >= ((long) 28 * GameStatistics.horseHardCost)) {
        return 0.7;
      }
      return (moneySpent / (double) (40 * GameStatistics.horseHardCost));
    }
    return 0.0;
  }
}
