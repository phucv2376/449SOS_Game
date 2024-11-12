import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//AC 4.x Make player move in simple game
public class MoveSimpleTest {
    @Test //AC 4.1 Player make valid move in a simple game
    void TestValidMoveSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        assertTrue(logic.getSimple());
        assertTrue(logic.makePlayerMove(0, 0));
    }

    @Test //AC 4.2 Player tries to move on an occupied spot in a simple game
    void TestInvalidMoveSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        logic.makePlayerMove(0, 0);
        assertTrue(logic.getSimple());
        assertFalse(logic.makePlayerMove(0, 0));
    }
}