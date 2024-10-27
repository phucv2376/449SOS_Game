import java.util.Arrays;

public abstract class SOSLogic {
    private boolean isSimple = true;
    protected GameState state;
    protected final Player blue;
    protected final Player red;
    protected int boardSize;
    protected char[][] board;

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
    public Player getBlue() {
        return blue;
    }
    public Player getRed() {
        return red;
    }
    public void setBoardSize(int boardSize) {
        if (boardSize < 2) {
            this.boardSize = 2;
        } else this.boardSize = Math.min(boardSize, 30);
    }

    //Constructor
    public SOSLogic() {
        this.blue = new Player("Blue", 'S');
        this.red = new Player("Red", 'S');
        this.state = GameState.PRE;
        this.boardSize = 3;
    }

    //Checks if grid position is occupied
    protected Boolean isOccupied(int row, int col) {
        for (int[] move : red.getPrevMoves()) {
            if (Arrays.equals(move, new int[]{row, col, (int) 'S'}) || Arrays.equals(move, new int[]{row, col, (int) 'O'})) {
                return true;
            }
        }
        for (int[] move : blue.getPrevMoves()) {
            if (Arrays.equals(move, new int[]{row, col, (int) 'S'}) || Arrays.equals(move, new int[]{row, col, (int) 'O'})) {
                return true;
            }
        }
        return false;
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

    public boolean checkEndOfGame() {
        int totalMoves = blue.getPrevMoves().size() + red.getPrevMoves().size();
        return totalMoves == this.boardSize * this.boardSize;
    }

    //Start the game
    public void startGame() {
        this.state = GameState.BLUE;
        blue.reset();
        red.reset();
    }

    //Getters and setters
    public int getBoardSize() {
        return boardSize;
    }
    public boolean isSimple() {
        return isSimple;
    }

    public int getAllMoves() {
        blue.getPrevMoves().clone();
            return 0;
    //      return res;
    }
    public void setState(GameState state) {
        this.state = state;
    }

    public abstract boolean makePlayerMove(int row, int col);
}