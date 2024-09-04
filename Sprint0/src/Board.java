import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;


public class Board extends JPanel {
    private GameConditions gameConds;
    private Player blue;
    private Player red;
    int spacing = 100;
    int[] gridAnchor;


    public Board(GameConditions gameConds, Player blue, Player red) {
        this.gameConds = gameConds;
        this.blue = blue;
        this.red = red;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                onClick(e.getX(), e.getY());
            }
        });
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateSpacing();
        updateGridTopLeft();

        for (int i = 0; i < gameConds.boardSize; i++) {
            for (int j = 0; j < gameConds.boardSize; j++) {
                g.drawRect(gridAnchor[0] + spacing * i, gridAnchor[1] + spacing * j, spacing, spacing);
            }
        }
        g.setFont(new Font("Arial", Font.PLAIN, spacing - 10));
        FontMetrics fmet = g.getFontMetrics();

        //Render Blue Player Moves
        renderMoves(g, fmet, blue);

        renderMoves(g, fmet, red);
    }


    public void updateBoard() {
        repaint();
    }
    private int[] getCenter() {
        return(new int[]{this.getWidth() / 2, this.getHeight() / 2});
    }
    private void updateGridTopLeft() {
        int totalGridWidth = gameConds.boardSize * spacing;
        int totalGridHeight = gameConds.boardSize * spacing;

        // Calculate the top-left corner position to center the grid
        int xTopLeft = getCenter()[0] - totalGridWidth / 2;
        int yTopLeft = getCenter()[1] - totalGridHeight / 2;

        this.gridAnchor = new int[]{xTopLeft, yTopLeft};
    }
    private void updateSpacing() {
        if (this.getHeight() < this.getWidth()) {
            this.spacing = (this.getHeight() - 20) / gameConds.boardSize;
        } else {
            this.spacing = (this.getWidth() - 20) / gameConds.boardSize;
        }
    }
    private void onClick(int xPos, int yPos) {
        int[] rowCol = posToRowCol(xPos, yPos);
        if (gameConds.state == GameState.BLUE) {
            blue.addMove(rowCol);
        } else if (gameConds.state == GameState.RED) {
            red.addMove(rowCol);
        }
        repaint();
    }
    private int[] rowColToPos(int row, int col) { //row col start from 0
        int x = col * spacing + gridAnchor[0];
        int y = row * spacing + gridAnchor[1];
        System.out.println(x + " " + y);
        return new int[] {x, y};
    }
    private int[] posToRowCol(int xPos, int yPos) {
        int row = (yPos - gridAnchor[1]) / spacing;
        int col = (xPos - gridAnchor[0]) / spacing;

        return new int[] {row, col};
    }
    private void renderMoves(Graphics g, FontMetrics fmet, Player player) {
        g.setColor((Objects.equals(player.getColor(), "Blue")) ? Color.blue : Color.red);
        for (int[] move: player.getPrevMoves()) {
            int[] pos = rowColToPos(move[0], move[1]); //https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
            g.drawString(String.valueOf(player.getLetter()), pos[0] + (spacing -  fmet.stringWidth(String.valueOf(player.getLetter()))) / 2, pos[1] + ((spacing - fmet.getHeight()) / 2) + fmet.getAscent());
        }
    }
}
