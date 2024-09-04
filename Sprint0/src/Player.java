import java.util.Stack;

public class Player {
    private String color;
    private char letter;
    private Boolean isComputer = false;
    private Stack<int[]> prevMoves = new Stack<>();
    private Boolean isCurrTurn = false;

    public Player(String color, char letter) {
        this.color = color;
        this.letter = letter;
    }

    public Boolean getCurrTurn() {
        return isCurrTurn;
    }

    public void setCurrTurn(Boolean currTurn) {
        isCurrTurn = currTurn;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getComputer() {
        return isComputer;
    }

    public void setComputer(Boolean computer) {
        isComputer = computer;
    }

    public Stack<int[]> getPrevMoves() {
        return prevMoves;
    }

    public void addMove(int[] move) {
        this.prevMoves.push(move);
    }

}
