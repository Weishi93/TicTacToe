package tictactoe;

import java.util.Scanner;

/**
 * Created by Wei Shi on 5/1/17.
 */
public class TicTacToe {
    // Ai: "X", Player: "O"
    public static void main(String[] args) {
        char curtPlayer;
        GameBoard gb = new GameBoard();
        gb.displayBoard();
        int difficulty = 1;
        String[] mode = new String[]{"Easy", "Medium", "Hard"};
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select difficulty: 1: Easy, 2: Medium, 3: Hard");
        difficulty = Integer.valueOf(sc.nextLine());
        if (difficulty < 1 || difficulty > 3) {
            throw new IllegalArgumentException("Difficulty is between 1 - 3");
        }
        System.out.println("You selected " + mode[difficulty - 1] + " mode.\n");
        System.out.println("Do you want to go first? Y/N");
        String first = sc.nextLine();
        System.out.println();
        if (first.equals("Y") || first.equals("y")) {
            curtPlayer = 'O';
        } else {
            curtPlayer = 'X';
        }
        int allowedDepth = difficulty == 1 ? 0 : (difficulty == 2 ? 4 : gb.getSize() * gb.getSize());

        // take turns to play game
        while (gb.cellLeft > 0) {
            if (curtPlayer == 'O') { // player's turn
                System.out.println("Player " + curtPlayer + " Please input your row and col");
                int row = sc.nextInt();
                int col = sc.nextInt();

                // if this move is valid
                boolean moveSuccess = gb.makeMove(curtPlayer, row, col);
                if (!moveSuccess) {
                    System.out.println("Your input is not valid, please input again!");
                } else {
                    if (gb.checkWin(curtPlayer)) {
                        gb.displayBoard();
                        break;
                    }
                    curtPlayer = 'X';
                }
            } else { // AI's turn
                int[] res = gb.ABSearch(allowedDepth);
                gb.makeMove('X', res[0], res[1]);
                System.out.println("AI made move on " + res[0] + " " + res[1]);
                if (gb.checkWin(curtPlayer)) {
                    gb.displayBoard();
                    break;
                }
                curtPlayer = 'O';
            }
            gb.displayBoard();
            System.out.println();
        }
        if (gb.cellLeft == 0) {
            if (gb.checkWin(gb.AI_MARK)) {
                System.out.println("X has won!");
            } else if (gb.checkWin(gb.PLAYER_MARK)) {
                System.out.println("O has won!");
            } else {
                System.out.println("Draw!");
            }
        } else {
            gb.displayBoard();
            System.out.println(curtPlayer + " has won!");
        }
    }

}