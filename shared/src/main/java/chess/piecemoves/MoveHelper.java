package chess.piecemoves;

public class MoveHelper {
    public static boolean isOnBoard(int row, int col) {
        return row >= 1 && row <= 8 && col >=1 && col <=8;
    }
}
