import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MainTest {

    @Test
    void isPosTestTrue() {
        Boolean r = Main.isPos(2);
        Assertions.assertEquals(true, r);
    }

    @Test
    void isPosTestFalse() {
        Boolean r = Main.isPos(-2);
        Assertions.assertEquals(false, r);
    }

    @Test
    void isEvenTestTrue() {
        Boolean r = Main.isEven(4);
        Assertions.assertEquals(true, r);
    }

    @Test
    void isEvenTestFalse() {
        Boolean r = Main.isEven(3);
        Assertions.assertEquals(false, r);
    }
}