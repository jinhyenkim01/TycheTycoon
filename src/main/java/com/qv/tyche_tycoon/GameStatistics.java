package com.qv.tyche_tycoon;

public class GameStatistics {

  static int lotteryEasyCost = 10000;
  static int lotteryEasyCount = 30;
  static int lotteryEasyPick = 5;
  static int[] lotteryEasyGoal = new int[] {2, 3, 4, 5};
  static int[] lotteryEasyPayout = new int[] {100, 200, 1500, 5000};
  static double[] lotteryEasyMult = new double[] {0.01, 0.02, 0.15, 0.5};

  static int lotteryNormalCost = 100000;
  static int lotteryNormalCount = 45;
  static int lotteryNormalPick = 6;
  static int[] lotteryNormalGoal = new int[] {3, 4, 5, 6};
  static int[] lotteryNormalPayout = new int[] {1000, 2000, 15000, 50000};
  static double[] lotteryNormalMult = new double[] {0.01, 0.02, 0.15, 0.5};

  static int lotteryHardCost = 1000000;
  static int lotteryHardCount = 60;
  static int lotteryHardPick = 7;
  static int[] lotteryHardGoal = new int[] {4, 5, 6, 7};
  static int[] lotteryHardPayout = new int[] {10000, 20000, 150000, 500000};
  static double[] lotteryHardMult = new double[] {0.01, 0.02, 0.15, 0.5};

  static int diceEasyCost = 1000;
  static int diceEasyCount = 5;
  static int diceEasyMax = 2;
  static int[] diceEasyGoal = new int[] {25, 28, 30};
  static int[] diceEasyPayout = new int[] {2000, 3000, 5000};
  static double[] diceEasyMult = new double[] {2.0, 3.0, 5.0};

  static int diceNormalCost = 10000;
  static int diceNormalCount = 10;
  static int diceNormalMax = 3;
  static int[] diceNormalGoal = new int[] {50, 56, 60};
  static int[] diceNormalPayout = new int[] {20000, 30000, 50000};
  static double[] diceNormalMult = new double[] {2.0, 3.0, 5.0};

  static int diceHardCost = 100000;
  static int diceHardCount = 15;
  static int diceHardMax = 4;
  static int[] diceHardGoal = new int[] {75, 84, 90};
  static int[] diceHardPayout = new int[] {200000, 300000, 500000};
  static double[] diceHardMult = new double[] {2.0, 3.0, 5.0};

  static int horseEasyCost = 1000;
  static int horseEasyCount = 6;
  static int horseEasyLength = 10;
  static int[] horseEasyGoal = new int[] {2, 1};
  static int[] horseEasyPayout = new int[] {1500, 3000};
  static double[] horseEasyMult = new double[] {1.5, 3.0};

  static int horseNormalCost = 10000;
  static int horseNormalCount = 10;
  static int horseNormalLength = 15;
  static int[] horseNormalGoal = new int[] {2, 1};
  static int[] horseNormalPayout = new int[] {15000, 30000};
  static double[] horseNormalMult = new double[] {1.5, 3.0};

  static int horseHardCost = 100000;
  static int horseHardCount = 16;
  static int horseHardLength = 20;
  static int[] horseHardGoal = new int[] {2, 1};
  static int[] horseHardPayout = new int[] {150000, 300000};
  static double[] horseHardMult = new double[] {1.5, 3.0};
}
