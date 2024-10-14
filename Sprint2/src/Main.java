public class Main {
    public static void main(String[] args) {
        initializeGame();
    }
    public static void initializeGame() {
        GameConditions gameConds = new GameConditions();
        new GUI(gameConds);
    }
}
