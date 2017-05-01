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
        while (gb.cellLeft > 0) {
            System.out.println("Player " + curtPlayer + " Please input your row and col");
            int row = sc.nextInt();
            int col = sc.nextInt();

            // if this move is valid
            boolean moveSuccess = gb.makeMove(curtPlayer, row, col);
            if (!moveSuccess) {
                System.out.println("Your input is not valid, please input again!");
            } else {
                if (gb.checkWin(curtPlayer)) break;
                curtPlayer = curtPlayer == 'O' ? 'X' : 'O';
            }
            gb.displayBoard();
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
            System.out.println(curtPlayer + " has won!");
        }
    }

}