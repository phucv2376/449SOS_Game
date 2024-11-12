import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleGameOverTest {
    @Test
    void TestRedWinHorizontalSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        blue.setLetter('S');
        red.setLetter('O');
        logic.startGame();

        logic.makePlayerMove(1, 0); //B
        logic.makePlayerMove(1, 1); //R
        logic.makePlayerMove(0, 0); //B
        red.setLetter('S');
        logic.makePlayerMove(1, 2); //R
        assertEquals(GameState.REDWIN,logic.getState());
    }
    @Test
    void TestBlueWinVerticalSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        red.setLetter('O');
        logic.startGame();

        logic.makePlayerMove(0, 0); //B
        logic.makePlayerMove(1, 0); //R
        logic.makePlayerMove(2, 0); //B
        assertEquals(GameState.BLUEWIN,logic.getState());
    }
    @Test
    void TestBlueWinDiagonalSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        logic.makePlayerMove(0, 0); //B
        red.setLetter('O');
        logic.makePlayerMove(1, 1); //R
        logic.makePlayerMove(2, 2); //B
        assertEquals(GameState.BLUEWIN,logic.getState());
    }
    @Test
    void TestDrawSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();

        logic.makePlayerMove(0, 0); //B
        logic.makePlayerMove(1, 0); //R
        logic.makePlayerMove(2, 0); //B
        logic.makePlayerMove(0, 1); //B
        logic.makePlayerMove(1, 1); //R
        logic.makePlayerMove(2, 1); //B
        logic.makePlayerMove(0, 2); //B
        logic.makePlayerMove(1, 2); //R
        logic.makePlayerMove(2, 2); //B
        assertEquals(GameState.DRAW,logic.getState());
    }
}
