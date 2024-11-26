public class ComputerPlayerGeneralLogic extends Player {
    public ComputerPlayerGeneralLogic(String color, char letter) {
        super(color, letter);
    }

    public boolean makeComputerMove() {
        //Make offensive move when there is a game winning move
        int bestNumOfCompletedSeq = 0; //Max number of completed sequence if move is made
        int[] bestSeq = new int[2];
        char bestSeqLetter = 'O';

        for (char letter : new char[]{'S', 'O'}) {
            for (int i = 0; i < logic.getBoardSize(); i++) {
                for (int j = 0; j < logic.getBoardSize(); j++) {
                    int numOfCompletedSeq = logic.tryMove(i, j, letter);
                    if ( !logic.isOccupied(i, j) && numOfCompletedSeq > bestNumOfCompletedSeq) {
                        bestNumOfCompletedSeq = numOfCompletedSeq;
                        bestSeq[0] = i;
                        bestSeq[1] = j;
                        bestSeqLetter = letter;
                    }
                }
            }
        }
        if (bestNumOfCompletedSeq > 0) {
            this.setLetter(bestSeqLetter);
            logic.makePlayerMove(bestSeq[0], bestSeq[1]);
            return true;
        }
        //Make random move
        return makeRandomComputerMove();
    }
    public boolean getIsComputer() {
        return true;
    }
}
