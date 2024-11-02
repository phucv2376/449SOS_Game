import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleGameOverTest {
    @Test
    void TestRedWinHorizontalSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        blue.setLetter('S');
        red.setLetter('O');
        gameConds.startGame();

        gameConds.makePlayerMove(1, 0); //B
        gameConds.makePlayerMove(1, 1); //R
        gameConds.makePlayerMove(0, 0); //B
        red.setLetter('S');
        gameConds.makePlayerMove(1, 2); //R
        assertEquals(GameState.REDWIN,gameConds.getState());
    }
    @Test
    void TestBlueWinVerticalSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        red.setLetter('O');
        gameConds.startGame();

        gameConds.makePlayerMove(0, 0); //B
        gameConds.makePlayerMove(1, 0); //R
        gameConds.makePlayerMove(2, 0); //B
        assertEquals(GameState.BLUEWIN,gameConds.getState());
    }
    @Test
    void TestBlueWinDiagonalSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        gameConds.startGame();
        gameConds.makePlayerMove(0, 0); //B
        red.setLetter('O');
        gameConds.makePlayerMove(1, 1); //R
        gameConds.makePlayerMove(2, 2); //B
        assertEquals(GameState.BLUEWIN,gameConds.getState());
    }
    @Test
    void TestDrawSimple() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        gameConds.startGame();

        gameConds.makePlayerMove(0, 0); //B
        gameConds.makePlayerMove(1, 0); //R
        gameConds.makePlayerMove(2, 0); //B
        gameConds.makePlayerMove(0, 1); //B
        gameConds.makePlayerMove(1, 1); //R
        gameConds.makePlayerMove(2, 1); //B
        gameConds.makePlayerMove(0, 2); //B
        gameConds.makePlayerMove(1, 2); //R
        gameConds.makePlayerMove(2, 2); //B
        assertEquals(GameState.DRAW,gameConds.getState());
    }
}
