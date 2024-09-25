public class GameConditions {
    boolean isSimple;
    GameState state;
    Player blue;
    Player red;
    int boardSize;

    public GameConditions(Player blue, Player red) {
        this.isSimple = true;
        this.state = GameState.PRE;
        this.blue = blue;
        this.red = red;
        this.boardSize = 5;
    }
}
