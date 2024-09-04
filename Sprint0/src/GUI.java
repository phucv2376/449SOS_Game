import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    Board board;
    JButton b1;
    public GUI(GameConditions gameConds, Player blue, Player red) {
        this.setTitle("449 SOS_Game");
        this.setLayout(new BorderLayout());

        this.add(new player_config_panel((ActionListener) this, blue), BorderLayout.WEST);
        this.add(new player_config_panel((ActionListener) this, red), BorderLayout.EAST);
        board = new Board(gameConds, blue, red);

        this.add(new game_config_panel(gameConds, board), BorderLayout.NORTH);

        this.add(board, BorderLayout.CENTER);
        b1 = new JButton("test");
        this.add(b1, BorderLayout.SOUTH);
        b1.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.add(b1, BorderLayout.SOUTH);
        this.add(new JCheckBox("Temporary checkbox "));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            System.out.println(board.getHeight());
        };
    }
}
