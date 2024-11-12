import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComputerPlayerGeneralTest {
    @Test
    void testGeneralBlueComputerPlayer() {
        Player blue = new ComputerPlayerGeneralLogic("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        assertTrue(blue.makeComputerMove());
    }

    @Test
    void testGeneralRedComputerPlayer() {
        Player blue = new Player("blue", 'S');
        Player red = new ComputerPlayerGeneralLogic("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        logic.makePlayerMove(0, 0);
        assertTrue(red.makeComputerMove());
    }
}
