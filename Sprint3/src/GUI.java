import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//GUI Window
public class GUI extends JFrame implements ActionListener {
    private Grid grid;
    private GameConfigPanel gameConfigPanel;
    private final GameStateIndicatorPanel gameStateIndicatorPanel;
    private SOSLogic gameConds;
    Player blue;
    Player red;
    int currBoardSize = 3;
    //Initialize GUI window
    public GUI() {
        this.setTitle("449 SOS_Game");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        blue = new Player("blue", 'S');
        red = new Player("red", 'S');
        this.gameConds = new SOSSimpleLogic(blue, red);
        this.grid = new Grid();
        this.gameConfigPanel = new GameConfigPanel();
        this.gameStateIndicatorPanel = new GameStateIndicatorPanel();

        updateGameStateToolTips();

        //Add panels to UI Frame
        this.add(new PlayerConfigPanel(blue), BorderLayout.WEST);
        this.add(new PlayerConfigPanel(red), BorderLayout.EAST);
        this.add(gameStateIndicatorPanel, BorderLayout.SOUTH);
        this.add(gameConfigPanel, BorderLayout.NORTH);
        this.add(grid, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }

    //Action handlers
    public void actionPerformed(ActionEvent e) {
        //TBA
    }

    //handle updating tooltips with respect to current game state
    public void updateGameStateToolTips() {
        if (gameConds.getState() == GameState.PRE) {
            gameStateIndicatorPanel.setToolTipText("Press 'Start Game' when ready.");
        } else if (gameConds.getState() == GameState.BLUE) {
            gameStateIndicatorPanel.setToolTipText("Blue's turn to move.");
        } else if (gameConds.getState() == GameState.RED) {
            gameStateIndicatorPanel.setToolTipText("Red's turn to move.");
        } else if (gameConds.getState() == GameState.BLUEWIN) { // Indicate winner
            gameStateIndicatorPanel.setToolTipText("Blue player wins | Press \"Restart Game\" to play again.");
        } else if (gameConds.getState() == GameState.REDWIN) { // Indicate winner
            gameStateIndicatorPanel.setToolTipText("Red player wins | Press \"Restart Game\" to play again.");
        } else if (gameConds.getState() == GameState.DRAW) {
            gameStateIndicatorPanel.setToolTipText("Draw game | Press \"Restart Game\" to play again.");
        }
    }

    //Grid or Grid UI class
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

            for (int i = 0; i < gameConds.getBoardSize(); i++) {
                for (int j = 0; j < gameConds.getBoardSize(); j++) {
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
            int totalGridSize = gameConds.getBoardSize() * spacing;

            // Calculate the top-left corner position to center the grid
            int xTopLeft = getCenter()[0] - totalGridSize / 2;
            int yTopLeft = getCenter()[1] - totalGridSize / 2;

            this.gridAnchor = new int[]{xTopLeft, yTopLeft};
        }

        //Update grid square scaling
        private void updateSpacing() {
            if (this.getHeight() < this.getWidth()) {
                this.spacing = (this.getHeight() - 20) / gameConds.getBoardSize();
            } else {
                this.spacing = (this.getWidth() - 20) / gameConds.getBoardSize();
            }
        }

        //Handle when player clicks on grid and is making a move
        private void handleMoveClick(int xPos, int yPos) {
            if (!(gameConds.getState() == GameState.RED || gameConds.getState() == GameState.BLUE)) return;
            int[] rowCol = posToRowCol(xPos, yPos);
            boolean isSuccessMove = gameConds.makePlayerMove(rowCol[0], rowCol[1]);
            if (!isSuccessMove) {
                gameStateIndicatorPanel.setToolTipText(String.format("Invalid Move. %s must choose different grid position", (gameConds.getState() == GameState.BLUE) ? "Blue" : "Red"));
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
            for (int row = 0; row < gameConds.getBoardSize(); row++) {
                for (int col = 0; col < gameConds.getBoardSize(); col++) {
                    if (!gameConds.isOccupied(row, col)) continue;
                    int[] pos = rowColToPos(row, col); //https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
                    g.drawString(String.valueOf(gameConds.getBoard()[row][col]), pos[0] + (spacing - fmet.stringWidth(String.valueOf(player.getLetter()))) / 2, pos[1] + ((spacing - fmet.getHeight()) / 2) + fmet.getAscent());
                }
            }
        }
    }

    private class GameStateIndicatorPanel extends JPanel {
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
    //Player config panels on left and right side of window
    public class PlayerConfigPanel extends JPanel implements ActionListener {
        private final JRadioButton humanButton; //Functionality TBA
        private final JRadioButton computerButton; //Functionality TBA
        private final JRadioButton SButton;
        private final JRadioButton OButton;
        private final Player player;

        //Constructor
        public PlayerConfigPanel(Player player) {
            this.player = player;

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setBorder(BorderFactory.createLineBorder(Color.black));
            humanButton = new JRadioButton("human");
            computerButton = new JRadioButton("computer");
            SButton = new JRadioButton("S");
            SButton.addActionListener(this);
            OButton = new JRadioButton("O");
            OButton.addActionListener(this);

            SButton.setSelected(true);
            humanButton.setSelected(true);

            ButtonGroup pType = new ButtonGroup();
            ButtonGroup pLetter = new ButtonGroup();

            JLabel playerLabel = new JLabel(String.format(" %s Player Configurations", player.getColor()));
            JLabel typePrompt = new JLabel("Player Type:");
            JLabel letterPrompt = new JLabel("Player Letter:");

            pType.add(humanButton);
            pType.add(computerButton);
            pLetter.add(SButton);
            pLetter.add(OButton);

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
        //Action handlers
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == SButton) {
                this.player.setLetter('S');
            } if (e.getSource() == OButton) {
                this.player.setLetter('O');
            }
        }
    }
    //Game configuration panels on the top
    public class GameConfigPanel extends JPanel implements ActionListener  {
        private final JButton gameTypeToggler;
        private final JSpinner boardSizeInput;
        private final JButton gameStartRestart;

        //Constructor
        public GameConfigPanel() {
            gameTypeToggler = new JButton((gameConds.isSimple())? "Current Game Type: Simple" : " Current GameType: General");
            gameTypeToggler.addActionListener(this);
            gameStartRestart = new JButton("Start Game");
            gameStartRestart.addActionListener(this);

            //Grid size input
            SpinnerModel model = new SpinnerNumberModel(gameConds.getBoardSize(), 2, 30, 1);
            boardSizeInput = new JSpinner(model);
            boardSizeInput.addChangeListener(e -> {
                gameConds.setBoardSize((Integer) boardSizeInput.getValue());
                currBoardSize = gameConds.getBoardSize();
                grid.updateBoard();
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
            if (e.getSource() == gameTypeToggler && gameConds.isSimple()) {
                gameConds.setSimple(false);
                gameConds = new SOSGeneralLogic(blue, red);
                gameConds.setBoardSize(currBoardSize);
                grid.updateBoard();
                gameTypeToggler.setText("Current Game Type: General");
            } else if (e.getSource() == gameTypeToggler && !gameConds.isSimple()) {
                gameConds.setSimple(true);
                gameConds = new SOSSimpleLogic(blue, red);
                gameConds.setBoardSize(currBoardSize);
                grid.updateBoard();
                gameTypeToggler.setText("Current Game Type: Simple");
                //Start new game  on click
            } else if (e.getSource() == gameStartRestart) {
                gameConds.startGame();
                grid.updateBoard();
                gameStartRestart.setText("Restart Game");
                gameStateIndicatorPanel.setToolTipText("Blue's turn to move.");
                blue.reset();
                red.reset();
            }
        }
    }
}


