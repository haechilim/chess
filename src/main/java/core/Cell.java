package core;

public class Cell {
    private Piece piece;
    private boolean selected;
    private boolean movable;
    private boolean castlingable;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return selected;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public void setCastlingable(boolean castlingable) {
        this.castlingable = castlingable;
    }

    public boolean isCastlingable() {
        return castlingable;
    }

    public boolean isEmpty() {
        return getPiece() == null ? true : false;
    }
}