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
        clearSelectedCell();
        cell.setselected(true);
        selectedCell = cell;
    }

    private void markMovable(Cell cell, int posX, int posY) {
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

    private void markMovablePawn(Cell selectedCell, int posX, int posY) {
        int offset = whiteTurn ? -1 : 1;
        Cell cell, target;

        {   // 전진
            cell = getCell(posX, posY + offset);

            if(!cell.hasPiece()) {
                cell.setMovable(true);

                cell = getCell(posX, posY + offset * 2);
                if(!selectedCell.getPiece().isMoved() && !cell.hasPiece()) cell.setMovable(true);
            }
        }

        {   // 대각선 먹기
            cell = getCell(posX + 1, posY + offset);
            if(hasEnemyPiece(cell)) cell.setMovable(true);

            cell = getCell(posX - 1, posY + offset);
            if(hasEnemyPiece(cell)) cell.setMovable(true);
        }

        {   // 앙파상 먹기
            cell = getCell(posX + 1, posY + offset);
            target = getCell(posX + 1, posY);
            if(!hasPiece(cell) && isEnpassantable(target)) cell.setMovable(true);

            cell = getCell(posX - 1, posY + offset);
            target = getCell(posX - 1, posY);
            if(!hasPiece(cell) && isEnpassantable(target)) cell.setMovable(true);
        }
    }

    private void markMovableRook(int posX, int posY) {
        for(int y = 1; ; y++) {
            if(!markMovableCell(posX, posY + y)) break;
        }

        for(int y = 1; ; y++) {
            if(!markMovableCell(posX, posY - y)) break;
        }

        for(int x = 1; ; x++) {
            if(!markMovableCell(posX + x, posY)) break;
        }

        for(int x = 1; ; x++) {
            if(!markMovableCell(posX - x, posY)) break;
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
        for(int n = 1; ; n++) {
            if(!markMovableCell(posX + n, posY + n)) break;
        }

        for(int n = 1; ; n++) {
            if(!markMovableCell(posX + n, posY - n)) break;
        }

        for(int n = 1; ; n++) {
            if(!markMovableCell(posX - n, posY - n)) break;
        }

        for(int n = 1; ; n++) {
            if(!markMovableCell(posX - n, posY + n)) break;
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
        if(isValidIndex(x, y) && isMovableCell(x, y)) {
            Cell cell = cells[x][y];
            cell.setMovable(true);

            return !cell.hasEnemyPiece(whiteTurn);
        }

        return false;
    }

    private void markCastling(int posX, int posY) {
        if(selectedCell == null) return;

        Piece selectedPiece = selectedCell.getPiece();

        if(selectedPiece != null && selectedPiece.getType() == Piece.KING && !selectedPiece.isMoved()) {
            if(cells[posX + 3][posY].hasPiece() && !cells[posX + 3][posY].getPiece().isMoved()) {
                if(cells[posX + 1][posY].getPiece() == null && cells[posX + 2][posY].getPiece() == null) {
                    cells[posX + 2][posY].setCastlingable(true);
                    cells[posX + 2][posY].setMovable(true);
                }
            }
            if(cells[posX - 4][posY].hasPiece() && !cells[posX - 4][posY].getPiece().isMoved()) {
                if(cells[posX - 1][posY].getPiece() == null && cells[posX - 2][posY].getPiece() == null && cells[posX - 3][posY].getPiece() == null) {
                    cells[posX - 2][posY].setCastlingable(true);
                    cells[posX - 2][posY].setMovable(true);
                }
            }
        }
    }

    private void promotion(Cell cell, int posY) {
        if(cell.getPiece().getType() == Piece.PAWN && (posY == 0 || posY == 7)) {
            cell.getPiece().setType(Piece.QUEEN);
        }
    }

    private boolean move(Cell cell, int posX, int posY) {
        if(cell.isMovable()) {
            setEnpassantable(posY);
            moveRookForCastling(cell, posX, posY);
            moveEnpassant(posX, posY);
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
        int offset = whiteTurn ? 1 : -1;

        if(selectedCell.getPiece().getType() == Piece.PAWN) {
            Cell target = cells[posX][posY + offset];
            if(hasEnemyPiece(target) && target.getPiece().isEnpassantable()) target.clearPiece();
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

    private void setEnpassantable(int posY) {
        Piece piece = selectedCell.getPiece();

        if(piece.getType() == Piece.PAWN && !piece.isMoved()) {
            if(posY == 3 || posY == 4) piece.setEnpassantable(true);
        }
    }

    private void clearEnpassant() {
        for(int x = 0; x < cells.length; x++) {
            for(int y = 0; y < cells[x].length; y++) {
                Piece piece = cells[x][y].getPiece();
                if(piece != null && piece.isWhite() == whiteTurn) piece.setEnpassantable(false);
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
        return (posX >= 0 && posX < 8) && (posY >= 0 && posY < 8) ? true : false;
    }

    private boolean isMovableCell(int posX, int posY) {
        Cell cell = cells[posX][posY];
        return !cell.hasPiece() || cell.hasEnemyPiece(whiteTurn);
    }

    private boolean isEnpassantable(Cell cell) {
        return hasEnemyPiece(cell) && cell.getPiece().isEnpassantable();
    }

    private boolean hasPiece(Cell cell) {
        return cell != null && cell.hasPiece();
    }

    private boolean hasEnemyPiece(Cell cell) {
        return cell != null && cell.hasEnemyPiece(whiteTurn);
    }

    private Cell getCell(int posX, int posY) {
        return isValidIndex(posX, posY) ? cells[posX][posY] : null;
    }

    //  화면에 클릭 이벤트가 발생하면 호출됨
    public void cellClicked(int posX, int posY) {
        Cell cell = cells[posX][posY];

        clearEnpassant();

        if(move(cell, posX, posY)) {
            promotion(cell, posY);

            clearMovable(cells);
            clearSelectedCell();
            whiteTurn = !whiteTurn;
            chessFrame.redraw();
            return;
        }

        if(!cell.hasPiece() || cell.getPiece().isWhite() != whiteTurn) return;

        markSelectedCell(cell);
        markMovable(cell, posX, posY);
        markCastling(posX, posY);
        chessFrame.redraw();
    }

    public Cell[][] getCells() {
        return cells;
    }
}