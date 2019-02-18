package core;

public class Piece {
    public static final int KING = 0;
    public static final int QUEEN = 1;
    public static final int BISHOP = 2;
    public static final int KNIGHT = 3;
    public static final int ROOK = 4;
    public static final int PAWN = 5;

    private boolean white;
    private boolean moved;
    private boolean enPassantable;
    private int type;

    public Piece(boolean white, int type) {
        this.white = white;
        this.type = type;
    }

    public boolean isWhite() {
        return white;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setEnPassantable(boolean enPassantable) {
        this.enPassantable = enPassantable;
    }

    public boolean isEnPassantable() {
        return enPassantable;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}