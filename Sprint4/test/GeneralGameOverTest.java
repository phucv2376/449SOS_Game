import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralGameOverTest {
    @Test
    void TestRedWinGeneral() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        red.setLetter('O');
        logic.makePlayerMove(0, 0); //B
        logic.makePlayerMove(1, 0); //R
        logic.makePlayerMove(2, 0); //B
        logic.makePlayerMove(0, 1); //B
        logic.makePlayerMove(1, 1); //R
        logic.makePlayerMove(2, 1); //B
        logic.makePlayerMove(0, 2); //B
        logic.makePlayerMove(1, 2); //R
        logic.makePlayerMove(2, 2); //B

        assertEquals(GameState.BLUEWIN,logic.getState());

    }
    @Test
    void TestBlueWinGeneral() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        blue.setLetter('O');

        logic.makePlayerMove(0, 0); //B
        logic.makePlayerMove(1, 0); //R
        logic.makePlayerMove(2, 0); //B
        logic.makePlayerMove(0, 1); //B
        logic.makePlayerMove(1, 1); //R
        logic.makePlayerMove(2, 1); //B
        logic.makePlayerMove(0, 2); //B
        logic.makePlayerMove(1, 2); //R
        logic.makePlayerMove(2, 2); //B
        assertEquals(GameState.REDWIN,logic.getState());
    }
    @Test
    void TestDrawGeneral() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSGeneralLogic(blue, red);
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
