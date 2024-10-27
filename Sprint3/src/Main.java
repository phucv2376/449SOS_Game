public class Main {
    public static void main(String[] args) {
        initializeGame();
    }
    public static void initializeGame() {
        SOSLogic gameConds = new SOSSimpleLogic();
        new GUI(gameConds);
    }
}
