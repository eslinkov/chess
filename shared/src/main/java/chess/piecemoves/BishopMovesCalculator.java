package chess.piecemoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator{

    private static final int[][] BISHOP_MOVES = {
            {1, -1},    {1, 1},
            {-1, -1},   {-1, 1}
    };

    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();

        int currentRow = myPosition.getRow();
        int currentColumn = myPosition.getColumn();

        for (int[] move : BISHOP_MOVES) {
            int newRow = currentRow;
            int newColumn = currentColumn;

            while(true) {
                newRow += move[0];
                newColumn += move[1];

                if (!MoveHelper.isOnBoard(newRow, newColumn)) {
                    break;
                }

                ChessPosition newPosition = new ChessPosition(newRow, newColumn);
                ChessPiece pieceAtPosition = board.getPiece(newPosition);

                if (pieceAtPosition == null) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }

                else if (pieceAtPosition.getTeamColor() != pieceColor) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                    break;
                }
                else {
                    break;
                }
            }
        }

        return moves;
    }
}
