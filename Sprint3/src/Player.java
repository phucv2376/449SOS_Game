import java.util.Stack;

//Player class; can only be 2 instances;blue and red
public class Player {
    private final String color;
    private char letter;
    private Boolean isComputer = false;
    private final Stack<int[]> prevMoves = new Stack<>();
    private int points; //Obsolete in simple game

    //Constructor
    public Player(String color, char letter) {
        this.color = color;
        this.letter = letter;
    }

    //get player move history
    public Stack<int[]> getPrevMoves() {
        return prevMoves;
    }
    //addMove to player history; does not check for conflicts
    public void addMove(int row, int col) {
        int[] move = new int[]{row, col, this.getLetter()};
        this.prevMoves.push(move);
    }
    //reset player state on game restarts
    public void reset() {
        setPoints(0);
        prevMoves.clear();
    }

    //getters & setters
    public char getLetter() {
        return letter;
    }
    public void setLetter(char letter) {
        this.letter = letter;
    }
    public Boolean getComputer() {
        return isComputer;
    }
    public void setComputer(Boolean isComputer) {
        this.isComputer = isComputer;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public int getPoints() {
        return points;
    }
    public String getColor() {
        return color;
    }
}
