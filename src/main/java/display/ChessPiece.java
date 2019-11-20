package display;

public class ChessPiece {
    private static final String[] types = { "k", "q", "b", "n", "r", "p" };

    public static String getPiecePath(boolean white, int type) {
        return String.format("%s.png", getKey(white, type));
    }

    public static String getKey(boolean white, int type) {
        return String.format("%s%s", white ? "w" : "b", toType(type));
    }

    private static String toType(int type) {
        return type>=0 && type<types.length ? types[type] : "";
    }
}
