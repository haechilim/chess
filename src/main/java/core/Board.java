package core;

import display.ChessFrame;

public class Board {
    private boolean whiteTurn = true;
    private Cell[][] cells;
    private Cell selectedCell;
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
        //cells[1][0].setPiece(new Piece(false, Piece.KNIGHT));
        //cells[2][0].setPiece(new Piece(false, Piece.BISHOP));
        //cells[3][0].setPiece(new Piece(false, Piece.QUEEN));
        cells[4][0].setPiece(new Piece(false, Piece.KING));
        //cells[5][0].setPiece(new Piece(false, Piece.BISHOP));
        //cells[6][0].setPiece(new Piece(false, Piece.KNIGHT));
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
        //cells[5][7].setPiece(new Piece(true, Piece.BISHOP));
        //cells[6][7].setPiece(new Piece(true, Piece.KNIGHT));
        cells[7][7].setPiece(new Piece(true, Piece.ROOK));

        /*cells[5][3].setPiece(new Piece(true, Piece.KNIGHT));
        cells[4][5].setPiece(new Piece(true, Piece.BISHOP));
        cells[3][4].setPiece(new Piece(true, Piece.KING));
        cells[4][3].setPiece(new Piece(true, Piece.QUEEN));
        cells[2][5].setPiece(new Piece(true, Piece.PAWN));
        cells[2][3].setPiece(new Piece(false, Piece.PAWN));

        cells[0][7].setPiece(new Piece(true, Piece.KING));

        cells[1][4].setPiece(new Piece(true, Piece.ROOK));*/
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

    private void markMovableCells(Piece piece, int x, int y) {
        if(piece.getType() == Piece.PAWN) markMovableCellsForPawn(piece, x, y);
        else if(piece.getType() == Piece.ROOK) markMovableCellsForRook(x, y);
        else if(piece.getType() == Piece.BISHOP) markMovableCellsForBishop(x, y);
        else if(piece.getType() == Piece.QUEEN) markMovableCellsForQueen(x, y);
        else if(piece.getType() == Piece.KING) markMovableCellsForKing(x, y);
        else if(piece.getType() == Piece.KNIGHT) markMovableCellsForKnight(x, y);
    }

    private void markMovableCellsForPawn(Piece piece, int posX, int posY) {
        int offset = whiteTurn ? -1 : 1;

        if(cells[posX][posY + offset].isEmpty()) {
            if (!markMovableCell(posX, posY + offset)) return;
            if (!piece.isMoved() && cells[posX][posY + (offset * 2)].isEmpty())
                markMovableCell(posX, posY + (offset * 2));
        }

        if(existsOppositePiece(posX + 1, posY + offset)) markMovableCell(posX + 1, posY + offset);
        if(existsOppositePiece(posX - 1, posY + offset)) markMovableCell(posX - 1, posY + offset);
    }

    private void markMovableCellsForRook(int posX, int posY) {
        for(int y = posY - 1; ; y--) {
            if(!markMovableCell(posX, y)) break;
        }

        for(int x = posX + 1; ; x++) {
            if(!markMovableCell(x, posY)) break;
        }

        for(int y = posY + 1; ; y++) {
            if(!markMovableCell(posX, y)) break;
        }

        for(int x = posX - 1; ; x--) {
            if(!markMovableCell(x, posY)) break;
        }
    }

    private void markMovableCellsForBishop(int posX, int posY) {
        for(int x = posX + 1, y = posY - 1; ; x++, y--) {
            if(!markMovableCell(x, y)) break;
        }

        for(int x = posX + 1, y = posY + 1; ; x++, y++) {
            if(!markMovableCell(x, y)) break;
        }

        for(int x = posX - 1, y = posY + 1; ; x--, y++) {
            if(!markMovableCell(x, y)) break;
        }

        for(int x = posX - 1, y = posY - 1; ; x--, y--) {
            if(!markMovableCell(x, y)) break;
        }
    }

    private void markMovableCellsForQueen(int posX,int posY) {
        markMovableCellsForRook(posX, posY);
        markMovableCellsForBishop(posX, posY);
    }

    private void markMovableCellsForKing(int posX,int posY) {
        markMovableCell(posX, posY  - 1);
        markMovableCell(posX + 1, posY - 1);
        markMovableCell(posX + 1, posY);
        markMovableCell(posX + 1, posY + 1);
        markMovableCell(posX, posY + 1);
        markMovableCell(posX - 1, posY + 1);
        markMovableCell(posX - 1, posY);
        markMovableCell(posX - 1, posY - 1);

        markCastling(posX, posY);
    }

    private void markMovableCellsForKnight(int posX, int posY) {
        markMovableCell(posX + 1, posY - 2);
        markMovableCell(posX + 2, posY - 1);
        markMovableCell(posX + 2, posY + 1);
        markMovableCell(posX + 1, posY + 2);
        markMovableCell(posX - 1, posY + 2);
        markMovableCell(posX - 2, posY + 1);
        markMovableCell(posX - 2, posY - 1);
        markMovableCell(posX - 2, posY - 1);
        markMovableCell(posX - 1, posY - 2);
    }

    private boolean markMovableCell(int posX, int posY) {
        if(isValidPosition(posX, posY)) {
            if(cells[posX][posY].isEmpty()) {
                cells[posX][posY].setMovable(true);
                return true;
            }
            else if(existsOppositePiece(posX, posY)) cells[posX][posY].setMovable(true);
        }

        return false;
    }

    private boolean isValidPosition(int posX, int posY) {
        return (posX>=0 && posX<8 && posY>=0 && posY<8);
    }

    private boolean existsOppositePiece(int posX, int posY) {
        if(!isValidPosition(posX, posY) || cells[posX][posY].isEmpty()) return false;

        return cells[posX][posY].getPiece().isWhite() == !whiteTurn;
    }

    private boolean move(Cell cell) {
        if(cell.isMovable() && selectedCell != null) {
            Piece selectedPiece = selectedCell.getPiece();
            cell.setPiece(selectedPiece);
            selectedPiece.setMoved(true);

            selectedCell.setPiece(null);
            selectedCell = null;
            whiteTurn = !whiteTurn;
            return true;
        }

        return false;
    }

    private void promotion(Piece piece, int posY) {
        if(piece.getType() == Piece.PAWN && (posY == 0 || posY == 7)) piece.setType(Piece.QUEEN);
    }

    private void markCastling(int posX, int posY) {
        Piece selectedPiece = cells[posX][posY].getPiece();
        if(selectedPiece == null || selectedPiece.getType() != Piece.KING || selectedPiece.isMoved()) return;

        Piece piece = cells[0][posY].getPiece();
        if(piece != null && !piece.isMoved()) {
            if(cells[1][posY].isEmpty() && cells[2][posY].isEmpty() && cells[3][posY].isEmpty()) cells[2][posY].setMovable(true);
        }

        piece = cells[7][posY].getPiece();
        if(piece != null && !piece.isMoved()) {
            if(cells[5][posY].isEmpty() && cells[6][posY].isEmpty()) cells[6][posY].setMovable(true);
        }

        /*
            실제 이동(룩도 같이)
         */
    }

    private void enPassant() {
        /*
            상대방 폰이 최초 두칸 이동한 바로 다음 턴에만 가능

            1. Piece 클래스에서 enPassantable 멤버변수 추가
            2. 매 턴마다 자기의 모든 폰의 enPassantable값을 false로 만듬
            3. 어떤 말이 최초 두칸을 움직일때 enPassantable = true
            4. 폰이 선택되어 있을때,
                4-1. 좌측/우측 폰이 상대방 폰이고
                4-2. 그 폰의 enPassantable값이 true인 경우
                4-3. 그 지점에 동그라미 표시를 해줌
            5. 상대방 말을 먹었을때 특별한 처리가 필요
         */
    }

    //  화면에 클릭 이벤트가 발생하면 호출됨
    public void cellClicked(int posX, int posY) {
        Cell cell = cells[posX][posY];
        Piece piece = cell.getPiece();

        if(move(cell)) {
            clearSelected(cells);
            clearMovabled(cells);
            promotion(cell.getPiece(), posY);
            chessFrame.redraw();
            return;
        }

        if(piece == null) return;
        if(piece.isWhite() == !whiteTurn) return;

        clearSelected(cells);
        clearMovabled(cells);
        cell.setSelected(true);
        markMovableCells(piece, posX, posY);
        chessFrame.redraw();

        selectedCell = cell;
    }

    public Cell[][] getCells() {
        return cells;
    }
}