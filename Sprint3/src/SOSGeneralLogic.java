public class SOSGeneralLogic extends SOSLogic {
    public SOSGeneralLogic(Player blue, Player red) {
        super(blue, red);
    }

    public boolean checkWin() {
        return false;
    }
    //Make player move in accordance with game conditions.
    public boolean makePlayerMove(int row, int col) {
        if (row > boardSize - 1 || col > boardSize - 1) { //Handle of range user selection
            return false;
        }
        if (this.state == GameState.BLUE || this.state == GameState.RED) {
            Player currPlayer = (this.state) == GameState.BLUE ? this.blue : this.red;
            if (!isOccupied(row, col)) {
                board[row][col] = currPlayer.getLetter();
                movesMade++;
                if (movesMade == boardSize * boardSize) {
                    endGame();
                    return true;
                }
                calculateCompletedSequence();
                this.altTurn();
                return true;
            }
        }
        return false;
    }

    public void endGame() {
        if (blue.getCompletedSequences().size() > red.getCompletedSequences().size())
            setState(GameState.BLUEWIN);
        else if (red.getCompletedSequences().size() > blue.getCompletedSequences().size())
            setState(GameState.REDWIN);
        else
            setState(GameState.DRAW);
    }
}
