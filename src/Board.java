/**
 * Created by ZChen on 1/18/2018.
 * basic data type to play n puzzle game
 * each board represents a layout of the current
 * blocks within the board
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private int[][] board;
    private int n = 0;
    /**
     * construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     * @param blocks
     */
    public Board(int[][] blocks) {
        this.n = blocks.length;
        board = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }

    /**
     * return board dimension n
     * @return int
     */
    public int dimension() {
        return this.n;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        return 0;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        return false;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     * @return Board data type
     */
    public Board twin() {

    }


    private void exch(Board node) {
        int i = 0;
        int cap = 2;
        int n = dimension();
        while (i < cap) {
            int index = StdRandom.uniform(n);
            if (board[index/n][index%n] != 0) {

            }
        }
    }

    /**
     * does this board equal y?
     * @return boolean true represents equal false otherwise
     */
    public boolean equals(Object c) {
        return false;
    }

    /**
     * all neighboring boards
     * @return iterable of Board data type
     */
//    public Iterable<Board> neightbors() {
//
//    }

    /**
     * out put in ***
     *            ***
     *            ***
     * @return String object in the above format
     */
    public String toString() {
        int n = board.length;
        int m = board[0].length;
        String ans = "";
        for (int i = 0; i < n; i++) {
            String row = "";
            for (int j = 0; j < m; j++) {
                row += board[i][j] + " ";
            }
            ans += row.substring(0, row.length() - 1) + "\n";
        }
        return ans;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board board = new Board(blocks);
        StdOut.print(board);

    }

}
