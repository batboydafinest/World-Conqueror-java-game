//Aadi Padmawar

import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class Project {

  //differnt stages  19 total: 18 regular, 1 final stage
  static String[] stages = {"Australia", "Japan", "South Korea", "China", "India", "Afghanistan", "Russia", "Turkey", "Germany", "United Kingdom", "Egypt", "Madagascar", "Argentina", "Brazil", "Costa Rica", "Mexico", "United States", "Canada", "Antartica" };
  //Basic stats for boss (atk, hp, wpn, armor)
  static int[] bossStats = {50, 100, 1, 1};
  //atk, hp, wpn, armor, money, stage(index), fireballs, exit(0 = run, 1 = exit)
  static int[] stats = {50, 100, 1, 1, 6, 0, 0, 0};
  //restore stats after battle
  static int maxHealth = 100;

  public static void main(String[] args) throws Exception {

    Scanner input = new Scanner(System.in);

    //for do-while loop on line 29
    boolean run = true;

    System.out.println("Welcome to World Conqueror\n");

    do {

      //asks if returning player or not
      System.out.println("Select Game Type: ");
      System.out.println("-New");
      System.out.println("-Returning");
      System.out.print("> ");

      //puts answer in temp and makes it all uppercase
      String temp;
      temp = input.next();
      temp = temp.toUpperCase();

      //if new, skips to gameplay
      if (temp.equals("NEW")) {
        run = false;
      }

      //if returning, asks for file where stats are stored
      else if (temp.equals("RETURNING")) {

        String file;

        System.out.print("Which file is the data stored in? \n>");
        file = input.next();

        System.out.print("Reading files from \"" + file + "\".....");

        Scanner fileIn = new Scanner(new File(file));

        //stores stats from file in global arrays
        stats[0] = fileIn.nextInt();
        stats[1] = fileIn.nextInt();
        stats[2] = fileIn.nextInt();
        stats[3] = fileIn.nextInt();
        stats[4] = fileIn.nextInt();
        stats[5] = fileIn.nextInt();
        stats[6] = fileIn.nextInt();

        //used to restore values after battle
        maxHealth = stats[1];

        fileIn.close();

        System.out.println("done");

        run = false;

      }

      //catches invalid answers
      else {
        System.out.println("Pick a valid answer!");
      }

    } while (run); //runs until a valid answer is passed

    //runs prestage until they win or they exit
    while (stats[5] <= 18 && stats[7] == 0) {
      prestage();
    }

    //if they win the game is asks if they want to save for the last time
    if (stats[5] == 19) {
      finalMessage();
    }

  }

  //message that pops up when they win. It asks if they want to save their data
  public static void finalMessage() {
    Scanner input = new Scanner(System.in);

    System.out.println("You won the Game!!!");
    System.out.println("Would you like to save the game? (yes or no)");
    System.out.print(">");
    String finalans = input.next();
    finalans = finalans.toUpperCase();
    if (finalans.equals("YES")) {
      save();
    }
    else if (finalans.equals("NO")) {

    }
    else {
      System.out.println("*****ERROR: Invalid input*********");
      finalMessage();
    }
  }

  //this is the main room that the player is in. it redirects them to where they want to go
  public static void prestage() {

    Scanner input = new Scanner(System.in);

    System.out.println("You are in " + stages[stats[5]] + " (Stage " + (stats[5] + 1) + ")");
    System.out.println("\nWhat would you like to do?");
    System.out.println("-Shop");
    System.out.println("-Fight");
    System.out.println("-Save");
    System.out.print("-Exit\n>");
    String ans = input.next();
    ans = ans.toUpperCase();
    if (ans.equals("SHOP")) {
      shop();
    }
    else if(ans.equals("FIGHT")) {
      fight();
    }

    else if(ans.equals("SAVE")) {
      save();
      stats[7] = 1;
    }

    else if(ans.equals("EXIT")) {
      stats[7] = 1;
    }
    else {
      prestage();
    }
  }

  //This is the shop, if the player has enough money, it will add the item to their stats
  public static void shop() {

    Scanner input = new Scanner(System.in);

    System.out.println("\n+--------------------------------------------+");
    System.out.println("|            Welcome to the Shop             |");
    System.out.println("|                                            |");
    System.out.println("|       What would you like to buy?          |");
    System.out.println("|                                            |");
    System.out.println("| 1. More Muscles...................5 coins  |");
    System.out.println("| 2. More Health....................5 coins  |");
    System.out.println("| 3. Sharper Sword..................20 coins |");
    System.out.println("| 4. Better Armor...................20 coins |");
    System.out.println("| 5. Fireball.......................10 coins |");
    System.out.println("| 6. Exit                                    |");
    System.out.println("|                                            |");
    System.out.println("| You currently have " + stats[4] + " coins                |");
    System.out.println("|                                            |");
    System.out.println("+--------------------------------------------+\n");

    System.out.print("What number do you chose?\n>");
    int ans = input.nextInt();

    if (ans == 1 && stats[4] >= 5) {
      stats[0] += 5;
      stats[4] -= 5;
      shop();
    }
    else if (ans == 2 && stats[4] >= 5) {
      stats[1] += 5;
      stats[4] -= 5;
      maxHealth = stats[1];
      shop();
    }
    else if (ans == 3 && stats[4] >= 20) {
      stats[2] += 1;
      stats[4] -= 20;
      shop();
    }
    else if (ans == 4 && stats[4] >= 20) {
      stats[3] += 1;
      stats[4] -= 20;
      shop();
    }
    else if (ans == 5 && stats[4] >= 10) {
      stats[6] += 1;
      stats[4] -= 10;
      shop();
    }
    else if (ans == 6) {

    }
    else {
      System.out.println("****Invalid choice or not enough money!****\n");
    }
  }

  //fight sequences
  public static void fight() {

    //sets boss stats, the boss gets harder as their stage gets higher
    bossStats[0] = 50 + (2 * stats[5]);
    bossStats[1] = 100 + (5 * stats[5]);
    bossStats[2] = (int)(1 + (.3 * stats[5]));
    bossStats[3] = (int)(1 + (.2 * stats[5]));

    //final boss has higher stats
    if (stats[5] == 18) {
      bossStats[0] = 200;
      bossStats[1] = 500;
      bossStats[2] = 4;
      bossStats[3] = 5;
    }

    Scanner input = new Scanner(System.in);

    System.out.println("");
    System.out.println("Battle sequence!!!!");

    while (bossStats[1] > 0 && stats[1] > 0) {

      System.out.println("");
      System.out.println("Boss HP: " + bossStats[1]);
      System.out.println("");
      System.out.println("Your HP: " + stats[1]);
      System.out.println("");
      System.out.println("What do you do? ");
      System.out.println("-Attack");
      //only appears if they have fireballs
      if (stats[6] > 0) {
        System.out.println("-Fireball");
      }

      System.out.print(">");
      String ans = input.next();

      ans = ans.toUpperCase();

      if (ans.equals("ATTACK") || (ans.equals("FIREBALL") && stats[6] > 0)) {
        if (ans.equals("ATTACK")) {
          enemyBattleDamage();
        }
        else if (ans.equals("FIREBALL") && stats[6] > 0) {
          enemyBattleDamageFireball();
        }

        if (bossStats[1] > 0) {
          playerBattleDamage();
        }

      }

      else {
        System.out.println("*****ERROR: Invalid input*********");
      }

    }

    if (stats[1] < 1) {
      System.out.println("\nYou have Lost!!! Try again!");
    }

    else if (bossStats[1] < 1) {
      System.out.println("\nYou beat the Boss!!!!");
      stats[4] += 2; //gives money for winning
      stats[5] += 1;
    }

    //restores stats to max
    stats[1] = maxHealth;
    stats[4] += 3;

    if(stats[5] == 18) {
      stats[4] += 47;
    }

  }

  //calculates damage taken by the enemy
  public static void enemyBattleDamage() {
    int atkdmg = ((stats[0] * stats[2])/bossStats[3]);
    bossStats[1] -= atkdmg;
  }

  //calculates damage taken by the enemy when fireball is used
  public static void enemyBattleDamageFireball() {
    stats[6] -= 1;
    int atkdmg = ((stats[0] * stats[2])/bossStats[3]) + 25;
    bossStats[1] -= atkdmg;
  }

  //calculates damage taken by the player
  public static void playerBattleDamage() {
    int atkdmg = ((bossStats[0] * bossStats[2])/stats[3]);
    stats[1] -= atkdmg;
  }

  //meathod to save their data to different file
  public static void save() {

    PrintWriter fileOut;
    Scanner input = new Scanner(System.in);

    System.out.print("Where should I save the data? \n>");
    String filename = input.next();

    try{
        fileOut = new PrintWriter(filename);

    } catch(IOException e) {
        System.out.println("Sorry, I can't open the file '" + filename + "' for editing.");
        System.out.println("Maybe the file exists and is read-only?");
        fileOut = null;
        System.exit(1);
    }

    fileOut.println( stats[0] + " " + stats[1] + " " + stats[2] + " " + stats[3] + " " + stats[4] + " " + stats[5] + " " + stats[6]);
    fileOut.close();

  }

}
