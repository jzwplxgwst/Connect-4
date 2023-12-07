/*----------------------------------------\
|              Connect 4.java             |
|           Programmer: James Wong        |
|  Date created: 01/22/2021 - 02/03/2021  | 
|             Course: ICS3U1-2A           | 
|                                         |
|-----------------------------------------*/

// Info: 
// Write a connect 4 program 


import java.util.*;
import java.lang.*;
import java.io.*;

public class Connect4
{
   // Row and Column variable
   final static int ROW = 6;
   final static int COLUMN = 7;
   
   public static void main (String[] args)
   {
      // scanner
      Scanner sc = new Scanner(System.in);
      
      // variables
      // integer variables
      int newOld;
      int wins = 0;
      int losses = 0; 
      int ties = 0;
      int totalGame = 0;
      int chosen = 0;
      int row = 0;
      int wOne = 0;
      int wTwo = 0; 
      int wThree = 0; 
      int wFour = 0;
      int playAgain = 0;
      int win = 0;
      
      // boolean variables
      boolean inputValid = false;
      boolean fillStop = false;
      boolean compFillStop = false;
      boolean printBoardValid = true;
      boolean repeat = true;
      
      // double variables
      double percWin = 0;
      double percLoss = 0;
      double percTie = 0;
      
      // String variables
      String name = "";
      
      // char variables
      char symbol = 'X';
      char compSymbol = 'O';
      char tieRule = 'n';
      
       // file variable 
      String nameFile = ".txt";
      
      // Array
      char[][] board = new char[ROW][COLUMN];
      
      // Instructions
      System.out.println("____________________________________");;
      System.out.println("[1] new player");
      System.out.println("[2] returning player");
      
      /*--------- 
      |  PART 1 |
      ---------*/
      
      // do while loop to loop the try catch statement
      do {
         // try statement for mismatch exception
         try { 
            System.out.print("Are you a new player or old player? ");   //ask the user if they are a new player or a returning player
            newOld = sc.nextInt();
            sc.nextLine();
            
            // if statement to load in player info
            switch (newOld) {
               case 1 :
                  System.out.print("What is your name: ");     // If they are a new player, it will ask them for their name.
                  name = sc.nextLine();
                  
                  nameFile = name + ".txt";                    // Your program will then create a text file with the file name in the format <PlayerName>.txt
                  
                  //BufferedWriter
                  BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile, false));  // If this file already exists, it should be overwritten
                  
                  for (int i = 0; i<3; i++) {
                     writer.write(""+0);           
                     writer.write("\r\n");
                  }
                  wins = 0;
                  losses = 0;
                  ties = 0;
                  
                  totalGame = wins + losses + ties;
                                    
                  // close writer
                  writer.close();
                  inputValid = true;
                  break;
               case 2 :   // If they are a returning player,, . , , .
                  System.out.print("What is your name returning player? ");      // it should ask for their name
                  name = sc.nextLine();
                  
                  nameFile = name + ".txt";        // and open their appropriate file
                     
                  // File Scanner
                  Scanner fs = new Scanner(new File(nameFile));
                  
                  wins = (int) fs.nextInt();       // The first line read should be their number of wins
                  losses = (int) fs.nextInt();     // the second should be their number of losses
                  ties = (int) fs.nextInt();       // and the third should be their number of ties
                  
                  totalGame = wins + losses + ties;
                  percWin = ((double) wins/totalGame)*100;
                  percLoss = ((double)losses/totalGame)*100;
                  percTie = ((double)ties/totalGame)*100;
                  
                  inputValid = true;
                  break;
            } 
             
            // catches incorrect data type
         } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println(e);
            System.out.println("try again"); 
            
            // catch non-existant file        
         } catch (FileNotFoundException f) {     
            System.out.println("file could not be opened");   // If the file cannot be opened your program should state that the file could not be opened
            System.out.println(f);
            
            // catches input output failures
         } catch (IOException o) {
            sc.nextLine();
            System.out.println(o);
         } catch ( java.lang.ArithmeticException p) {
            sc.nextLine();
            System.out.println(p);
         }
      } while (!inputValid);
      
      inputValid = false; // Reset boolean for next try catch statement
      
      
      
      /*--------
      | PART 2 |
      --------*/
      
      System.out.println("____________________________________");
      System.out.println("");
      System.out.println("!! SCOREBOARD !!");
      
      // your program should print out the user’s current record 
      if (totalGame == 0) {
         System.out.println(newWelcomeMessage(name, wins, losses, ties));
         
      } else  {
         System.out.println(returnWelcomeMessage(name, wins, losses, ties, percWin, percLoss, percTie));
      }
      
      // It will then begin a game of Connect Four
   
      /*--------- 
      |  PART 3 |                        
      ---------*/                      
      
      // EXTRA
      try {
         do {
            // instructions
            System.out.println("_________________________________");
            System.out.println("IF MULTIPLE SYMBOLS ARE INPUTTED");;
            System.out.println("ONLY THE FIRST SYMBOL WILL BE USED");
            System.out.print("Input the computer's variable: ");
            compSymbol = sc.next().charAt(0);
            System.out.print("Input your variable: ");
            symbol = sc.next().charAt(0);
            
            if (symbol == compSymbol) {
               inputValid = false;
               System.out.println("symbols cannot be the same");
            } else if (symbol == '+' || compSymbol == '+') {
               inputValid = false;
               System.out.println("symbols cannot be +");
            } else {
               inputValid = true;
            }
         } while (!inputValid) ;
         // catches any incorrect data types
      } catch (InputMismatchException e) {
         System.out.println(e);
      }
         
      do {
         
         inputValid = false;
      
         resetBoard(board);         // Call resetBoard method  
      
      
         while (printBoardValid) {       
         
            printboard(board);
         
            if (winConditionHorizontal(board, wOne, wTwo, wThree, wFour, symbol) || winConditionVertical(board, wOne, wTwo, wThree, wFour, symbol) || 
            winConditionLeftBottomDiagonal(board, wOne, wTwo, wThree, wFour, symbol) || winConditionRightBottomDiagonal(board, wOne, wTwo, wThree, wFour, symbol)) 
            {    // The player has four checkers in a row, column or diagonally
            
               sc.nextLine();
               System.out.println(" __________________________________");
               System.out.println("|                                  |");
               System.out.println("|--------------You Win-------------|");
               System.out.println("|__________________________________|");
               printBoardValid = false;
               win = 1;                   // Determines the winner/loser
            
            } else if (winCompHorizontal(board, wOne, wTwo, wThree, wFour, compSymbol) || winCompVertical(board, wOne, wTwo, wThree, wFour, compSymbol) ||
            winCompLeftBottomDiagonal(board, wOne, wTwo, wThree, wFour, compSymbol) ||  winCompRightBottomDiagonal(board, wOne, wTwo, wThree, wFour, compSymbol)) 
            {   // The computer has four checkers either in a row, a column or diagonally
            
               sc.nextLine();
               System.out.println(" __________________________________");
               System.out.println("|                                  |");
               System.out.println("|-------------You Lose-------------|");
               System.out.println("|__________________________________|");
               printBoardValid = false;
               win = 2;                   // Determines the winner/loser
            
            } else if (!tieCondition(board, tieRule)) {      // All positions on the game board are full
            
               sc.nextLine();
               System.out.println(" __________________________________");
               System.out.println("|                                  |");
               System.out.println("|----------------Tie---------------|");
               System.out.println("|__________________________________|");
               printBoardValid = false;
               win = 3;                   // Determines the winner/loser
            
            } else { 
            
               do {
                  try { 
                     do {
                        System.out.print("Chose a column: ");      // It should ask the player to choose a column (numbered 1 through 7) to drop their checker into
                        chosen = sc.nextInt();
                        chosen--;
                        
                        if (!fillBoard(chosen, board, symbol, compSymbol, fillStop, row)) {
                           chosen = 8;
                        }
                        
                     } while (chosen>7 || chosen<-1);
                     
                     // fillBoard(chosen, board, symbol, compSymbol, fillStop, row);
                   
                     computerTurn(board, symbol, compSymbol, compFillStop, row, wOne, wTwo, wThree, wFour);    // The computer will then take their turn.
                  
                     inputValid = true;
                  
                  } catch (InputMismatchException e) {
                     sc.nextLine();
                     System.out.println(e);
                     System.out.println("Please input a column number");
                  }
               } while (!inputValid);     // The game will continue in this fashion until either:
            }
         }
         
         inputValid = false;
         
         switch (win) {
            case 1 :    // the player wins, and their number of wins should be increased by one
               wins++;
               totalGame = wins + losses + ties;
               percWin = ((double) wins/totalGame)*100;
               percLoss = ((double)losses/totalGame)*100;
               percTie = ((double)ties/totalGame)*100;
               break;
            case 2 :    // the player loses, and their number of losses should be increased by one
               losses++;
               totalGame = wins + losses + ties;
               percLoss = ((double)losses/totalGame)*100;
               percWin = ((double) wins/totalGame)*100;
               percTie = ((double)ties/totalGame)*100;
               break;
            case 3 :    // it is a tie game, and the number of ties should be increased by one
               ties++;
               totalGame = wins + losses + ties;
               percTie = ((double)ties/totalGame)*100;
               percLoss = ((double)losses/totalGame)*100;
               percWin = ((double) wins/totalGame)*100;
               percTie = ((double)ties/totalGame)*100;
               break;
         }
      
         try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile, false));
         
         // Your program should then save this data to the appropriate file
            writer.write(""+wins);
            writer.write("\r\n");
            writer.write(""+losses);
            writer.write("\r\n");
            writer.write(""+ties);
            writer.write("\r\n");
            writer.close();
         
         // your program should then print the new number of wins, losses and ties for that player
         // along with the percentage of wins and losses in the same fashion that it was printed before the game started, 
         // but the updated numbers
            System.out.println("____________________________________");
            System.out.println("");
            System.out.println("!! SCOREBOARD !!");
            System.out.println(returnWelcomeMessage(name, wins, losses, ties, percWin, percLoss, percTie));
         
         } catch (IOException y) {
            System.out.println(y);
         }  
      // Your program should then ask the user if they would like to play again, or quit.
         
         while (!inputValid) {
            System.out.println("____________________________________");
            System.out.println("[1] play again");
            System.out.println("[2] quit");
            System.out.print("Do you wish to play again? ");
            playAgain = sc.nextInt();
            
            if (playAgain == 1) {
               inputValid = true;
               repeat = true;
            } else if (playAgain == 2) {
               inputValid = true;
               repeat = false;
            } else {
               System.out.println("Try again");
            }
         } 
         
         // reset variables for loop
         inputValid = false;
         printBoardValid =  true;
         
      } while (repeat) ; 
   }
   

/*====================================================================
|  Part 3                                                            |
|--------------------------------------------------------------------|
|  tieCondition(char[][] board, char tieRule)                        |
|--------------------------------------------------------------------|
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| char tieRule - This parameter holds each array value to check for +|
|--------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if the board is full   |
================================================================================*/ 

   public static boolean tieCondition(char[][] board, char tieRule)
   {
      boolean check = false;
   
      for (int row = 5; row>-1; row--) {
         for (int col = 6; col>-1; col--) {
            tieRule = board[row][col];
            
            if (tieRule == '+') {
               check = true;
            }
         }
      }
       
      return check;     
   } 
   
   
/*====================================================================
|  Part 3                                                            |
|------------------------------------------------------------------------------------------------------\
|winComprRightBottomDiagonal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char compSymbol|
|------------------------------------------------------------------------------------------------------/
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| int wOne - This parameter is holds the first variable              |
| int wTwo - This parameter is holds the second variable             |
| int wThree - This parameter is holds the third variable            |
| int wFour - This parameter is holds the fourth variable            |
| char compSymbol - this parameter is used to be compared to         |
|---------------------------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if it is a diagonal right bottom to top win|
===================================================================================================*/ 
   
   public static boolean winCompRightBottomDiagonal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char compSymbol)
   {
      boolean check = false;
   
      for (int row = 5; row>2; row--) {
         for (int col = 6; col>2; col--) {
            wOne = board[row][col];
            wTwo = board[row-1][col-1];
            wThree = board[row-2][col-2];
            wFour = board[row-3][col-3];
            
            if (compSymbol == wOne && wOne == wTwo && wTwo == wThree && wThree == wFour) {
               check = true;
            }
         }
      }
      return check;     
   } 
   
/*====================================================================
|  Part 3                                                            |
|----------------------------------------------------------------------------------------------------\
|winCompLeftBottomDiagonal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char compSymbol|
|----------------------------------------------------------------------------------------------------/
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| int wOne - This parameter is holds the first variable              |
| int wTwo - This parameter is holds the second variable             |
| int wThree - This parameter is holds the third variable            |
| int wFour - This parameter is holds the fourth variable            |
| char compSymbol - this parameter is used to be compared to         |
|--------------------------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if it is a diagonal left bottom to top win|
==================================================================================================*/ 
   
   public static boolean winCompLeftBottomDiagonal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char compSymbol)
   {
      boolean check = false;
   
      for (int row = 5; row>2; row--) {
         for (int col = 0; col<4; col++) {
            wOne = board[row][col];
            wTwo = board[row-1][col+1];
            wThree = board[row-2][col+2];
            wFour = board[row-3][col+3];
            
            if (compSymbol == wOne && wOne == wTwo && wTwo == wThree && wThree == wFour) {
               check = true;
            }
         }
      }
      return check;     
   } 
   
/*====================================================================
|  Part 3                                                            |
|-----------------------------------------------------------------------------------------\
|winCompVertical(char[][] board, int wOne, int wTwo, int wThree, int wFour, char compSymbol|
|-----------------------------------------------------------------------------------------/
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| int wOne - This parameter is holds the first variable              |
| int wTwo - This parameter is holds the second variable             |
| int wThree - This parameter is holds the third variable            |
| int wFour - This parameter is holds the fourth variable            |
| char compSymbol - this parameter is used to be compared to         |
|-------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if it is a vertical win|
================================================================================*/  
   
   public static boolean winCompVertical(char[][] board, int wOne, int wTwo, int wThree, int wFour, char compSymbol)
   {
      boolean check = false;
   
      for (int row = 0; row<3; row++) {
         for (int col = 0; col<7; col++) {
            wOne = board[row][col];
            wTwo = board[row+1][col];
            wThree = board[row+2][col];
            wFour = board[row+3][col];
            
            if (compSymbol == wOne && wOne == wTwo && wTwo == wThree && wThree == wFour) {
               check = true;
            }
         }
      }
      return check;     
   } 
   
/*====================================================================
|  Part 3                                                            |
|-----------------------------------------------------------------------------------\
|winCompHorizontal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char compSymbol|
|-----------------------------------------------------------------------------------/
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| int wOne - This parameter is holds the first variable              |
| int wTwo - This parameter is holds the second variable             |
| int wThree - This parameter is holds the third variable            |
| int wFour - This parameter is holds the fourth variable            |
| char symbol - this parameter is used to be compared to             |
|---------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if it is a horizontal win|
=================================================================================*/ 
   
   public static boolean winCompHorizontal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char compSymbol)
   {
      boolean check = false;
   
      for (int row = 0; row<6; row++) {
         for (int col = 0; col<4; col++) {
            wOne = board[row][col];
            wTwo = board[row][col+1];
            wThree = board[row][col+2];
            wFour = board[row][col+3];
            
            if (compSymbol == wOne && wOne == wTwo && wTwo == wThree && wThree == wFour) {
               check = true;
            }
         }
      }
      return check;     
   }
   
/*====================================================================
|  Part 3                                                            |
|------------------------------------------------------------------------------------------------------\
|winConditionRightBottomDiagonal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char symbol|
|------------------------------------------------------------------------------------------------------/
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| int wOne - This parameter is holds the first variable              |
| int wTwo - This parameter is holds the second variable             |
| int wThree - This parameter is holds the third variable            |
| int wFour - This parameter is holds the fourth variable            |
| char symbol - this parameter is used to be compared to             |
|---------------------------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if it is a diagonal right bottom to top win|
===================================================================================================*/ 
   
   public static boolean winConditionRightBottomDiagonal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char symbol)
   {
      boolean check = false;
   
      for (int row = 5; row>2; row--) {
         for (int col = 6; col>2; col--) {
            wOne = board[row][col];
            wTwo = board[row-1][col-1];
            wThree = board[row-2][col-2];
            wFour = board[row-3][col-3];
            
            if (symbol == wOne && wOne == wTwo && wTwo == wThree && wThree == wFour) {
               check = true;
            }
         }
      }
      return check;     
   } 
   
/*====================================================================
|  Part 3                                                            |
|----------------------------------------------------------------------------------------------------\
|winConditionLeftBottomDiagonal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char symbol|
|----------------------------------------------------------------------------------------------------/
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| int wOne - This parameter is holds the first variable              |
| int wTwo - This parameter is holds the second variable             |
| int wThree - This parameter is holds the third variable            |
| int wFour - This parameter is holds the fourth variable            |
| char symbol - this parameter is used to be compared to             |
|--------------------------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if it is a diagonal left bottom to top win|
==================================================================================================*/ 
   
   public static boolean winConditionLeftBottomDiagonal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char symbol)
   {
      boolean check = false;
   
      for (int row = 5; row>2; row--) {
         for (int col = 0; col<4; col++) {
            wOne = board[row][col];
            wTwo = board[row-1][col+1];
            wThree = board[row-2][col+2];
            wFour = board[row-3][col+3];
            
            if (symbol == wOne && wOne == wTwo && wTwo == wThree && wThree == wFour) {
               check = true;
            }
         }
      }
      return check;     
   } 
   
/*====================================================================
|  Part 3                                                            |
|-----------------------------------------------------------------------------------\
|winConditionVertical(char[][] board, int wOne, int wTwo, int wThree, int wFour, char symbol|
|-----------------------------------------------------------------------------------/
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| int wOne - This parameter is holds the first variable              |
| int wTwo - This parameter is holds the second variable             |
| int wThree - This parameter is holds the third variable            |
| int wFour - This parameter is holds the fourth variable            |
| char symbol - this parameter is used to be compared to             |
|-------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if it is a vertical win|
================================================================================*/  
   
   public static boolean winConditionVertical(char[][] board, int wOne, int wTwo, int wThree, int wFour, char symbol)
   {
      boolean check = false;
   
      for (int row = 0; row<3; row++) {
         for (int col = 0; col<7; col++) {
            wOne = board[row][col];
            wTwo = board[row+1][col];
            wThree = board[row+2][col];
            wFour = board[row+3][col];
            
            if (symbol == wOne && wOne == wTwo && wTwo == wThree && wThree == wFour) {
               check = true;
            }
         }
      }
      return check;     
   } 
   
/*====================================================================
|  Part 3                                                            |
|-----------------------------------------------------------------------------------\
|winConditionHorizontal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char symbol|
|-----------------------------------------------------------------------------------/
|  return check;                                                     |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
| int wOne - This parameter is holds the first variable              |
| int wTwo - This parameter is holds the second variable             |
| int wThree - This parameter is holds the third variable            |
| int wFour - This parameter is holds the fourth variable            |
| char symbol - this parameter is used to be compared to             |
|---------------------------------------------------------------------------------\
|  This method returns the boolean check as true or false if it is a horizontal win|
=================================================================================*/ 
   
   public static boolean winConditionHorizontal(char[][] board, int wOne, int wTwo, int wThree, int wFour, char symbol)
   {
      boolean check = false;
   
      for (int row = 0; row<6; row++) {
         for (int col = 0; col<4; col++) {
            wOne = board[row][col];
            wTwo = board[row][col+1];
            wThree = board[row][col+2];
            wFour = board[row][col+3];
            
            if (symbol == wOne && wOne == wTwo && wTwo == wThree && wThree == wFour) {
               check = true;
            }
         }
      }
      return check;     
   } 
 
/*====================================================================
|  Part 3                                                            |
|----------------------------------------------------------------------------------------\
|computerTurn(char[][] board, char symbol, char compSymbol, boolean compFillStop, int row)|
|----------------------------------------------------------------------------------------/
|  no return statement, instead fills array with a variable          |
|--------------------------------------------------------------------|
| char compSymbol - this parameter is the computer's symbol          |
| char[][] board - This parameter is an array for the connect 4 board|
| char symbol - parameter is the symbol the player selected           \
| boolean compFillStop - this parameter stops the program from running|
| int row - this parameter checks each row                            /
| int wOne - this parameter holds 1/4 slots in the array             |
| int wTwo - this parameter holds 1/4 slots in the array             |
| int wThree - this parameter holds 1/4 slots in the array           |
| int wFour - this parameter holds 1/4 slots in the array            |
|--------------------------------------------------------------------|
|  This method takes the board and fills it                          |
====================================================================*/
 
   public static void computerTurn(char[][] board, char symbol, char compSymbol, boolean compFillStop, int row, int wOne, int wTwo, int wThree, int wFour)
   {
      int compChoice;
      int doesWork = 0;    // used to check random spot generator will be used
      
      // Diagonal Check Right Bottom to Left Top
      for (int r = 5; r>2; r--) {
         for (int col = 6; col>2; col--) {
            wOne = board[r][col];
            wTwo = board[r-1][col-1];
            wThree = board[r-2][col-2];
            wFour = board[r-3][col-3];
            
            if (symbol == wOne && symbol == wTwo && symbol == wThree && '+' == wFour) {
               if (board[r-2][col-3] != '+') {
                  board[r-3][col-3] = compSymbol;
                  doesWork++;
                  r =2;
                  col =2;
               }
               
            } else if (symbol == wOne && symbol == wTwo && '+' == wThree && symbol == wFour) {
               if (board[r-1][col-2] != '+') {
                  board[r-2][col-2] = compSymbol;
                  doesWork++;
                  r =2;
                  col =2;
               }
               
            } else if (symbol == wOne && '+' == wTwo && symbol == wThree && symbol == wFour) {
               if (board[r][col-1] != '+') {
                  board[r-1][col-1] = compSymbol;
                  doesWork++;
                  r =2;
                  col =2;
               }
               
            } else if ('+' == wOne && symbol == wTwo && symbol == wThree && symbol == wFour) {
               if (r<5) {
                  if (board[r+1][col] != '+') {
                     board[r][col] = compSymbol;
                     doesWork++;
                     r =2;
                     col =2;
                  }
               } else {
                  board[r][col] = compSymbol;
                  doesWork++;
                  r =2;
                  col =2;
               }
            } else {
               doesWork += 0;
            }   
         }
      }
      
      // Diagonal Check Left Bottom to Right Top  
      if (doesWork == 0) {
         for (int r = 5; r>2; r--) {
            for (int col = 0; col<4; col++) {
               wOne = board[r][col];
               wTwo = board[r-1][col+1];
               wThree = board[r-2][col+2];
               wFour = board[r-3][col+3];
                     
               if (symbol == wOne && symbol== wTwo && symbol == wThree && '+' == wFour) {
                  if (board[r-2][col+3] != '+') {
                     board[r-3][col+3] = compSymbol;
                     doesWork++;
                     r = 2;
                     col = 4;
                  }
                        
               } else if (symbol == wOne && symbol == wTwo && '+' == wThree && symbol == wFour) {
                  if (board[r-1][col+2] != '+') {
                     board[r-2][col+2] = compSymbol;
                     doesWork++;
                     r = 2;
                     col = 4;
                  }
                        
               } else if (symbol == wOne && '+' == wTwo && symbol == wThree && symbol == wFour) {
                  if (board[r][col+1] != '+') {
                     board[r-1][col+1] = compSymbol;
                     doesWork++;
                     r = 2;
                     col = 4;
                  }
                        
               } else if ('+' == wOne && symbol == wTwo && symbol == wThree && symbol == wFour) {
                  
                  if (r<5) {
                     if (board[r+1][col] != '+') {
                        board[r][col] = compSymbol;
                        doesWork++;
                        r = 2;
                        col = 4;
                     }
                  } else {
                     board[r][col] = compSymbol;
                     doesWork++;
                     r = 2;
                     col = 4;
                  }
                                          
               } else { 
                  doesWork += 0;
               } 
            }
         }
      }  
      
      // Horizntal Check
      if (doesWork == 0) {
         for (int r = 0; r<6; r++) {
            for (int col = 0; col<4; col++) {
               wOne = board[r][col];
               wTwo = board[r][col+1];
               wThree = board[r][col+2];
               wFour = board[r][col+3];
            
               if (symbol == wOne && symbol== wTwo && symbol == wThree && '+' == wFour) {
                  if (r<5) {
                     if (board[r+1][col+3] != '+') {
                        board[r][col+3] = compSymbol;
                        doesWork++;
                        col = 4;
                        r = 6;
                     } 
                  } else {
                     board[r][col+3] = compSymbol;
                     doesWork++;
                     col = 4;
                     r = 6;
                  }
                  
               } else if (symbol == wOne && symbol == wTwo && '+' == wThree && symbol == wFour) {
                  if (r<5) {
                     if (board[r+1][col+2]!= '+') {
                        board[r][col+2] = compSymbol;
                        doesWork++;
                        col = 4;
                        r = 6;
                     }
                  } else {
                     board[r][col+2] = compSymbol;
                     doesWork++;
                     col = 4;
                        r = 6;
                  }
                   
               } else if (symbol == wOne && '+' == wTwo && symbol == wThree && symbol == wFour) {
                  if (r<5) {
                     if (board[r+1][col+1]!= '+') {
                        board[r][col+1] = compSymbol;
                        doesWork++;
                        col = 4;
                        r = 6;
                     }
                  } else {
                     board[r][col+1] = compSymbol;
                     doesWork++;
                     col = 4;
                     r = 6;
                  }
                  
               } else if ('+' == wOne && symbol == wTwo && symbol == wThree && symbol == wFour) {
                  if (r<5) {
                     if (board[r+1][col]!= '+') {
                        board[r][col] = compSymbol;
                        doesWork++;
                        col = 4;
                        r = 6;
                     }
                  } else {
                     board[r][col] = compSymbol;
                     doesWork++;
                     col = 4;
                     r = 6;
                  }
                  
               } else {
                  doesWork += 0;
               }
            }
         }
      } 
      
      // Vertical Check
      if (doesWork == 0) {
         for (int r = 0; r<3; r++) {
            for (int col = 0; col<7; col++) {
               wOne = board[r][col];
               wTwo = board[r+1][col];
               wThree = board[r+2][col];
               wFour = board[r+3][col];
            
               if ('+' == wOne && symbol == wTwo && symbol == wThree && symbol == wFour) {
                  doesWork++;
                  board[r][col] = compSymbol;
                     
               } else {
                  doesWork += 0;
               }
            
            }
         }
      }
         
      // If there is nothing to check
      if (doesWork == 0) {
         compChoice = (int) (Math.random()*6)+0;
         row = ROW;  
         do {
            row--;    
            if (board[0][compChoice] == symbol || board[0][compChoice] == compSymbol) {
               compChoice = (int) (Math.random()*6)+0;
               compFillStop = false;
               
            } else if (board[row][compChoice] == symbol || board[row][compChoice] == compSymbol) {
               compFillStop = false;
               
            } else {
               board[row][compChoice] = compSymbol;
               compFillStop = true;
            }
               
         } while (row > 0 && compFillStop == false) ;
      }
   }     
 
/*====================================================================
|  Part 3                                                            |
|--------------------------------------------------------------------|
|  void printboard(char[][] board)                                   |
|--------------------------------------------------------------------|
|  no return statement, instead fills array with a variable          |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
|--------------------------------------------------------------------|
|  This method takes the board and prints it                         |
====================================================================*/  
   public static void printboard(char[][] board) 
   {
      System.out.println(" _____________");
      System.out.println(" 1 2 3 4 5 6 7");
      for (int r = 0; r < ROW; r++) {        // When the game begins your program should print the game board on screen using characters to represent the board. 
         for (int c = 0; c < COLUMN; c++) {
            System.out.print(" "); 
            System.out.print(board[r][c]);
         }
         System.out.println("");
      }
      System.out.println(" _____________");
      System.out.println("");
   } 

/*====================================================================
|  Part 3                                                            |
|-----------------------------------------------------------------------------\
|fillBoard(int chosen, char[][] board, char symbol, boolean fillStop, int row)|
|-----------------------------------------------------------------------------/
|  no return statement, instead fills array with a variable          |
|--------------------------------------------------------------------|
| int chosen - this parameter is the column the player selected      |
| char[][] board - This parameter is an array for the connect 4 board|
| char symbol - parameter is the symbol the player selected          |
| boolean fillStop - this parameter stops the program from running   |
| int row - this parameter checks each row                           |
|--------------------------------------------------------------------|
|  This method takes the board and fills it                          |
====================================================================*/
   public static boolean fillBoard(int chosen, char[][] board, char symbol, char compSymbol, boolean fillStop, int row)
   {
      row = ROW; 
      boolean check = false;
       
      do {
         row--;  
         if (chosen > 6 || chosen < -1) {      
            fillStop = true;
            check = false;
         
         } else if (board[0][chosen] == symbol || board[0][chosen] == compSymbol) {
            fillStop = true;
            check = false;
            
         } else if (board[row][chosen] == symbol || board[row][chosen] == compSymbol) {
            fillStop = false;
            check = false;
         
         } else {
            board[row][chosen] = symbol;
            fillStop = true;
            check = true; 
         } 
         
      } while (row > 0 && fillStop == false) ;
      return check;
   } 

/*====================================================================
|  Part 2                                                            |
|--------------------------------------------------------------------|
|  void resetBoard(char[][] board)                                   |
|--------------------------------------------------------------------|
|  no return statement, instead fills array with a variable          |
|--------------------------------------------------------------------|
| char[][] board - This parameter is an array for the connect 4 board|
|--------------------------------------------------------------------|
|  This method takes the board and clears it                         |
====================================================================*/
   public static void resetBoard(char[][] board) 
   {
      for (int r = 0; r < ROW; r++) {  
         for (int c = 0; c < COLUMN; c++) {
            board[r][c] = '+';
         }
      }
   }
 
/*====================================================================
|  Part 1                                                            |
|--------------------------------------------------------------------|
| newWelcomeMessage(String name, int wins, int losses, int ties)     |
|--------------------------------------------------------------------|
| return message - the value of the output statement                 |
|--------------------------------------------------------------------|
| String name - this parameter is the name of the player             |
| int wins - this parameter is the number of wins of the player      |
| int losses - this parameter is the number of losses of the player  |
| int ties -this parameter is the number of ties of the player       |
|--------------------------------------------------------------------|
|  This method takes the given variables and output a welcome message|
====================================================================*/
  
   public static String newWelcomeMessage(String name, int wins, int losses, int ties)
   {
      // variables
      String message;
      
      //calculation
      message = "Welcome new player " + name + "!! \n # of wins: " + wins + " \n # of losses: " + losses + " \n # of ties: " + ties;
      
      //return message
      return message;
   }
   
/*====================================================================
|  Part 1                                                            |
|--------------------------------------------------------------------|
| returnWelcomeMessage(String name, int wins, int losses, int ties)  |
|--------------------------------------------------------------------|
| return message - the value of the output statement                 |
|--------------------------------------------------------------------|
| String name - this parameter is the name of the player             |
| int wins - this parameter is the number of wins of the player      |
| int losses - this parameter is the number of losses of the player  |
| int ties -this parameter is the number of ties of the player       |  
| double percWin - this parameter is the percentage of wins          |
| double percLoss - this parameter is the percentage of losses       |
|--------------------------------------------------------------------|
|  This method takes the given variables and output a welcome message|
====================================================================*/
   
   public static String returnWelcomeMessage(String name, int wins, int losses, int ties, double percWin, double percLoss, double percTie)
   {
      // variables
      String message;
      int shortenWin = (int) Math.round(percWin);
      int shortenLoss = (int) Math.round(percLoss);
      int shortenTie = (int) Math.round(percTie);
      
      //calculation
      message = "Welcome returning player " + name + "!! \n # of wins: " + wins + " \n # of losses: " + losses + " \n # of ties: " + ties + " \n Percentage of wins: " + shortenWin + "% \n Percentage of losses: " + shortenLoss + "% \n Percentage of ties: " + shortenTie;
      
      //return message
      return message;
   }
}