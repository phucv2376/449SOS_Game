import java.util.ArrayList;

//Player class; can only be 2 instances;blue and red
public class Player {
    private final String color;
    private char letter;
    private Boolean isComputer = false;
    private int points; //Obsolete in simple game
    private final ArrayList<int[][]> completedSequences = new ArrayList<>();

    //Constructor
    public Player(String color, char letter) {
        this.color = color;
        this.letter = letter;
    }

    //reset player state on game restarts
    public void reset() {
        completedSequences.clear();
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
    public String getColor() {
        return color;
    }
    public void addCompletedSequence(int[][] sequence) {
        if (!completedSequences.contains(sequence))
            this.completedSequences.add(sequence);
    }
    public ArrayList<int[][]> getCompletedSequences() {
        return completedSequences;
    }
}
