import java.util.ArrayList;
import java.util.Arrays;

public abstract class SOSLogic {
    private boolean isSimple = true;
    protected GameState state;
    protected Player blue;
    protected Player red;
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
    public void setBlue(Player blue) {
        this.blue = blue;
    }
    public void setRed(Player red) {
        this.red = red;
    }
    public GameState getState() {
        return state;
    }
    public void setBoardSize(int boardSize) {
        final int MIN_BOARD_SIZE = 3;
        final int MAX_BOARD_SIZE = 30;

        //Keep board size within range
        if (boardSize < MIN_BOARD_SIZE) {
            this.boardSize = MIN_BOARD_SIZE;
        } else {
            this.boardSize = Math.min(boardSize, MAX_BOARD_SIZE);
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
    public void handleTurnCycle() {
        if (this.state == GameState.PRE) { //Blue's turn if it is a new game
            this.state = GameState.BLUE;
            if (blue.getIsComputer()) //handle computer move
                blue.makeComputerMove();
        } else if (this.state == GameState.BLUE) { //alternate turn
            this.state = GameState.RED;
            if (red.getIsComputer()) //handle computer move
                red.makeComputerMove();
        } else {
            this.state = GameState.BLUE; //alternate turn
            if (blue.getIsComputer()) //handle computer move
                blue.makeComputerMove();
        }
    }

    //Start the game
    public void startGame() {
        this.board = new char[boardSize][boardSize];
        this.totalCompletedSeq = 0;
        this.movesMade = 0;

        blue.setLogic(this); //give players access to current board conditions
        red.setLogic(this);

        this.handleTurnCycle(); //Change to blue turn
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

    //Returns delta of completed sequence from one move in the actual board
    protected int calculateCompletedSequence() {
        //NTS: COL & ROW START AT 0
        int completedSeqDelta = 0;
        Player currPlayer = (this.state) == GameState.BLUE ? this.blue : this.red;
        Player oppPlayer = (this.state) == GameState.BLUE ? this.red : this.blue;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Check horizontal seq
                if (j >= 2 && board[i][j] == 'S' && board[i][j - 1] == 'O' && board[i][j - 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i, j - 1}, {i, j- 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq))) {
                        currPlayer.addCompletedSequence(completedSeq);
                        completedSeqDelta++;
                    }
                }
                // Check vertical seq
                if (i >= 2 && board[i][j] == 'S' && board[i - 1][j] == 'O' && board[i - 2][j] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j}, {i - 2, j}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq))) {
                        currPlayer.addCompletedSequence(completedSeq);
                        completedSeqDelta++;
                    }
                }

                // Check top-left to bottom right seq
                if (i >= 2 && j >= 2 && board[i][j] == 'S' && board[i - 1][j - 1] == 'O' && board[i - 2][j - 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j - 1}, {i - 2, j- 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq))) {
                        currPlayer.addCompletedSequence(completedSeq);
                        completedSeqDelta++;
                    }
                }
                // Check top-right to bottom left seq
                if (i >= 2 && j <= boardSize - 3 && board[i][j] == 'S' && board[i - 1][j + 1] == 'O' && board[i - 2][j + 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j + 1}, {i - 2, j + 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq))) {
                        currPlayer.addCompletedSequence(completedSeq);
                        completedSeqDelta++;
                    }
                }
            }
        }
        return completedSeqDelta;
    }

    //Tries a move in a hypothetical scenario; does not impact the actual game //True if complete seq move, False if no complete seq move
    public int tryMove(int row, int col, char letter) {
        int completedSeqDelta = 0;
        Player currPlayer = (this.state) == GameState.BLUE ? this.blue : this.red; //Specify the curent turn player and their opposition
        Player oppPlayer = (this.state) == GameState.BLUE ? this.red : this.blue;
        char[][] tempBoard = new char[boardSize][boardSize]; // Initialize the 2D array
        ArrayList<int[][]> tempCompletedSeqs = new ArrayList<>();

        //Clone current board state onto a hypothetical tempBoard
        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, boardSize);
        }

        tempBoard[row][col] = letter; //make hypothetical move on hypothetical board

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Check horizontal seq
                if (j >= 2 && tempBoard[i][j] == 'S' && tempBoard[i][j - 1] == 'O' && tempBoard[i][j - 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i, j - 1}, {i, j- 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq) || containsSeq(tempCompletedSeqs, completedSeq))) {
                        completedSeqDelta++;
                        tempCompletedSeqs.add(completedSeq);
                    }
                }
                // Check vertical seq on hypothetical board
                if (i >= 2 && tempBoard[i][j] == 'S' && tempBoard[i - 1][j] == 'O' && tempBoard[i - 2][j] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j}, {i - 2, j}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq) || containsSeq(tempCompletedSeqs, completedSeq))) {
                        completedSeqDelta++;
                        tempCompletedSeqs.add(completedSeq);
                    }
                }

                // Check top-left to bottom right seq on hypothetical board
                if (i >= 2 && j >= 2 && tempBoard[i][j] == 'S' && tempBoard[i - 1][j - 1] == 'O' && tempBoard[i - 2][j - 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j - 1}, {i - 2, j- 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq) || containsSeq(tempCompletedSeqs, completedSeq))) {
                        completedSeqDelta++;
                        tempCompletedSeqs.add(completedSeq);
                    }
                }
                // Check top-right to bottom left seq on hypothetical board
                if (i >= 2 && j <= boardSize - 3 && tempBoard[i][j] == 'S' && tempBoard[i - 1][j + 1] == 'O' && tempBoard[i - 2][j + 2] == 'S') {
                    int[][] completedSeq = new int[][]{{i, j}, {i - 1, j + 1}, {i - 2, j + 2}};
                    if (!(containsSeq(currPlayer.getCompletedSequences(), completedSeq) || containsSeq(oppPlayer.getCompletedSequences(), completedSeq) || containsSeq(tempCompletedSeqs, completedSeq))) {
                        completedSeqDelta++;
                        tempCompletedSeqs.add(completedSeq);
                    }
                }
            }
        }
        return completedSeqDelta; //Returns the delta of completedSequences in hypothetical board vs actual board
    }

    //Checks if a sequence exists in a list of sequences
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