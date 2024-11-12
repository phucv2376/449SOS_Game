import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

//GUI Window
public class GUI extends JFrame implements ActionListener {
    private final Grid grid;
    private final GameStateIndicatorPanel gameStateIndicatorPanel;
    private SOSLogic logic;
    Player blue;
    Player red;
    SOSConfigs configs = new SOSConfigs();
    PlayerConfigPanel blueConfigPanel;
    PlayerConfigPanel redConfigPanel;
    //Initialize GUI window
    public GUI() {
        this.setTitle("449 SOS_Game");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initialize players strictly for panel change visuals
        blue = new Player("blue", 'S');
        red = new Player("red", 'S');

        //Initialize game logic strictly for grid change visuals
        this.logic = new SOSSimpleLogic(blue, red);

        //Initialize GUI components
        this.grid = new Grid();
        GameConfigPanel gameConfigPanel = new GameConfigPanel();
        this.gameStateIndicatorPanel = new GameStateIndicatorPanel();
        blueConfigPanel = new PlayerConfigPanel(blue);
        redConfigPanel = new PlayerConfigPanel(red);


        //Add panels to UI Frame
        this.add(blueConfigPanel, BorderLayout.WEST);
        this.add(redConfigPanel, BorderLayout.EAST);
        this.add(gameStateIndicatorPanel, BorderLayout.SOUTH);
        this.add(gameConfigPanel, BorderLayout.NORTH);
        this.add(grid, BorderLayout.CENTER);

        updateGameStateToolTips();
        this.pack();
        this.setVisible(true);
    }

    //Action handlers
    public void actionPerformed(ActionEvent e) {
        //TBA
    }
    public void applyConfigurations() { //Apply user specified configurations at game start
        //Initialize players with respect to player types configs
        if (configs.isSimple) {
            this.blue = configs.isBlueHuman ? new Player("blue", 'S') : new ComputerPlayerSimpleLogic("blue", 'S');
            this.red = configs.isRedHuman ? new Player("red", 'S') : new ComputerPlayerSimpleLogic("red", 'S');
        } else {
            this.blue = configs.isBlueHuman ? new Player("blue", 'S') : new ComputerPlayerGeneralLogic("blue", 'S');
            this.red = configs.isRedHuman ? new Player("red", 'S') : new ComputerPlayerGeneralLogic("red", 'S');
        }

        blue.setLetter(configs.blueLetter);
        red.setLetter(configs.redLetter);

        blueConfigPanel.setPlayer(blue);
        redConfigPanel.setPlayer(red);

        //Initialize gamelogic respective to game mode configs
        if (configs.isSimple)
            this.logic = new SOSSimpleLogic(blue, red);
        else
            this.logic = new SOSGeneralLogic(blue, red);

        blue.setLogic(this.logic);
        red.setLogic(this.logic);

        this.logic.setBoardSize(configs.currBoardSize); //apply configured board size
    }

    //handle updating tooltips with respect to current game state
    public void updateGameStateToolTips() {
        if (logic.getState() == GameState.PRE) {
            gameStateIndicatorPanel.setToolTipText("Press 'Start Game' when ready.");
        } else if (logic.getState() == GameState.BLUE) {
            gameStateIndicatorPanel.setToolTipText("Blue's turn to move.");
        } else if (logic.getState() == GameState.RED) {
            gameStateIndicatorPanel.setToolTipText("Red's turn to move.");
        } else if (logic.getState() == GameState.BLUEWIN) { // Indicate winner
            gameStateIndicatorPanel.setToolTipText("Blue player wins | Press \"Restart Game\" to play again.");
        } else if (logic.getState() == GameState.REDWIN) { // Indicate winner
            gameStateIndicatorPanel.setToolTipText("Red player wins | Press \"Restart Game\" to play again.");
        } else if (logic.getState() == GameState.DRAW) {
            gameStateIndicatorPanel.setToolTipText("Draw game | Press \"Restart Game\" to play again.");
        }
    }

    //Grid UI class
    public class Grid extends JPanel {
        private int spacing = 100; //initial spacing 100
        private int[] gridAnchor;

        public Grid() {
            //Listen and handle mouse click on grid
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    handleMoveClick(e.getX(), e.getY());
                }
            });
        }

        // Paint grid
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            updateSpacing(); //Recalculate grid positioning and scale
            updateGridTopLeft();

            for (int i = 0; i < logic.getBoardSize(); i++) {
                for (int j = 0; j < logic.getBoardSize(); j++) {
                    g.drawRect(gridAnchor[0] + spacing * i, gridAnchor[1] + spacing * j, spacing, spacing);
                }
            }
            g.setFont(new Font("Arial", Font.PLAIN, spacing - 10));
            FontMetrics fmet = g.getFontMetrics();

            //Render Player Moves
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10));

            renderMoves(g, fmet, blue);
            renderMoves(g, fmet, red);
            renderCompleteSequence(g2);
        }

        public void renderCompleteSequence(Graphics2D g2) {
            for (int[][] sequence: blue.getCompletedSequences()) {
                int[] rowColBegin= getCellCenter(sequence[0][0], sequence[0][1]);
                int[] rowColEnd= getCellCenter(sequence[2][0], sequence[2][1]);

                g2.setColor(Color.BLUE);
                g2.drawLine(rowColBegin[0], rowColBegin[1], rowColEnd[0], rowColEnd[1]);
            }
            for (int[][] sequence: red.getCompletedSequences()) {
                int[] rowColBegin= getCellCenter(sequence[0][0], sequence[0][1]);
                int[] rowColEnd= getCellCenter(sequence[2][0], sequence[2][1]);

                g2.setColor(Color.RED);
                g2.drawLine(rowColBegin[0], rowColBegin[1], rowColEnd[0], rowColEnd[1]);
            }
        }

        //Rerender grid grid
        public void updateBoard() {
            this.repaint();
        }

        //Get center of grid
        private int[] getCenter() {
            return (new int[]{this.getWidth() / 2, this.getHeight() / 2});
        }
        private int[] getCellCenter(int row,int col) {
            int cellRelativeCenter = this.spacing / 2;
            int[] rowCol = rowColToPos(row, col);
            return new int[]{rowCol[0] + cellRelativeCenter, rowCol[1] + cellRelativeCenter};
        }

        //Update grid top left x and y positioning
        private void updateGridTopLeft() {
            int totalGridSize = logic.getBoardSize() * spacing;

            // Calculate the top-left corner position to center the grid
            int xTopLeft = getCenter()[0] - totalGridSize / 2;
            int yTopLeft = getCenter()[1] - totalGridSize / 2;

            this.gridAnchor = new int[]{xTopLeft, yTopLeft};
        }

        //Update grid square scaling
        private void updateSpacing() {
            if (this.getHeight() < this.getWidth()) {
                this.spacing = (this.getHeight() - 20) / logic.getBoardSize();
            } else {
                this.spacing = (this.getWidth() - 20) / logic.getBoardSize();
            }
        }

        //Handle when player clicks on grid and is making a move
        private void handleMoveClick(int xPos, int yPos) {
            if (!(logic.getState() == GameState.RED || logic.getState() == GameState.BLUE)) return;
            int[] rowCol = posToRowCol(xPos, yPos);
            boolean isSuccessMove = logic.makePlayerMove(rowCol[0], rowCol[1]);
            if (!isSuccessMove) {
                gameStateIndicatorPanel.setToolTipText(String.format("Invalid Move. %s must choose different grid position", (logic.getState() == GameState.BLUE) ? "Blue" : "Red"));
                return;
            }
            updateGameStateToolTips();
            repaint();
        }

        //Translate row and column into window x,y positions
        private int[] rowColToPos(int row, int col) { //row col start from 0
            int x = col * spacing + gridAnchor[0];
            int y = row * spacing + gridAnchor[1];
            return new int[]{x, y};
        }

        //Translate x,y positions into row and column
        private int[] posToRowCol(int xPos, int yPos) {
            int row = (yPos - gridAnchor[1]) / spacing;
            int col = (xPos - gridAnchor[0]) / spacing;
            return new int[]{row, col};
        }

        //render moves made by Player
        private void renderMoves(Graphics g, FontMetrics fmet, Player player) {
//        g.setColor((Objects.equals(player.getColor(), "Blue")) ? Color.blue : Color.red);
            for (int row = 0; row < logic.getBoardSize(); row++) {
                for (int col = 0; col < logic.getBoardSize(); col++) {
                    if (!logic.isOccupied(row, col)) continue;
                    int[] pos = rowColToPos(row, col); //https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
                    g.drawString(String.valueOf(logic.getBoard()[row][col]), pos[0] + (spacing - fmet.stringWidth(String.valueOf(player.getLetter()))) / 2, pos[1] + ((spacing - fmet.getHeight()) / 2) + fmet.getAscent());
                }
            }
        }
    }

    //Bottom center tool tip panel
    private static class GameStateIndicatorPanel extends JPanel {
        private final JLabel gameStateToolTips;
        //Constructor
        public GameStateIndicatorPanel() {
            gameStateToolTips = new JLabel("");
            this.add(gameStateToolTips);
        }

        //tooltip setter
        public void setToolTipText(String text) {
            gameStateToolTips.setText(text);
        }
    }

    //Player config UI panels on left and right side of window
    public class PlayerConfigPanel extends JPanel implements ActionListener {
        private final JRadioButton humanButton; //Functionality TBA
        private final JRadioButton computerButton; //Functionality TBA
        private final JRadioButton SButton;
        private final JRadioButton OButton;
        private Player player;

        //Constructor
        public PlayerConfigPanel(Player player) {
            this.player = player;
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//            this.setBorder(BorderFactory.createLineBorder(Color.black));
            this.setBorder(new EmptyBorder(10, 20, 10, 20)); // Adds 10px padding on all sides
            this.setBackground(Color.lightGray);
            this.setOpaque(false);

            humanButton = new JRadioButton("human");
            computerButton = new JRadioButton("computer");
            SButton = new JRadioButton("S");
            OButton = new JRadioButton("O");

            humanButton.addActionListener(this);
            computerButton.addActionListener(this);
            SButton.addActionListener(this);
            OButton.addActionListener(this);

            SButton.setSelected(true);
            humanButton.setSelected(true);

            ButtonGroup pType = new ButtonGroup();
            ButtonGroup pLetter = new ButtonGroup();

            pType.add(humanButton);
            pType.add(computerButton);
            pLetter.add(SButton);
            pLetter.add(OButton);

            JLabel playerLabel = new JLabel(String.format("%s Player Configs", player.getColor().toUpperCase()));
            JLabel typePrompt = new JLabel("Player Type:");
            JLabel letterPrompt = new JLabel("Player Letter:");

            //Add components to panel
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
        @Override
        protected void paintComponent(Graphics g) { //Rounded corners on panel
            int cornerRadius = 20;
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius); // Draw rounded rectangle
        }
        //Action handlers
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == SButton) {
                this.player.setLetter('S');
                if (Objects.equals(player.getColor(), "blue")) //Save to configs
                    configs.blueLetter = player.getLetter();
                else
                    configs.redLetter = player.getLetter();
            } if (e.getSource() == OButton) {
                this.player.setLetter('O');
                if (Objects.equals(player.getColor(), "blue")) //Save to configs
                    configs.blueLetter = player.getLetter();
                else
                    configs.redLetter = player.getLetter();
            } if (e.getSource() == humanButton) {
                if (Objects.equals(player.getColor(), "blue")) { //Save to configs
                    configs.isBlueHuman = true;
                } else if (Objects.equals(player.getColor(), "red")) { //Save to configs
                    configs.isRedHuman = true;
                }
                OButton.setEnabled(true);
                SButton.setEnabled(true);
            } if (e.getSource() == computerButton) {
                if (Objects.equals(player.getColor(), "blue")) {
                    configs.isBlueHuman = false;
                } else if (Objects.equals(player.getColor(), "red")) {
                    configs.isRedHuman = false;
                }
                OButton.setEnabled(false);
                SButton.setEnabled(false);
            }
        }
        public void setPlayer(Player player) {
            this.player = player;
        }
    }

    //Game configuration UI panels on the top
    public class GameConfigPanel extends JPanel implements ActionListener  {
        private final JButton gameTypeToggler;
        private final JSpinner boardSizeInput;
        private final JButton gameStartRestart;

        //Constructor
        public GameConfigPanel() {
            gameTypeToggler = new JButton((logic.isSimple())? "Current Game Type: Simple" : " Current GameType: General");
            gameTypeToggler.addActionListener(this);
            gameStartRestart = new JButton("Start Game");
            gameStartRestart.addActionListener(this);

            //Grid size input
            SpinnerModel model = new SpinnerNumberModel(logic.getBoardSize(), 2, 30, 1);
            boardSizeInput = new JSpinner(model);
            boardSizeInput.addChangeListener(e -> { //Listen for board size change and update UI grid size
                logic.setBoardSize((Integer) boardSizeInput.getValue());
                configs.currBoardSize = logic.getBoardSize();
                grid.updateBoard();
                blue.reset();
                red.reset();
            });

            //Add components to panel
            this.add(gameTypeToggler);
            this.add(new JLabel("Grid Size:"));
            this.add(boardSizeInput);
            this.add(gameStartRestart);
        }
        //Action handlers
        public void actionPerformed(ActionEvent e) {
            //Change game type toggle and its button label on click
            if (e.getSource() == gameTypeToggler && configs.isSimple) {
                configs.isSimple = false;
                gameTypeToggler.setText("Current Game Type: General");
            } else if (e.getSource() == gameTypeToggler && !configs.isSimple) {
                configs.isSimple = true;
                gameTypeToggler.setText("Current Game Type: Simple");
                //Start new game  on click
            } else if (e.getSource() == gameStartRestart) {
                applyConfigurations();
                grid.updateBoard();
                logic.startGame();
                gameStartRestart.setText("Restart Game");
                updateGameStateToolTips();
            }
        }
    }
}

