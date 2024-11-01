public class SOSSimpleLogic extends SOSLogic {
    public SOSSimpleLogic(Player blue, Player red) {
        super(blue, red);
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
                movesMade = movesMade + 1;
                if (calculateCompletedSequence() == 1 || movesMade == boardSize * boardSize) {
                    endGame();
                    return true;
                }
                this.altTurn();
                return true;
            }
        }
        return false;
    }

    public void endGame() {
        if (movesMade == boardSize * boardSize) {
            setState(GameState.DRAW);
            return;
        }
        setState(getState() == GameState.RED ? GameState.REDWIN : GameState.BLUEWIN);
    }
}
