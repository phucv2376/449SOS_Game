import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//AC 4.x Make player move in simple game
public class MoveSimpleTest {
    @Test //AC 4.1 Player make valid move in a simple game
    void TestValidMoveSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        gameConds.startGame();
        assertTrue(gameConds.getSimple());
        assertTrue(gameConds.makePlayerMove(0, 0));
    }

    @Test //AC 4.2 Player tries to move on an occupied spot in a simple game
    void TestInvalidMoveSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        gameConds.startGame();
        gameConds.makePlayerMove(0, 0);
        assertTrue(gameConds.getSimple());
        assertFalse(gameConds.makePlayerMove(0, 0));
    }
}