import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameModeTest {
    @Test // AC 2.1 Player select ‘Simple’ game mode [AI generated]
    void TestChooseSimpleGame() {
        GameConditions gameConds = new GameConditions();
        gameConds.setSimple(true);
        assertTrue(gameConds.getSimple());
    }


    @Test //AC 2.2 Default Simple game mode [AI generated]
    void TestDefaultGameMode() {
        GameConditions gameConds = new GameConditions();
        assertTrue(gameConds.getSimple());
    }

    @Test //AC 2.3 Player select 'General' game mode
    void TestChooseGeneralGame() {
        GameConditions gameConds = new GameConditions();
        gameConds.setSimple(false);
        assertFalse(gameConds.getSimple());
    }
}

