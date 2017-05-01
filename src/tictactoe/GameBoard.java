package tictactoe;

import java.util.Arrays;

/**
 * Created by Wei Shi on 5/1/17.
 */
public class GameBoard {
    private char[][] board;
    private static int SIZE = 4;
    protected int cellLeft = SIZE * SIZE;
    public GameBoard() {
        board = new char[SIZE][SIZE];

        // intialize board
        for (int row = 0; row < SIZE; row++) {
            Arrays.fill(board[row], '~');
        }
    }

    public void displayBoard() {
        for (int row = 0; row < SIZE; row++) {
            System.out.println(Arrays.toString(board[row]));
        }
    }

    public boolean makeMove(char player, int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            if (board[row][col] == '~') {
                board[row][col] = player;
                cellLeft--;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkWin(char player) {
        // check row
        boolean win = false;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != player) break;
                if (col == SIZE - 1 && board[row][col] == player) {
                    win = true;
                }
            }
        }

        // check col
        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++) {
                if (board[row][col] != player) break;
                if (row == SIZE - 1 && board[row][col] == player) {
                    win = true;
                }
            }
        }

        // check diagnoal
        for (int row = 0; row < SIZE; row++) {
            if (board[row][row] != player) break;
            if (row == SIZE - 1 && board[row][row] == player) {
                win = true;
            }
        }
        for (int row = 0; row < SIZE; row++) {
            if (board[row][SIZE - row - 1] != player) break;
            if (row == SIZE - 1 && board[row][SIZE - row - 1] == player) {
                win = true;
            }
        }
        return win;
    }
}
