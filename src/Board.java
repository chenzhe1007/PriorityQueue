import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ZChen on 1/18/2018.
 * basic data type to play n puzzle game
 * each board represents a layout of the current
 * blocks within the board
 * This is an immutable class
 */



public class Board {

    private class NeighborIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {  return new NeighborIterator();  }
        private class NeighborIterator implements Iterator<Board> {
            private final int[][] iterBoard;
            private final int total;
            private final int[] dx;
            private final int[] dy;
            private int current = 0;
            public NeighborIterator() {
                iterBoard = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        iterBoard[i][j] = board[i][j];
                    }
                }
                int x = spaceIndex / n;
                int y = spaceIndex % n;
                if ((x == 0 && y == 0) || (x == n - 1 && y == n - 1) || (x == 0 && y == n - 1) || (x == n - 1 && y == 0)) {
                    dx = new int[2];
                    dy = new int[2];
                } else if (x == 0 || y == 0 || x == n - 1 || y == n - 1) {
                    dx = new int[3];
                    dy = new int[3];
                } else {
                    dx = new int[4];
                    dy = new int[4];
                }
                total = dx.length;
                setNeighborIndex();
            }

            private void setNeighborIndex() {
                final int[] allDx = {1, -1, 0, 0};
                final int[] allDy = {0, 0, 1, -1};
                int blankX = spaceIndex / n;
                int blankY = spaceIndex % n;
                int count = 0;
                int index = 0;
                for (int i = 0; i < allDx.length; i++) {
                    if (blankX + allDx[i] >= 0 && blankY + allDy[i] >= 0 && blankX + allDx[i] < n && blankY + allDy[i] < n) {
                        dx[index] = allDx[i];
                        dy[index] = allDy[i];
                        index++;
                    }
                }
            }

            public boolean hasNext() {
                return current < total;
            }

            public Board next() {
                if (!hasNext()) { throw new NoSuchElementException(); }
                int x = (spaceIndex/n) + dx[current];
                int y = (spaceIndex%n) + dy[current];
                StdOut.println(dx[current] + " : " + dy[current]);
                StdOut.println(x + " : " + y);
                iterBoard[spaceIndex/n][spaceIndex%n] = iterBoard[x][y];
                iterBoard[x][y] = 0;
                Board res = new Board(iterBoard);
                iterBoard[x][y] = iterBoard[spaceIndex/n][spaceIndex%n];
                iterBoard[spaceIndex/n][spaceIndex%n] = 0;
                current++;
                return res;
            }

        }



    }
    private final int[][] board;
    private final int n;
    private int spaceIndex;
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
                if (blocks[i][j] == 0) {
                    spaceIndex = i * n + j;
                }
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
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = board[i][j];
                if (value == 0) {
                    continue;
                }
                int goalX = (value - 1) / n;
                int goalY = (value - 1) % n;
                sum += ((goalX - i) >= 0 ? (goalX - i) : (i - goalX)) + ((goalY - j) >= 0 ? (goalY - j) : (j - goalY));
            }
        }
        return sum;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        int prev = board[0][0];
        for (int i = 1; i < n - 1; i++) {

            if (board[i/n][i%n] < prev || board[i/n][i%n] == 0) { return false; }
        }
        return true;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     * @return Board data type
     */
    public Board twin() {
        int first = getBlockIndex();
        int second = getBlockIndex();
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = board[i][j];
            }
        }
        while (second == first) {
            second = getBlockIndex();
        }
        int tmp = copy[first/n][first%n];
        copy[first/n][first%n] = copy[second/n][second%n];
        copy[second/n][second%n] = tmp;
        return new Board(copy);
    }


    private int getBlockIndex() {
        int ans;
        while (true) {
            ans = StdRandom.uniform(n);
            if (board[ans/n][ans%n] != 0) break;
        }
        return ans;
    } // get a random non empty block index



    /**
     * does this board equal y?
     * @return boolean true represents equal false otherwise
     */
    public boolean equals(Object c) {
        if (c == this) return true;
        if (c == null) return false;
        if (c.getClass() != this.getClass()) return false;
        Board item = (Board) c;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != item.board[i][j]) return false;
            }
        }
        return true;
    }

    /**
     * all neighboring boards
     * @return iterable of Board data type
     */
    public Iterable<Board> neightbors() {
         return new NeighborIterable();
    }

    /**
     * out put in ***
     *            ***
     *            ***
     * @return String object in the above format
     */
    public String toString() {
        int n = board.length;
        int m = board[0].length;
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(String.format("%2d ", board[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
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
        Board board1 = board.twin();
        StdOut.println(board);

//        for (Board board2 : board.neightbors()) {
//            StdOut.print(board2);
//        }
        StdOut.println(board.manhattan());



    }

}
