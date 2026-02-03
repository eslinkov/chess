package chess.piecemoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator{

    private static final int[][] QUEEN_MOVES = {
            {1, 0}, {0, 1}, {-1, 0}, {0, -1},
            {1, -1}, {1, 1}, {-1, -1}, {-1, 1}
    };

    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();

        int currentRow = myPosition.getRow();
        int currentColumn = myPosition.getColumn();

        for (int[] move : QUEEN_MOVES) {
            int newRow = currentRow;
            int newColumn = currentColumn;

            while (true) {
                newRow += move[0];
                newColumn += move[1];

                if (!MoveHelper.isOnBoard(newRow, newColumn)) {
                    break;
                } else {
                    ChessPosition newPosition = new ChessPosition(newRow, newColumn);
                    ChessPiece pieceAtNew = board.getPiece(newPosition);

                    if (pieceAtNew == null) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                    else if (pieceAtNew.getTeamColor() != pieceColor) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        return moves;
    }
}
