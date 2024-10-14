import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


//AC 1.x
class BoardSizeTest {
    @Test //AC 1.1 The game board size defaults to 3x3.
    void testDefaultBoardSize() {
        GameConditions gameConds = new GameConditions();
        assertEquals(3, gameConds.getBoardSize());
    }

    @Test //AC 1.2 The player selects a custom board size
    void testCustomBoardSize() {
        GameConditions gameConds = new GameConditions();
        gameConds.setBoardSize(15);
        assertEquals(15, gameConds.getBoardSize());
    }

    @Test //AC 1.3 Player attempts to choose an invalid board size (less than 2x2).
    void testInvalidBoardSize() {
        GameConditions gameConds = new GameConditions();
        gameConds.setBoardSize(-1);
        GameConditions gameConds2 = new GameConditions();
        gameConds2.setBoardSize(35);
        assertEquals(2, gameConds.getBoardSize());
        assertEquals(30, gameConds2.getBoardSize());
    }
}
