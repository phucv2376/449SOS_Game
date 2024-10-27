public class SOSSimpleLogic extends SOSLogic {
    public SOSSimpleLogic() {
        super();
    }

    //Make player move in accordance with game conditions.
    public boolean makePlayerMove(int row, int col) {
        if (row > boardSize - 1 || col > boardSize - 1) { //Handle of range user selection
            return false;
        }
        if (this.state == GameState.BLUE || this.state == GameState.RED) {
            Player currPlayer = (this.state) == GameState.BLUE ? this.blue : this.red;
            if (!isOccupied(row, col)) {
                currPlayer.addMove(row, col);
                this.altTurn();
                return true;
            }
        }
        return false;
    }

    //Returns number of completed sequence from one move
    public int checkCompletedSequence(char letter, int row, int col) {
//        int totalCompleted = 0;
//        int allMoves = getAllMoves();
//        //Check horizontal
//        if (letter == 'S') {
//            if (col > 1 && allMoves[row][col - 1] == 'O' && allMoves[row][col - 2] == 'S') {
//
//            }
//        } else {
//
//        }
//        //Check vertical
//        //Check diagonal
        return 0;
    }

    public boolean checkEndOfGame() {
        return false;
    }
}
