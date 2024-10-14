import java.util.Arrays;

public class GameConditions {
    private boolean isSimple = true;
    private GameState state;
    private final Player blue;
    private final Player red;
    private int boardSize;

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
    public GameConditions() {
        this.blue = new Player("Blue", 'S');
        this.red = new Player("Red", 'S');
        this.isSimple = true;
        this.state = GameState.PRE;
        this.boardSize = 3;
    }

    //Checks if grid position is occupied
    private Boolean isOccupied(int row, int col) {
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

    //Make player move in accordance with game conditions.
    public Boolean makePlayerMove(int row, int col) {
        if (row > boardSize - 1 || col > boardSize - 1) {
            return false;
        }
        if (this.state == GameState.BLUE || this.state == GameState.RED) {
            Player currPlayer = (this.state) == GameState.BLUE ? this.blue : this.red;
            if (!isOccupied(row, col)) {
                currPlayer.addMove(row, col, currPlayer.getLetter());
                this.altTurn();
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

    //Check for win TBA
    public Boolean checkWin() {
        return Boolean.FALSE;
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
    public void setState(GameState state) {
        this.state = state;
    }
}