import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComputerPlayerSimpleTest {
    @Test
    void testSimpleBlueComputerPlayer() {
        Player blue = new ComputerPlayerSimpleLogic("blue", 'S');
        Player red = new Player("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        assertTrue(blue.makeComputerMove());
    }
    @Test
    void testSimpleRedComputerPlayer() {
        Player blue = new Player("blue", 'S');
        Player red = new ComputerPlayerSimpleLogic("red", 'S');
        SOSLogic logic = new SOSSimpleLogic(blue, red);
        logic.startGame();
        logic.makePlayerMove(0, 0);
        assertTrue(red.makeComputerMove());
    }
}
