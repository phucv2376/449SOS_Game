import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStartTest {
    @Test //AC 3.1 Player successfully start new game with custom settings
    void TestStartCustomGame() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.setBoardSize(2);
        logic.setSimple(false);
        logic.startGame();
        assertEquals(GameState.BLUE, logic.getState());
        assertEquals(2, logic.getBoardSize());
        assertFalse(logic.getSimple());
    }
    @Test //AC 3.2 Player successfully start game with default settings
    void TestStartDefaultGame() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        assertEquals(GameState.BLUE, logic.getState());
        assertEquals(3, logic.getBoardSize());
        assertTrue(logic.getSimple());
    }
}
