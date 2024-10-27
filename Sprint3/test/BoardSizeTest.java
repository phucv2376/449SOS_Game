import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


//AC 1.x
class BoardSizeTest {
    @Test //AC 1.1 The game board size defaults to 3x3.
    void testDefaultBoardSize() {
        SOSLogic gameConds = new SOSLogic();
        assertEquals(3, gameConds.getBoardSize());
    }

    @Test //AC 1.2 The player selects a custom board size
    void testCustomBoardSize() {
        SOSLogic gameConds = new SOSLogic();
        gameConds.setBoardSize(15);
        assertEquals(15, gameConds.getBoardSize());
    }

    @Test //AC 1.3 Player attempts to choose an invalid board size (less than 2x2).
    void testInvalidBoardSize() {
        SOSLogic gameConds = new SOSLogic();
        gameConds.setBoardSize(-1);
        SOSLogic gameConds2 = new SOSLogic();
        gameConds2.setBoardSize(35);
        assertEquals(2, gameConds.getBoardSize());
        assertEquals(30, gameConds2.getBoardSize());
    }
}
