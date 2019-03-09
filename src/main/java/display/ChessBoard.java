package display;

import core.Board;
import core.Cell;
import core.Piece;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class ChessBoard extends Panel implements MouseListener {
    private static final String classpath = ChessBoard.class.getResource("/").getPath();
    private static final Color[] colorSelected = { new Color(246, 247, 128), new Color(187, 204, 59) };
    private static final Color[] colorMovable = { new Color(190, 190,167), new Color(93, 120, 66) };

    private Board board;
    private Cell[][] cells;
    private double cellSize;
    private double circleSize;

    private Image offscreen;
    private Image imageBoard;
    private Map<String, Image> imagePiecies;

    public ChessBoard(Board board) {
        this.board = board;
        this.cells = board.getCells();
    }

    public void init() {
        imagePiecies = new HashMap<>();
        imageBoard = Util.loadImage(String.format("%s%s", classpath, "board.png"));

        setLayout(null);
        initDimensions();
        addMouseListener(this);
    }

    public void redraw() {
        if(offscreen == null) offscreen = createImage(getWidth(), getHeight());

        Graphics graphics = offscreen.getGraphics();
        graphics.drawImage(imageBoard, 0, 0, getWidth(), getHeight(), this);

        if(cells == null) return;

        for(int y=0; y<cells.length; y++) {
            for(int x=0; x<cells[y].length; x++) {
                drawSelected(graphics, x, y, cells[x][y]);
                drawMovable(graphics, x, y, cells[x][y]);
                drawPiece(graphics, x, y, cells[x][y]);
            }
        }

        revalidate();
        repaint();
    }

    private void initDimensions() {
        cellSize = getHeight() / 8.0;
        circleSize = cellSize / 3.0;
    }

    private void drawSelected(Graphics graphics, int posX, int posY, Cell cell) {
        if(!cell.isSelected()) return;

        int x = (int)(posX * cellSize);
        int y = (int)(posY * cellSize);
        graphics.setColor(colorSelected[colorIndex(posX, posY)]);
        graphics.fillRect(x, y, (int)cellSize + 1, (int)cellSize + 1);
    }

    private void drawMovable(Graphics graphics, int posX, int posY, Cell cell) {
        if(!cell.isMovable()) return;

        int x = posX * (int)cellSize + ((int)cellSize/2 - (int)circleSize/2);
        int y = posY * (int)cellSize + ((int)cellSize/2 - (int)circleSize/2);
        graphics.setColor(colorMovable[colorIndex(posX, posY)]);
        graphics.fillOval(x, y, (int)circleSize, (int)circleSize);
    }

    private void drawPiece(Graphics graphics, int posX, int posY, Cell cell) {
        Piece piece = cell.getPiece();
        if(piece == null) return;

        int x = posX * (int)cellSize;
        int y = posY * (int)cellSize;
        graphics.drawImage(getPieceImage(piece.isWhite(), piece.getType()), x, y, (int)cellSize, (int)cellSize, this);
    }

    private Image getPieceImage(boolean white, int type) {
        String key = ChessPiece.getKey(white, type);
        Image image = imagePiecies.get(key);

        if(image == null) {
            image = ChessPiece.getPiece(white, type);
            imagePiecies.put(key, image);
        }

        return image;
    }

    private int colorIndex(int posX, int posY) {
        boolean second = posX % 2 == 1;
        if(posY % 2 == 1) second = !second;
        return second ? 1 : 0;
    }

    @Override
    public void update(Graphics graphics) {
        paint(graphics);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(offscreen, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {
        board.cellClicked(event.getX() / (int)cellSize, event.getY() / (int)cellSize);
    }

    @Override
    public void mouseClicked(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}
}