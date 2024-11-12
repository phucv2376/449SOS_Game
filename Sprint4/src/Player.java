import java.util.ArrayList;
import java.util.Random;

//Player class; can only be 2 instances;blue and red
public class Player {
    private final String color;
    private char letter;
    private Boolean isComputer = false;
    private final ArrayList<int[][]> completedSequences = new ArrayList<>();
    protected SOSLogic logic;

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
    public void setLogic(SOSLogic logic) {
        this.logic = logic;
    }
    public boolean getIsComputer() {
        return false;
    }

    public boolean makeComputerMove() {
        return false;
    }
    public boolean makeRandomComputerMove() {
        Random random = new Random();
        while (true) { //loop until valid random move is made
            int row = random.nextInt(logic.boardSize);
            int col = random.nextInt(logic.boardSize);
            int letter = random.nextInt(2);
            if (!logic.isOccupied(row, col)) {
                if (letter == 0)
                    this.setLetter('S');
                else
                    this.setLetter('O');
                return logic.makePlayerMove(row, col);
            }
        }

    }
}
