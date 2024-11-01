import java.util.ArrayList;
import java.util.Arrays;

public abstract class SOSLogic {
    private boolean isSimple = true;
    protected GameState state;
    protected final Player blue;
    protected final Player red;
    protected int boardSize;
    protected char[][] board;              //[0] = blue
    int totalCompletedSeq = 0;
    int movesMade = 0;

    //setters & getters
    public boolean getSimple() {
        return isSimple;
    }
    public void setSimple(boolean simple) {
        isSimple = simple;
    }
    public GameState getState() {
        return state;
    }
    public void setBoardSize(int boardSize) {
        if (boardSize < 2) {
            this.boardSize = 2;
        } else {
            this.boardSize = Math.min(boardSize, 30);
            this.board = new char[boardSize][boardSize];
        }
    }

    //Constructor
    public SOSLogic(Player blue, Player red) {
        this.blue = blue;
        this.red = red;
        this.state = GameState.PRE;
        this.boardSize = 3;
        this.board = new char[boardSize][boardSize];
    }

    //Checks if grid position is occupied
    protected Boolean isOccupied(int row, int col) {
        return (board[row][col] == 'S' || board[row][col] == 'O');
    }

    //Alternate to the other player's turn
    public void altTurn() {
        if (this.state == GameState.BLUE) {
            this.state = GameState.RED;
        }
        else {
            this.state = GameState.BLUE;
        }
    }

    //Start the game
    public void startGame() {
        this.state = GameState.BLUE;
        this.board = new char[boardSize][boardSize];
        this.totalCompletedSeq = 0;
        this.movesMade = 0;
    }

    //Getters and setters
    public int getBoardSize() {
        return boardSize;
    }
    public boolean isSimple() {
        return isSimple;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    //Returns number of completed sequence from one move
    public int calculateCompletedSequence() {
        //NTS: COL & ROW START AT 0
        Player currPlayer = (this.state) == GameState.BLUE ? this.blue : this.red;
        Player oppPlayer = (this.state) == GameState.BLUE ? this.red : this.blue;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Check horizontal seq
                if (j >= 2 && board[i][j] == 'S' && board[i][j - 1] == 'O' && board[i][j - 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i, j - 1}, {i, j- 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq))) {
                        currPlayer.addCompletedSequence(completedSeq);
                        totalCompletedSeq++;
                    }
                }
                // Check vertical seq
                if (i >= 2 && board[i][j] == 'S' && board[i - 1][j] == 'O' && board[i - 2][j] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j}, {i - 2, j}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq))) {
                        currPlayer.addCompletedSequence(completedSeq);
                        totalCompletedSeq++;
                    }
                }

                // Check top-left to bottom right seq
                if (i >= 2 && j >= 2 && board[i][j] == 'S' && board[i - 1][j - 1] == 'O' && board[i - 2][j - 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j - 1}, {i - 2, j- 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq))) {
                        currPlayer.addCompletedSequence(completedSeq);
                        totalCompletedSeq++;
                    }
                }
                // Check top-right to bottom left seq
                if (i >= 2 && j <= boardSize - 3 && board[i][j] == 'S' && board[i - 1][j + 1] == 'O' && board[i - 2][j + 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j + 1}, {i - 2, j + 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq))) {
                        currPlayer.addCompletedSequence(completedSeq);
                        totalCompletedSeq++;
                    }
                }
            }
        }
        return totalCompletedSeq;
    }

    private static boolean containsSeq(ArrayList<int[][]> seqList, int[][] targetArray) {
        for (int[][] seq : seqList) {
            if (Arrays.deepEquals(seq, targetArray)) {
                return true; // Found a match
            }
        }
        return false; // No match found
    }

    public abstract boolean makePlayerMove(int row, int col);
}