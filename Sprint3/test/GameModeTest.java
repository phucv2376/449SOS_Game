import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameModeTest {
    @Test // AC 2.1 Player select ‘Simple’ game mode [AI generated]
    void TestChooseSimpleGame() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        gameConds.setSimple(true);
        assertTrue(gameConds.getSimple());
    }


    @Test //AC 2.2 Default Simple game mode [AI generated]
    void TestDefaultGameMode() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSSimpleLogic(blue, red);
        assertTrue(gameConds.getSimple());
    }

    @Test //AC 2.3 Player select 'General' game mode
    void TestChooseGeneralGame() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic gameConds = new SOSGeneralLogic(blue, red);
        gameConds.setSimple(false);
        assertFalse(gameConds.getSimple());
    }
}

