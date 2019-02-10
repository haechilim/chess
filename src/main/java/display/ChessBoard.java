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
    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static final String classpath = ChessBoard.class.getResource("/").getPath();
    private static final Color[] colorSelected = { new Color(246, 247, 128), new Color(187, 204, 59) };
    private static final Color[] colorMovable = { new Color(190, 190,167), new Color(93, 120, 66) };

    private Board board;
    private Dimension cellDim;
    private Dimension pieceDim;
    private Dimension circleDim;

    private Image imageBoard;
    private Map<String, Image> imagePiecies;
    private Cell[][] cells;

    public ChessBoard(Board board) {
        this.board = board;
    }

    public void init() {
        imagePiecies = new HashMap<>();
        imageBoard = toolkit.getImage(String.format("%s%s", classpath, "board.png"));

        setLayout(null);
        initDimensions();
        addMouseListener(this);
    }

    public void redraw(Cell[][] cells) {
        this.cells = cells;

        revalidate();
        repaint();
    }

    private void initDimensions() {
        cellDim = new Dimension();
        cellDim.height = getHeight() / 8;
        cellDim.width = cellDim.height;

        pieceDim = new Dimension();
        pieceDim.height = getHeight() / 8;
        pieceDim.width = pieceDim.height;

        circleDim = new Dimension();
        circleDim.height = cellDim.height / 3;
        circleDim.width = circleDim.height;
    }

    private void drawSelected(Graphics graphics, int posX, int posY, Cell cell) {
        if(!cell.isSelected()) return;

        int x = posX * cellDim.width;
        int y = posY * cellDim.height;
        graphics.setColor(colorSelected[colorIndex(posX, posY)]);
        graphics.fillRect(x, y, cellDim.width, cellDim.height);
    }

    private void drawMovable(Graphics graphics, int posX, int posY, Cell cell) {
        if(!cell.isMovable()) return;

        int x = posX * cellDim.width + (cellDim.width/2 - circleDim.width/2);
        int y = posY * cellDim.height + (cellDim.height/2 - circleDim.height/2);
        graphics.setColor(colorMovable[colorIndex(posX, posY)]);
        graphics.fillOval(x, y, circleDim.width, circleDim.height);
    }

    private void drawPiece(Graphics graphics, int posX, int posY, Cell cell) {
        Piece piece = cell.getPiece();
        if(piece == null) return;

        int x = posX * cellDim.width;
        int y = posY * cellDim.height;
        graphics.drawImage(getPieceImage(piece.isWhite(), piece.getType()), x, y, pieceDim.width, pieceDim.height, this);
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
    public void paint(Graphics graphics) {
        graphics.drawImage(imageBoard, 0, 0, getWidth(), getHeight(), this);

        if(cells == null) return;

        for(int y=0; y<cells.length; y++) {
            for(int x=0; x<cells[y].length; x++) {
                drawSelected(graphics, x, y, cells[x][y]);
                drawMovable(graphics, x, y, cells[x][y]);
                drawPiece(graphics, x, y, cells[x][y]);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        board.cellClicked(event.getX() / cellDim.width, event.getY() / cellDim.height);
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}
}