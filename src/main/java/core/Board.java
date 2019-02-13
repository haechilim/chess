package core;

import display.ChessFrame;

public class Board {
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;

    private boolean whiteTurn = true;
    private Cell[][] cells;
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
        cells[2][0].setPiece(new Piece(false, Piece.BISHOP));
        cells[3][0].setPiece(new Piece(false, Piece.QUEEN));
        cells[4][0].setPiece(new Piece(false, Piece.KING));
        cells[5][0].setPiece(new Piece(false, Piece.BISHOP));
        cells[6][0].setPiece(new Piece(false, Piece.KNIGHT));
        cells[7][0].setPiece(new Piece(false, Piece.ROOK));
        cells[0][1].setPiece(new Piece(false, Piece.PAWN));
        cells[1][1].setPiece(new Piece(false, Piece.PAWN));
        cells[2][1].setPiece(new Piece(false, Piece.PAWN));
        cells[3][1].setPiece(new Piece(false, Piece.PAWN));
        cells[4][1].setPiece(new Piece(false, Piece.PAWN));
        cells[5][1].setPiece(new Piece(false, Piece.PAWN));
        cells[6][1].setPiece(new Piece(false, Piece.PAWN));
        cells[7][1].setPiece(new Piece(false, Piece.PAWN));


        cells[0][6].setPiece(new Piece(true, Piece.PAWN));
        cells[1][6].setPiece(new Piece(true, Piece.PAWN));
        cells[2][6].setPiece(new Piece(true, Piece.PAWN));
        cells[3][6].setPiece(new Piece(true, Piece.PAWN));
        cells[4][6].setPiece(new Piece(true, Piece.PAWN));
        cells[5][6].setPiece(new Piece(true, Piece.PAWN));
        cells[6][6].setPiece(new Piece(true, Piece.PAWN));
        cells[7][6].setPiece(new Piece(true, Piece.PAWN));
        cells[0][7].setPiece(new Piece(true, Piece.ROOK));
        cells[1][7].setPiece(new Piece(true, Piece.KNIGHT));
        cells[2][7].setPiece(new Piece(true, Piece.BISHOP));
        cells[3][7].setPiece(new Piece(true, Piece.QUEEN));
        cells[4][7].setPiece(new Piece(true, Piece.KING));
        cells[5][7].setPiece(new Piece(true, Piece.BISHOP));
        cells[6][7].setPiece(new Piece(true, Piece.KNIGHT));
        cells[7][7].setPiece(new Piece(true, Piece.ROOK));

        cells[5][3].setPiece(new Piece(false, Piece.BISHOP));
        cells[7][4].setPiece(new Piece(true, Piece.ROOK));
        cells[3][4].setPiece(new Piece(true, Piece.PAWN));
    }

    private void clearSelected(Cell[][] cells) {
        for(int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                cells[x][y].setSelected(false);
            }
        }
    }

    private void clearMovabled(Cell[][] cells) {
        for(int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                cells[x][y].setMovable(false);
            }
        }
    }

    //  (0) private으로 수정
    //  (0) cell 객체는 불필요하니, piece 객체를 넘겨주도록 수정
    public void markMovableCells(Cell cell, int x, int y) {
        //  (0) cell.getPiece().PAWN -> Cell.PAWN 으로 수정
        if(cell.getPiece().getType() == cell.getPiece().PAWN) markMovableCellsForPawn(cell, x, y);
        else if(cell.getPiece().getType() == cell.getPiece().ROOK) markMovableCellsForRook(cell, x, y);
        else if(cell.getPiece().getType() == cell.getPiece().BISHOP) markMovableCellsForBishop(cell, x, y);
        //  (4) 퀸 구현
        //  (5) 킹 구현
        //  (6) 나이트 구현
    }

    //  (0) cell 객체는 불필요하니, piece 객체를 넘겨주도록 수정
    private void markMovableCellsForPawn(Cell cell, int x, int y) {
        int number = whiteTurn ? -1 : 1;

        //  (2) 양쪽 대각선에 상대방 말이 있으면 이동가능함

        if (cells[x][y + number].isEmpty()) {
            cells[x][y + number].setMovable(true);

            if(!cell.getPiece().isOneTimesMove()) {
                //  (1) 이 자리에 말이 있으면 이동불가능함
                cells[x][y + (2 * number)].setMovable(true);
            }
        }
    }

    //  (0) cell 변수 사용되지 않으니 제거
    private void markMovableCellsForRook(Cell cell, int posX, int posY) {
        setMovableCellsForRook(posX, posY, SOUTH);
        setMovableCellsForRook(posX, posY, NORTH);
        setMovableCellsForRook(posX, posY, WEST);
        setMovableCellsForRook(posX, posY, EAST);
    }

    private void setMovableCellsForRook(int posX, int posY, int direction) {
        if(direction == SOUTH) {
            for(int x = posX - 1; x >= 0; x--) {
                //  (0) 이하 Cell 클래스의 isEmpty() 함수 사용하도록 수정
                if (cells[x][posY].getPiece() != null) break;
                cells[x][posY].setMovable(true);
            }
        }
        else if(direction == NORTH) {
            for(int x = posX + 1; x < 8; x++) {
                if(cells[x][posY].getPiece() != null) break;
                cells[x][posY].setMovable(true);
            }
        }
        else if(direction == WEST) {
            for(int y = posY - 1; y >= 0; y--) {
                if(cells[posX][y].getPiece() != null) break;
                cells[posX][y].setMovable(true);
            }
        }
        else {
            for(int y = posY + 1; y < 8; y++) {
                if(cells[posX][y].getPiece() != null) break;
                cells[posX][y].setMovable(true);
            }
        }
    }

    //  (0) cell 변수 사용되지 않으니 제거
    private void markMovableCellsForBishop(Cell cell, int posX, int posY) {
        //  (3) 다른 말은 하나도 없게 한 다음, 비숍이 (4, 4)에 있는 경우 버그가 있음
        for(int number = 1; number < 8; number++) {
            int movabledX = posX + number;
            int movabledY = posY + number;

            if(movabledX > 7 || movabledY > 7) break;
            if(cells[movabledX][movabledY].getPiece() != null) break;
            cells[movabledX][movabledY].setMovable(true);
        }
        for(int number = 1; number < 8; number++) {
            int movabledX = posX - number;
            int movabledY = posY - number;

            if(movabledX <= 0 || movabledY <= 0) break;
            if(cells[movabledX][movabledY].getPiece() != null) break;
            cells[movabledX][movabledY].setMovable(true);
        }
        for(int number = 1; number < 8; number++) {
            int movabledX = posX - number;
            int movabledY = posY + number;

            if(movabledX <= 0 || movabledY > 7) break;
            if(cells[movabledX][movabledY].getPiece() != null) break;
            cells[movabledX][movabledY].setMovable(true);
        }
        for(int number = 1; number < 8; number++) {
            int movabledX = posX + number;
            int movabledY = posY - number;

            if(movabledX > 7 || movabledY <= 0) break;
            if(cells[movabledX][movabledY].getPiece() != null) break;
            cells[movabledX][movabledY].setMovable(true);
        }
    }

    //  화면에 클릭 이벤트가 발생하면 호출됨
    public void cellClicked(int posX, int posY) {
        Cell cell = cells[posX][posY];
        Piece piece = cell.getPiece();

        //  (7) cell이 movable 표시가 되어 있다면, 현재 선택된 말을 이동시켜줌

        if(piece == null) return;
        if(piece.isWhite() != whiteTurn) return;

        clearSelected(cells);
        clearMovabled(cells);
        cell.setSelected(true);
        markMovableCells(cell, posX, posY);
        chessFrame.redraw();
    }

    public Cell[][] getCells() {
        return cells;
    }
}