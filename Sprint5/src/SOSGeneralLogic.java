public class SOSGeneralLogic extends SOSLogic {
    public SOSGeneralLogic(Player blue, Player red) {
        super(blue, red);
    }

    // Make player move in accordance with game conditions.
    public boolean makePlayerMove(int row, int col) {
        if (row > boardSize - 1
                || col > boardSize - 1) { // Handle of range user selection
            return false;
        }
        if (this.state == GameState.BLUE || this.state == GameState.RED) {
            Player currPlayer = (this.state) == GameState.BLUE ? this.blue : this.red;
            if (!isOccupied(row, col)) {
                board[row][col] = currPlayer.getLetter();
                movesMade++;
                int[] move = {currPlayer.getLetter(), row,
                        col}; // add player move history for recording purposes
                addMoveRecord(move);
                int completedSequenceDelta = calculateCompletedSequence();
                if (completedSequenceDelta
                        > 0) { // Check if new move completed a new sequence
                    totalCompletedSeq = totalCompletedSeq + completedSequenceDelta;
                    if (currPlayer.getIsComputer())
                        return currPlayer
                                .makeComputerMove(); // Get computer player to make another move
                    // if it completed a new seq
                    return true;
                }
                if (movesMade == boardSize * boardSize) {
                    endGame();
                    return true;
                }
                this.handleTurnCycle();
                return true;
            }
        }
        return false;
    }

    private void endGame() {
        if (blue.getCompletedSequences().size()
                > red.getCompletedSequences().size())
            setState(GameState.BLUEWIN);
        else if (red.getCompletedSequences().size()
                > blue.getCompletedSequences().size())
            setState(GameState.REDWIN);
        else
            setState(GameState.DRAW);
    }
}

