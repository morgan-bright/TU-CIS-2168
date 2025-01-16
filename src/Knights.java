import java.lang.Math;
import java.util.Arrays;

public class Knights {
    public static int numTurns = 0;
    public static int[] rowMoves = { 2, 1, -1, -2, -2, -1, 1, 2 };
    public static int[] colMoves = { 1, 2, 2, 1, -1, -2, -2, -1 };
    public static int[][] board = new int[8][8];


    public static boolean solve(int[][] board, int row, int col, int moveNumber) {
        double size = Math.pow(board.length, 2);
       // board[row][col] = moveNumber;

        if (moveNumber == size) {
            System.out.println();
            board[row][col] = moveNumber;
            for (int j = 0; j < board.length; j++) {
               System.out.println(Arrays.toString(board[j]));
            }
            return true;
        }

        for (int i = 0; i < 8; i++) {
            int[] newMove = chooseBestMove(board, row, col, i);
            int newRow = newMove[0];
            int newCol = newMove[1];

            if (valid(board, newRow, newCol)) {
                board[row][col] = moveNumber;
                numTurns++;
                //System.out.println(moveNumber);

                if (solve(board, newRow, newCol, moveNumber+1)) {
                    return true;
                }else{
                    board[newRow][newCol] = 0;
                    //System.out.println();
                }
            }
        }
        return false;
    }

        public static boolean valid ( int[][] board, int row, int col){
            int size = board.length;

            if (row < 0 || col < 0 || row >= size || col >= size) {
                return false;
            } else if (board[row][col] != 0) {
                return false;
            } else {
                return true;
            }
        }

        public static int getOnward(int row, int col){
            int onward = 0;
            for (int k = 0; k < 8; k++) {
                int onwardRow = row + rowMoves[k];
                int onwardCol = col + colMoves[k];

                if (valid(board, onwardRow, onwardCol)) {
                    onward++;
                }
            }
            return onward;
        }

//Warnsdorff's Rule
        public static int[] chooseBestMove (int[][] board, int row, int col, int i){
            int[][] possibleMoves = new int[8][3];
            int numPossible = 0;
            int[] bestMove = {-1, -1};

            for (int j = 0; j < 8; j++) {
                int newRow = row + rowMoves[j];
                int newCol = col + colMoves[j];

                if (valid(board, newRow, newCol)) {
                    numPossible++;
                    possibleMoves[numPossible - 1][0] = newRow;
                    possibleMoves[numPossible - 1][1] = newCol;
                    int onward = getOnward(newRow, newCol);
                    possibleMoves[numPossible - 1][2] = onward;
                }
            }

            int[][] rankedMoves = new int[numPossible][2];
            boolean[] taken = new boolean[numPossible];

            for (int s = 0; s < numPossible; s++) {
                int min = Integer.MAX_VALUE;
                int minOnward = Integer.MAX_VALUE;
                int minIndex = -1;
                int thisRow = 0;
                int thisCol = 0;
                for (int t = 0; t < numPossible; t++) {
                    if (!taken[t]) {
                        int currentOnwardMoves = possibleMoves[t][2];
                        if (currentOnwardMoves< min || (currentOnwardMoves == min && getOnward(possibleMoves[t][0], possibleMoves[t][1]) < minOnward)) {
                            min = possibleMoves[t][2];
                            thisRow = possibleMoves[t][0];
                            thisCol = possibleMoves[t][1];
                            minIndex = t;
                        }
                    }
                }
                rankedMoves[s][0] = thisRow;
                rankedMoves[s][1] = thisCol;
                taken[minIndex] = true;
            }
            if(i<numPossible){
                bestMove[0] = rankedMoves[i][0];
                bestMove[1] = rankedMoves[i][1];
            }else{
                bestMove[0] = row + rowMoves[i];
                bestMove[1] =  col + colMoves[i];
            }
            return bestMove;
        }


        public static void main (String[]args){
            //int[][] board = int[6][6];

            int board[][] =
                    {
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},


                    };
           // board[0][0] = 1;
            System.out.println(Arrays.deepToString(board));
            solve(board, 7, 0, 0);
        }
    }

