package core;

import display.ChessFrame;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        ChessFrame chessFrame = new ChessFrame("해치 체스", board);
        chessFrame.init();
        //chessFrame.redraw(cells);

        int[][] test;
        test = new int[8][];
        test[0] = new int[8];
        test[0][0] = 11;    //  좌표로 (x, y) => (0, 0)
        test[0][1] = 33;    //  좌표로 (x, y) => (0, 7)
        test[0][7] = 44;
        test[1] = new int[8];
        test[2] = new int[8];
        test[3] = new int[8];
        test[4] = new int[8];
        test[5] = new int[8];
        test[6] = new int[8];
        test[7] = new int[8];
    }
}