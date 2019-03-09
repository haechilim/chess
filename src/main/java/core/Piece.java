package core;

public class Piece {
    public static final int KING = 0;
    public static final int QUEEN = 1;
    public static final int BISHOP = 2;
    public static final int KNIGHT = 3;
    public static final int ROOK = 4;
    public static final int PAWN = 5;

    private boolean moved;
    private boolean white;
    private boolean enpassantable;
    private int type;

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isMoved(){
        return moved;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

    public void setEnpassantable(boolean enpassantable) {
        this.enpassantable = enpassantable;
    }

    public boolean isEnpassantable() {
        return enpassantable;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}