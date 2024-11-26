import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


//AC 1.x
class GridSizeTest {
    @Test //AC 1.1 The game board size defaults to 3x3.
    void testDefaultBoardSize() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        assertEquals(3, logic.getBoardSize());
    }

    @Test //AC 1.2 The player selects a custom board size
    void testCustomBoardSize() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.setBoardSize(15);
        assertEquals(15, logic.getBoardSize());
    }

    @Test //AC 1.3 Player attempts to choose an invalid board size (less than 2x2).
    void testInvalidBoardSize() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.setBoardSize(-1);
        
        SOSLogic logic2 = new SOSSimpleLogic(blue, red);
        logic2.setBoardSize(35);
        assertEquals(2, logic.getBoardSize());
        assertEquals(30, logic2.getBoardSize());
    }
}
