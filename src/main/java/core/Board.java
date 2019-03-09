package core;

import display.ChessFrame;

public class Board {
    private Cell[][] cells;
    private boolean whiteTurn = true;
    private Cell selectedCell;
    private ChessFrame chessFrame;

    public void init() {
        makeCells();
        resetPiecePositions();

        chessFrame = new ChessFrame("해치 체스",this);
        chessFrame.init();
        chessFrame.redraw();
    }

    private void makeCells() {
        cells = new Cell[8][];

        for(int x = 0; x < cells.length; x++) {
            cells[x] = new Cell[8];

            for(int y = 0; y < cells[x].length; y++) {
                cells[x][y] = new Cell();
            }
        }
    }

    private void resetPiecePositions() {
        cells[0][0].setPiece(false, Piece.ROOK);
        cells[1][0].setPiece(false, Piece.KNIGHT);
        cells[2][0].setPiece(false, Piece.BISHOP);
        cells[3][0].setPiece(false, Piece.QUEEN);
        cells[4][0].setPiece(false, Piece.KING);
        cells[5][0].setPiece(false, Piece.BISHOP);
        cells[6][0].setPiece(false, Piece.KNIGHT);
        cells[7][0].setPiece(false, Piece.ROOK);
        cells[0][1].setPiece(false, Piece.PAWN);
        cells[1][1].setPiece(false, Piece.PAWN);
        cells[2][1].setPiece(false, Piece.PAWN);
        cells[3][1].setPiece(false, Piece.PAWN);
        cells[4][1].setPiece(false, Piece.PAWN);
        cells[5][1].setPiece(false, Piece.PAWN);
        cells[6][1].setPiece(false, Piece.PAWN);
        cells[7][1].setPiece(false, Piece.PAWN);
        cells[0][6].setPiece(true, Piece.PAWN);
        cells[1][6].setPiece(true, Piece.PAWN);
        cells[2][6].setPiece(true, Piece.PAWN);
        cells[3][6].setPiece(true, Piece.PAWN);
        cells[4][6].setPiece(true, Piece.PAWN);
        cells[5][6].setPiece(true, Piece.PAWN);
        cells[6][6].setPiece(true, Piece.PAWN);
        cells[7][6].setPiece(true, Piece.PAWN);
        cells[0][7].setPiece(true, Piece.ROOK);
        cells[1][7].setPiece(true, Piece.KNIGHT);
        cells[2][7].setPiece(true, Piece.BISHOP);
        cells[3][7].setPiece(true, Piece.QUEEN);
        cells[4][7].setPiece(true, Piece.KING);
        cells[5][7].setPiece(true, Piece.BISHOP);
        cells[6][7].setPiece(true, Piece.KNIGHT);
        cells[7][7].setPiece(true, Piece.ROOK);
    }

    private void markSelectedCell(Cell cell) {
        if(cell.getPiece() == null) return;
        if(cell.getPiece().isWhite() != whiteTurn) return;

        clearSelectedCell();
        cell.setselected(true);
        selectedCell = cell;
    }

    private void markMovable(Cell cell, int posX, int posY) {
        if(cell.getPiece() == null) return;
        if(cell.getPiece().isWhite() != whiteTurn) return;

        clearMovable(cells);

        switch(cell.getPiece().getType()) {
            case Piece.PAWN:
                markMovablePawn(cell, posX, posY);
                break;

            case Piece.ROOK:
                markMovableRook(posX, posY);
                break;

            case Piece.KNIGHT:
                markMovableKnight(posX, posY);
                break;

            case Piece.BISHOP:
                markMovableBishop(posX, posY);
                break;

            case Piece.QUEEN:
                markMovableQueen(posX, posY);
                break;

            case Piece.KING:
                markMovableKing(posX, posY);
                break;
        }
    }

    private void markMovablePawn(Cell cell, int posX, int posY) {
        int offset = whiteTurn ? -1 : 1;

        if(cells[posX][posY + offset].getPiece() == null) {
            cells[posX][posY + offset].setMovable(true);

            if(!cell.getPiece().isMoved() && cells[posX][posY + offset * 2].getPiece() == null) {
                cells[posX][posY + (offset * 2)].setMovable(true);
            }
        }
        if(isValidIndex(posX + 1, posY + offset) && cells[posX + 1][posY + offset].existsOppositePiece(whiteTurn)) {
            cells[posX + 1][posY + offset].setMovable(true);
        }
        if(isValidIndex(posX - 1, posY + offset) && cells[posX - 1][posY + offset].existsOppositePiece(whiteTurn)) {
            cells[posX - 1][posY + offset].setMovable(true);
        }
        if(isValidIndex(posX + 1, posY + offset) && cells[posX + 1][posY].existsPiece()
                && cells[posX + 1][posY].getPiece().isEnpassantable()) {
            cells[posX + 1][posY + offset].setMovable(true);
        }
        if(isValidIndex(posX - 1, posY + offset) && cells[posX - 1][posY].existsPiece()
                && cells[posX - 1][posY].getPiece().isEnpassantable()) {
            cells[posX - 1][posY + offset].setMovable(true);
        }
    }

    private void markMovableRook(int posX, int posY) {
        for(int number = 1;; number++) {
            if(!markMovableCell(posX, posY + number)) break;
            if(holdavle(posX, posY + number)) break;
        }
        for(int number = 1;; number++) {
            if(!markMovableCell(posX, posY - number)) break;
            if(holdavle(posX, posY - number)) break;
        }
        for(int number = 1;; number++) {
            if(!markMovableCell(posX + number, posY)) break;
            if(holdavle(posX + number, posY)) break;
        }
        for(int number = 1;; number++) {
            if(!markMovableCell(posX - number, posY)) break;
            if(holdavle(posX - number, posY)) break;
        }
    }

    private void markMovableKnight(int posX, int posY) {
        markMovableCell(posX + 1, posY - 2);
        markMovableCell(posX + 2, posY - 1);
        markMovableCell(posX + 2, posY + 1);
        markMovableCell(posX + 1, posY + 2);
        markMovableCell(posX - 1, posY + 2);
        markMovableCell(posX - 2, posY + 1);
        markMovableCell(posX - 2, posY - 1);
        markMovableCell(posX - 1, posY - 2);
    }

    private void markMovableBishop(int posX, int posY) {
        for(int number = 1;; number++) {
            if(!markMovableCell(posX + number, posY + number)) break;
            if(holdavle(posX + number, posY + number)) break;
        }
        for(int number = 1;; number++) {
            if(!markMovableCell(posX + number, posY - number)) break;
            if(holdavle(posX + number, posY - number)) break;
        }
        for(int number = 1;; number++) {
            if(!markMovableCell(posX - number, posY - number)) break;
            if(holdavle(posX - number, posY - number)) break;
        }
        for(int number = 1;; number++) {
            if(!markMovableCell(posX - number, posY + number)) break;
            if(holdavle(posX - number, posY + number)) break;
        }
    }

    private void markMovableQueen(int posX, int posY) {
        markMovableRook(posX, posY);
        markMovableBishop(posX, posY);
    }

    private void markMovableKing(int posX, int posY) {
        markMovableCell(posX, posY - 1);
        markMovableCell(posX + 1, posY - 1);
        markMovableCell(posX + 1, posY);
        markMovableCell(posX + 1, posY + 1);
        markMovableCell(posX, posY + 1);
        markMovableCell(posX - 1, posY + 1);
        markMovableCell(posX - 1, posY);
        markMovableCell(posX - 1, posY - 1);
    }

    private boolean markMovableCell(int x, int y) {
        if(isValidIndex(x, y) && movableCell(x, y)) {
            cells[x][y].setMovable(true);
            return true;
        }

        return false;
    }

    private boolean holdavle(int x, int y) {
        if(isValidIndex(x, y) && cells[x][y].existsPiece()) {
            return cells[x][y].getPiece().isWhite() != whiteTurn ? true : false;
        }

        return false;
    }

    private void promotion(Cell cell, int posY) {
        if(cell.getPiece().getType() == Piece.PAWN && (posY == 0 || posY == 7)) {
            cell.getPiece().setType(Piece.QUEEN);
        }
    }

    private void markCastling(int posX, int posY) {
        if(selectedCell == null) return;

        Piece selectedPiece = selectedCell.getPiece();

        if(selectedPiece != null && selectedPiece.getType() == Piece.KING && !selectedPiece.isMoved()) {
            if(cells[posX + 3][posY].existsPiece() && !cells[posX + 3][posY].getPiece().isMoved()) {
                if(cells[posX + 1][posY].getPiece() == null && cells[posX + 2][posY].getPiece() == null) {
                    cells[posX + 2][posY].setCastlingable(true);
                    cells[posX + 2][posY].setMovable(true);
                }
            }
            if(cells[posX - 4][posY].existsPiece() && !cells[posX - 4][posY].getPiece().isMoved()) {
                if(cells[posX - 1][posY].getPiece() == null && cells[posX - 2][posY].getPiece() == null && cells[posX - 3][posY].getPiece() == null) {
                    cells[posX - 2][posY].setCastlingable(true);
                    cells[posX - 2][posY].setMovable(true);
                }
            }
        }
    }

    private void setEnpassantable(int posX) {
        Piece piece = selectedCell.getPiece();

        if(piece.getType() == Piece.PAWN && !piece.isMoved()) {
            if(posX == 3 || posX == 4) {
                piece.setEnpassantable(true);
            }
        }
    }

    private boolean move(Cell cell, int posX, int posY) {
        if(cell.isMovable()) {
            setEnpassantable(posX);
            moveRookForCastling(cell, posX, posY);
            //moveEnpassant(posX, posY);
            movePiece(selectedCell, cell);
            return true;
        }

        return false;
    }

    private void moveRookForCastling(Cell cell, int posX, int posY) {
        if(cell.isCastlingable()) {
            if(posX > 4) movePiece(cells[7][posY], cells[5][posY]);
            else if(posX < 4) movePiece(cells[0][posY], cells[3][posY]);

            clearCastlingable(posY);
        }
    }

    private void moveEnpassant(int posX, int posY) {
        int offset = whiteTurn ? -1 : 1;

        if(selectedCell.getPiece().getType() == Piece.PAWN) {
            Cell cell = cells[posX][posY + offset];
            if(cell.existsPiece() && cell.getPiece().isEnpassantable()) cell.clearPiece();
        }
    }

    private void movePiece(Cell source, Cell target) {
        source.getPiece().setMoved(true);
        target.setPiece(source.getPiece());
        source.clearPiece();
    }

    private void clearCastlingable(int posY) {
        cells[2][posY].setCastlingable(false);
        cells[6][posY].setCastlingable(false);
    }

    private void clearEnpassant() {
        for(int x = 0; x < cells.length; x++) {
            for(int y = 0; y < cells[x].length; y++) {
                if(cells[x][y].existsPiece() && cells[x][y].getPiece().isWhite() == whiteTurn) {
                    cells[x][y].getPiece().setEnpassantable(false);
                }
            }
        }
    }

    private void clearMovable(Cell[][] cells) {
        for(int x = 0; x < cells.length; x++) {
            for(int y = 0; y < cells[x].length; y++) {
                cells[x][y].setMovable(false);
            }
        }
    }

    private void clearSelectedCell() {
        for(int x = 0; x < cells.length; x++) {
            for(int y = 0; y < cells[x].length; y++) {
                cells[x][y].setselected(false);
            }
        }
    }

    private boolean isValidIndex(int posX, int posY) {
        return (posX < 8 && posX >= 0) && (posY < 8 && posY >= 0) ? true : false;
    }

    private boolean movableCell(int posX, int posY) {
        Piece piece = cells[posX][posY].getPiece();

        return piece == null || piece.isWhite() != whiteTurn ? true : false;
    }

    //  화면에 클릭 이벤트가 발생하면 호출됨
    public void cellClicked(int posX, int posY) {
        Cell cell = cells[posX][posY];

        if(move(cell, posX, posY)) {
            promotion(cell, posY);

            clearMovable(cells);
            clearSelectedCell();
            //clearEnpassant();
            whiteTurn = !whiteTurn;
            chessFrame.redraw();
            return;
        }

        markSelectedCell(cell);
        markMovable(cell, posX, posY);
        markCastling(posX, posY);
        chessFrame.redraw();
    }

    public Cell[][] getCells() {
        return cells;
    }
}