package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Wei Shi on 5/1/17.
 */
public class GameBoard {
    char[][] board;
    private static int SIZE = 4;
    int cellLeft = SIZE * SIZE;
    static char PLAYER_MARK = 'O';
    static char AI_MARK= 'X';
    /**
     * Some variables to record information
     */
    private boolean cutoff;
    private int maxDepth, totalNodes, maxCount , minCount;
    private long startTime;
    private static long TIME_INTERVAL = 10000000000l;

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



    public List<int[]> getAvailableCell() {
        List<int[]> result = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == '~') {
                    result.add(new int[]{row, col});
                }
            }
        }
        return result;
    }

    public int[] ABSearch() {
        cutoff = false;
        totalNodes = 1;
        maxCount = 0;
        minCount = 0;
        maxDepth = 0;
        startTime = System.nanoTime();
        int value = Integer.MIN_VALUE;
        int[] move = new int[2];
        List<int[]> availableCell = getAvailableCell();
        Collections.shuffle(availableCell);
        for (int[] point : availableCell) {
            if (board[point[0]][point[1]] == '~') {
                board[point[0]][point[1]] = 'X';
                int result = Min_Value(-1000, 1000, 1);
                board[point[0]][point[1]] = '~';
                if (result > value) {
                    value = result;
                    move = point;
                }
            }
        }
        if (cutoff) {
            System.out.println("Cutoff occured!");
        }
        System.out.println("Generated " + totalNodes + " nodes.");
        System.out.println("Max_Value function called " + maxCount + " times.");
        System.out.println("Min_Value function called " + minCount + " times.");
        System.out.println("Max depth is " + maxDepth);
        return move;
    }

    public int Max_Value(int a, int b, int depth) {
        if (cutoff) {
            return eval();
        }
        totalNodes++;
        maxCount++;
        maxDepth = Math.max(depth, maxDepth);
        if (!cutoff && (System.nanoTime() - startTime >= TIME_INTERVAL)) { // cutoff
            cutoff = true;
        }
        if (checkWin(PLAYER_MARK)) { // terminal state: Player won!
            return -1000;
        }
        List<int[]> availableCell = getAvailableCell();
        if (availableCell.size() == 0) { // terminal state: Draw!
            return 0;
        }
        Collections.shuffle(availableCell);
        int value = Integer.MIN_VALUE;
        for (int[] point : availableCell) {
            if (board[point[0]][point[1]] == '~') {
                board[point[0]][point[1]] = AI_MARK;
                value = Math.max(value, Min_Value(a, b, depth + 1));
                board[point[0]][point[1]] = '~';

                if (value >= b) {
                    return value;
                }
                a = Math.max(a, value);
            }
        }
        return value;
    }

    public int Min_Value(int a, int b, int depth) {
        if (cutoff) {
            int res = eval();
            return res;
        }
        totalNodes++;
        minCount++;
        maxDepth = Math.max(depth, maxDepth);

        if (!cutoff && System.nanoTime() - startTime >= TIME_INTERVAL) { // cutoff
            cutoff = true;
        }
        if (checkWin(AI_MARK)) { // terminal state: AI won!
            return 1000;
        }
        List<int[]> availableCell = getAvailableCell();
        if (availableCell.size() == 0) { // terminal state: Draw!
            return 0;
        }
        Collections.shuffle(availableCell);
        int value = Integer.MAX_VALUE;
        for (int[] point : availableCell) {
            if (board[point[0]][point[1]] == '~') {
                board[point[0]][point[1]] = PLAYER_MARK;
                value = Math.min(value, Max_Value(a, b, depth + 1));
                board[point[0]][point[1]] = '~';

                if (value <= a) {
                    return value;
                }
                b = Math.min(b, value);
            }
        }
        return value;
    }

    public int eval() {
        int[] x = new int[SIZE];
        int[] o = new int[SIZE];
        // check row
        for (int row = 0; row < SIZE; row++) {
            int xNum = 0, oNum = 0;
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == AI_MARK) {
                    xNum++;
                } else if (board[row][col] == PLAYER_MARK) {
                    oNum++;
                }
            }
            if (oNum == 0 && xNum == 0) continue;
            if (oNum == 0) {
                x[xNum - 1]++;
            }
            if (xNum == 0) {
                o[oNum - 1]++;
            }
        }

        // check col
        for (int col = 0; col < SIZE; col++) {
            int xNum = 0, oNum = 0;
            for (int row = 0; row < SIZE; row++) {
                if (board[row][col] == AI_MARK) {
                    xNum++;
                } else if (board[row][col] == PLAYER_MARK) {
                    oNum++;
                }
            }
            if (oNum == 0 && xNum == 0) continue;
            if (oNum == 0) {
                x[xNum - 1]++;
            }
            if (xNum == 0) {
                o[oNum - 1]++;
            }
        }

        // check diagnoal
        for (int i = 0; i < SIZE; i++) {
            int xNum = 0, oNum = 0;
            if (board[i][i] == AI_MARK) {
                xNum++;
            } else if (board[i][i] == PLAYER_MARK) {
                oNum++;
            }
            if (oNum == 0 && xNum == 0) continue;
            if (oNum == 0) {
                x[xNum - 1]++;
            }
            if (xNum == 0) {
                o[oNum - 1]++;
            }
        }

        for (int i = 0; i < SIZE; i++) {
            int xNum = 0, oNum = 0;
            if (board[i][SIZE - i - 1] == AI_MARK) {
                xNum++;
            } else if (board[i][SIZE - i - 1] == PLAYER_MARK) {
                oNum++;
            }
            if (oNum == 0 && xNum == 0) continue;
            if (oNum == 0) {
                x[xNum - 1]++;
            }
            if (xNum == 0) {
                o[oNum - 1]++;
            }
        }
        return 6 * x[2] + 3 * x[1] + x[0] - (6 * o[2] + 3 * o[1] + o[0]);
    }
}

