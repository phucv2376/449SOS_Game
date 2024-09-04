public class Main {
    public static void main(String[] args) {
        Player blue = new Player("Blue", 'S');
        Player red = new Player("Red", 'O');

        GameConditions gameConds = new GameConditions(blue, red);

          GUI gui = new GUI(gameConds, blue, red);
    }

    public static Boolean isPos(int x) {
        return x > 0;
    }

    public static Boolean isEven(int x) {
        return x % 2 == 0;
    }
}
