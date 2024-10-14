import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//AC 4.x Make player move in simple game
public class MoveSimpleTest {
    @Test //AC 4.1 Player make valid move in a simple game
    void TestValidMoveSimple() {
        GameConditions gameConds = new GameConditions();
        gameConds.startGame();
        assertTrue(gameConds.getSimple());
        assertTrue(gameConds.makePlayerMove(0, 0));
    }

    @Test //AC 4.2 Player tries to move on an occupied spot in a simple game
    void TestInvalidMoveSimple() {
        GameConditions gameConds = new GameConditions();
        gameConds.startGame();
        gameConds.makePlayerMove(0, 0);
        assertTrue(gameConds.getSimple());
        assertFalse(gameConds.makePlayerMove(0, 0));
    }
}