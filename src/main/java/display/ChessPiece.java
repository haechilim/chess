package display;

import java.awt.*;

public class ChessPiece {
    private static final String classpath = ChessPiece.class.getResource("/").getPath();
    private static final String[] types = { "k", "q", "b", "n", "r", "p" };

    public static Image getPiece(boolean white, int type) {
        String path = String.format("%s%s.png", classpath, getKey(white, type));
        return Util.loadImage(path);
    }

    public static String getKey(boolean white, int type) {
        return String.format("%s%s", white ? "w" : "b", toType(type));
    }

    private static String toType(int type) {
        return type>=0 && type<types.length ? types[type] : "";
    }
}
