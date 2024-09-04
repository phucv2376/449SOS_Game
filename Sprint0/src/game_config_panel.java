import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game_config_panel extends JPanel implements ActionListener  {
    private JButton gameTypeToggler;
    private GameConditions gameConds;
    private JSpinner boardSizeInput;
    private JButton gameStartRestart;

    public game_config_panel(GameConditions gameConds, Board board) {
        this.gameConds = gameConds;

        gameTypeToggler = new JButton((gameConds.isSimple)? "Game Type: Simple " : "GameType: General");
        gameTypeToggler.addActionListener(this);

        gameStartRestart = new JButton("Start Game");
        gameStartRestart.addActionListener(this);

        SpinnerModel model = new SpinnerNumberModel(gameConds.boardSize, 3, 30, 1);

        boardSizeInput = new JSpinner(model);
        boardSizeInput.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameConds.boardSize = (Integer) boardSizeInput.getValue();
                board.updateBoard();
            }
        });

        this.add(gameTypeToggler);
        this.add(new JLabel("Board Size:"));
        this.add(boardSizeInput);
        this.add(gameStartRestart);

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTypeToggler && gameConds.isSimple) {
            gameConds.isSimple = false;
            gameTypeToggler.setText("Game Type: Simple");
        } else if (e.getSource() == gameTypeToggler && !gameConds.isSimple) {
            gameConds.isSimple = true;
            gameTypeToggler.setText("Game Type: General");
        } else if (e.getSource() == gameStartRestart && gameConds.state == GameState.PRE) {
            gameConds.state = GameState.BLUE;
            gameStartRestart.setText("Restart Game");
        }
    }
}
