package tictactoe;

import java.util.Scanner;

/**
 * Created by Wei Shi on 5/1/17.
 */
public class TicTacToe {
    // Ai: "X", Player: "O"
    public static void main(String[] args) {
        boolean someoneWin = false;
        char curtPlayer;
        char winner;
        GameBoard gb = new GameBoard();
        gb.displayBoard();

        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to go first? Y/N");
        String first = sc.nextLine();
        if (first.equals("Y") || first.equals("y")) {
            curtPlayer = 'O';
        } else {
            curtPlayer = 'X';
        }
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
                    if (gb.checkWin(curtPlayer)) break;
                    curtPlayer = 'X';
                }
                gb.displayBoard();
                System.out.println();
            } else { // AI's turn
                int[] res = gb.ABSearch(gb.board);
                gb.board[res[0]][res[1]] = 'X';
                if (gb.checkWin(curtPlayer)) break;
                curtPlayer = 'O';
                gb.displayBoard();
            }

        }
        if (gb.cellLeft == 0) {
            if (gb.checkWin('X')) {
                System.out.println("Player X has won!");
            } else if (gb.checkWin('O')) {
                System.out.println("Player O has won!");
            } else {
                System.out.println("Draw!");
            }
        } else {
            gb.displayBoard();
            System.out.println(curtPlayer + " has won!");
        }
    }

}