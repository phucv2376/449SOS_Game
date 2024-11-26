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
                int[] move = {currPlayer.getLetter(), row, col}; //add player move history for recording purposes
                addMoveRecord(move);
                totalCompletedSeq = calculateCompletedSequence();
                if (totalCompletedSeq > 0 || movesMade == boardSize * boardSize) { //If there is a completed sequence or full board, end game
                    endGame();
                    return true;
                }
                this.handleTurnCycle(); //alternate turn after non-game winning move
                return true;
            }
        }
        return false;
    }
    private void endGame() {
        if (totalCompletedSeq == 0) { //Game is draw if no completed sequence
            setState(GameState.DRAW);
        } else
            setState(getState() == GameState.RED ? GameState.REDWIN : GameState.BLUEWIN); //Declare winner
    }
}
