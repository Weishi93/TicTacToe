package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wei Shi on 5/1/17.
 */
public class GameBoard {
    protected char[][] board;
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

    public List<int[]> getAvailableCell(char[][] newBoard) {
        List<int[]> result = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (newBoard[row][col] == '~') {
                    result.add(new int[]{row, col});
                }
            }
        }
        return result;
    }

    public int[] ABSearch(char[][] board) {
        ReturnType result = Max_Value(board, 1000, -1000, 0);
        return result.point;
    }

    public ReturnType Max_Value(char[][] board, int a, int b, int depth) {
        if (checkWin('X')) {
            return new ReturnType(null, 1000);
        }
        if (checkWin('O')) {
            return new ReturnType(null, -1000);
        }
        List<int[]> avaliableCell = getAvailableCell(board);
        if (avaliableCell.isEmpty()) {
            return new ReturnType(null, 0);
        }
        int value = Integer.MIN_VALUE;
        for (int[] point : avaliableCell) {
            char[][] newBoard = getNewBoard(board, point, 'X');
            value = Math.max(value, Min_Value(newBoard, a, b, depth + 1).value);
            if (value >= b) {
                return new ReturnType(point, value);
            }
            a = Math.max(a, value);
        }
        return new ReturnType(null, value);
    }

    public ReturnType Min_Value(char[][] board, int a, int b, int depth) {
        if (checkWin('X')) {
            return new ReturnType(null, 1000);
        }
        if (checkWin('O')) {
            return new ReturnType(null, -1000);
        }
        List<int[]> avaliableCell = getAvailableCell(board);
        if (avaliableCell.isEmpty()) {
            return new ReturnType(null, 0);
        }
        int value = Integer.MAX_VALUE;
        for (int[] point : avaliableCell) {
            char[][] newBoard = getNewBoard(board, point, 'O');
            value = Math.min(value, Max_Value(newBoard, a, b, depth + 1).value);
            if (value >= a) {
                return new ReturnType(point, value);
            }
            a = Math.max(a, value);
        }
        return new ReturnType(null, value);
    }

    public char[][] getNewBoard(char[][] board, int[] point, char player) {
        char[][] newBoard = new char[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                newBoard[row][col] = board[row][col];
            }
        }
        newBoard[point[0]][point[1]] = player;
        return newBoard;
    }
}

class ReturnType {
    int[] point;
    int value;
    public ReturnType(int[] point, int value) {
        this.point = point;
        this.value = value;
    }
}
