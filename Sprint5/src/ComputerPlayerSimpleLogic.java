import java.util.Random;

public class ComputerPlayerSimpleLogic extends Player {
    public ComputerPlayerSimpleLogic(String color, char letter) {
        super(color, letter);
    }
    @Override
    public boolean makeComputerMove() {
        //Make offensive move when there is a game winning move
        for (char letter : new char[]{'S', 'O'}) {
            for (int i = 0; i < logic.getBoardSize(); i++) {
                for (int j = 0; j < logic.getBoardSize(); j++) {
                    if ( !logic.isOccupied(i, j) && logic.tryMove(i, j, letter) > 0) {
                        this.setLetter(letter);
                        return logic.makePlayerMove(i, j);
                    }
                }
            }
        }
        //Make random computer move if there was no winning move
        return makeRandomComputerMove();
    }
    public boolean getIsComputer() {
        return true;
    }
}
