import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class player_config_panel extends JPanel {
    private JRadioButton humanButton;
    private JRadioButton computerButton;
    private JRadioButton SButton;
    private JRadioButton OButton;

    public player_config_panel(ActionListener actionListener, Player player) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        humanButton = new JRadioButton("human");
        computerButton = new JRadioButton("computer");
        SButton = new JRadioButton("S");
        OButton = new JRadioButton("O");

        ButtonGroup pType = new ButtonGroup();
        ButtonGroup pLetter = new ButtonGroup();

        LayoutManager layout = new FlowLayout();

        JLabel playerLabel = new JLabel(String.format(" %s Player Configurations", player.getColor()));
        JLabel typePrompt = new JLabel("Player Type:");
        JLabel letterPrompt = new JLabel("Player Letter:");

        pType.add(humanButton);
        pType.add(computerButton);
        pLetter.add(SButton);
        pLetter.add(OButton);

        this.add(playerLabel);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(typePrompt);
        this.add(humanButton);
        this.add(computerButton);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(letterPrompt);
        this.add(SButton);
        this.add(OButton);

    }
    public void actionPerformed(ActionEvent e) {
    }
}