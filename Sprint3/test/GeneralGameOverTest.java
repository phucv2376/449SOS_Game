import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralGameOverTest {
    @Test
    void TestRedWinGeneral() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        gameConds.startGame();
        red.setLetter('O');
        gameConds.makePlayerMove(0, 0); //B
        gameConds.makePlayerMove(1, 0); //R
        gameConds.makePlayerMove(2, 0); //B
        gameConds.makePlayerMove(0, 1); //B
        gameConds.makePlayerMove(1, 1); //R
        gameConds.makePlayerMove(2, 1); //B
        gameConds.makePlayerMove(0, 2); //B
        gameConds.makePlayerMove(1, 2); //R
        gameConds.makePlayerMove(2, 2); //B

        assertEquals(GameState.BLUEWIN,gameConds.getState());

    }
    @Test
    void TestBlueWinGeneral() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        gameConds.startGame();
        blue.setLetter('O');

        gameConds.makePlayerMove(0, 0); //B
        gameConds.makePlayerMove(1, 0); //R
        gameConds.makePlayerMove(2, 0); //B
        gameConds.makePlayerMove(0, 1); //B
        gameConds.makePlayerMove(1, 1); //R
        gameConds.makePlayerMove(2, 1); //B
        gameConds.makePlayerMove(0, 2); //B
        gameConds.makePlayerMove(1, 2); //R
        gameConds.makePlayerMove(2, 2); //B
        assertEquals(GameState.REDWIN,gameConds.getState());
    }
    @Test
    void TestDrawGeneral() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSGeneralLogic(blue, red);
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
