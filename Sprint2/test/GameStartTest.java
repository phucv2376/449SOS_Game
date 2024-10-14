import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStartTest {
    @Test //AC 3.1 Player successfully start new game with custom settings
    void TestStartCustomGame() {
        GameConditions gameConds = new GameConditions();
        gameConds.setBoardSize(2);
        gameConds.setSimple(false);
        gameConds.startGame();
        assertEquals(GameState.BLUE, gameConds.getState());
        assertEquals(2, gameConds.getBoardSize());
        assertFalse(gameConds.getSimple());
    }
    @Test //AC 3.2 Player successfully start game with default settings
    void TestStartDefaultGame() {
        GameConditions gameConds = new GameConditions();
        gameConds.startGame();
        assertEquals(GameState.BLUE, gameConds.getState());
        assertEquals(3, gameConds.getBoardSize());
        assertTrue(gameConds.getSimple());
    }
}
