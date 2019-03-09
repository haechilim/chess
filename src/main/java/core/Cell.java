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

    public void setPiece(boolean white, int type) {
        Piece piece = new Piece();
        piece.setType(type);
        piece.setWhite(white);

        this.piece = piece;
    }

    public void setselected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setCastlingable(boolean castlingable) {
        this.castlingable = castlingable;
    }

    public boolean isCastlingable() {
        return castlingable;
    }

    public void clearPiece() {
        piece = null;
    }

    public boolean existsPiece() {
        return piece != null;
    }

    public boolean existsOppositePiece(boolean whiteTurn) {
        return (piece != null && piece.isWhite() != whiteTurn);
    }
}