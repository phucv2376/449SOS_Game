import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameModeTest {
    @Test // AC 2.1 Player select ‘Simple’ game mode [AI generated]
    void TestChooseSimpleGame() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.setSimple(true);
        assertTrue(logic.getSimple());
    }


    @Test //AC 2.2 Default Simple game mode [AI generated]
    void TestDefaultGameMode() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        assertTrue(logic.getSimple());
    }

    @Test //AC 2.3 Player select 'General' game mode
    void TestChooseGeneralGame() {
        Player blue = new Player("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSGeneralLogic(blue, red);
        logic.setSimple(false);
        assertFalse(logic.getSimple());
    }
}

