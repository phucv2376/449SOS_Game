import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// AC 6.x Make move in General game
public class MoveGeneralTest {
    @Test //AC 6.1 Player make valid move in a General game
    void TestValidMoveGeneral() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSGeneralLogic(blue, red);
        logic.setSimple(false);
        logic.startGame();

        assertFalse(logic.getSimple());
        assertTrue(logic.makePlayerMove(0, 0));
    }

    @Test //AC 6.2 Player tries to move on an occupied spot in a General game
    void TestInvalidMoveGeneral() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSGeneralLogic(blue, red);
        logic.startGame();
        logic.makePlayerMove(0, 0);
        logic.setSimple(false);

        assertFalse(logic.getSimple());
        assertFalse(logic.makePlayerMove(0, 0));
    }
}
