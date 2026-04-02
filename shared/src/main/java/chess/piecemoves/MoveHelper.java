package chess.piecemoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MoveHelper {
    public static boolean isOnBoard(int row, int col) {
        return row >= 1 && row <= 8 && col >=1 && col <=8;
    }

    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, int[][] directions) {
        List<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        for (int[] move : directions) {
            int newRow = currentRow;
            int newCol = currentCol;

            while (true) {
                newRow += move[0];
                newCol += move[1];

                if (!isOnBoard(newRow, newCol)) {
                    break;
                }

                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece pieceAtPosition = board.getPiece(newPosition);

                if (pieceAtPosition == null) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                } else if (pieceAtPosition.getTeamColor() != pieceColor) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                    break;
                } else {
                    break;
                }
            }
        }
        return moves;
    }
}
