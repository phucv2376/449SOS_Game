import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// AC 6.x Make move in General game
public class MoveGeneralTest {
    @Test //AC 6.1 Player make valid move in a General game
    void TestValidMoveGeneral() {
        GameConditions gameConds = new GameConditions();
        gameConds.setSimple(false);
        gameConds.startGame();

        assertFalse(gameConds.getSimple());
        assertTrue(gameConds.makePlayerMove(0, 0));
    }

    @Test //AC 6.2 Player tries to move on an occupied spot in a General game
    void TestInvalidMoveGeneral() {
        GameConditions gameConds = new GameConditions();
        gameConds.startGame();
        gameConds.makePlayerMove(0, 0);
        gameConds.setSimple(false);

        assertFalse(gameConds.getSimple());
        assertFalse(gameConds.makePlayerMove(0, 0));
    }
}
