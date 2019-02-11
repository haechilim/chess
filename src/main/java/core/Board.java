package core;

import display.ChessFrame;

public class Board {
    private boolean whiteTurn;
    private Cell cells[][];
    private ChessFrame chessFrame;

    public void init() {
        makeCells();
        resetPieces();

        chessFrame = new ChessFrame("해치 체스", this);
        chessFrame.init();
        chessFrame.redraw();
    }

    //  셀 객체(인스턴스)를 만듦
    private void makeCells() {
        cells = new Cell[8][];

        for(int x = 0; x < cells.length; x++) {
            cells[x] = new Cell[8];

            for(int y = 0; y < cells[x].length; y++) {
                cells[x][y] = new Cell();
            }
        }
    }

    //  모든 말(piece)을 최초 자리로 옮김
    private void resetPieces() {
        cells[0][0].setPiece(new Piece(false, Piece.ROOK));
        cells[1][0].setPiece(new Piece(false, Piece.KNIGHT));


        cells[7][6].setPiece(new Piece(true, Piece.PAWN));

        cells[6][7].setPiece(new Piece(true, Piece.KNIGHT));
        cells[7][7].setPiece(new Piece(true, Piece.ROOK));
    }

    private void clearSelected(Cell[][] cells) {
        for(int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                cells[x][y].setSelected(false);
            }
        }
    }

    //  화면에 클릭 이벤트가 발생하면 호출됨
    public void cellClicked(int posX, int posY) {
        Cell cell = cells[posX][posY];

        if(cell.getPiece() == null) return;

        clearSelected(cells);
        cell.setSelected(true);
        chessFrame.redraw();
    }

    public Cell[][] getCells() {
        return cells;
    }
}