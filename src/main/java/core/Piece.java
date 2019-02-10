package core;

public class Piece {
    public static final int KING = 0;
    public static final int QUEEN = 1;
    public static final int BISHOP = 2;
    public static final int KNIGHT = 3;
    public static final int ROOK = 4;
    public static final int PAWN = 5;

    private boolean white;
    private int type;

    public Piece(boolean white, int type) {
        this.white = white;
        this.type = type;
    }

    public boolean isWhite() {
        return white;
    }

    public int getType() {
        return type;
    }
}