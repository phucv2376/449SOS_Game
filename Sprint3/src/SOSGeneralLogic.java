public class SOSGeneralLogic extends SOSLogic {
    int points = 0;

    public boolean checkWin() {
        return false;
    }
    public boolean makePlayerMove(int row, int col) {
        if (row > boardSize - 1 || col > boardSize - 1) {
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

}
