package chess.piecemoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator implements PieceMovesCalculator {

    private static final int[][] KNIGHT_MOVES = {
            {-2, -1}, {-2, +1},
            {+2, -1}, {+2, +1},
            {-1, -2}, {-1, +2},
            {+1, -2}, {+1, +2}
    };


    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition,
                                                ChessGame.TeamColor pieceColor) {

        List<ChessMove> moves = new ArrayList<>();

        int currentRow = myPosition.getRow();
        int currentColumn = myPosition.getColumn();

        for (int move[] : KNIGHT_MOVES) {

            int newRowPosition = currentRow + move[0];
            int newColumnPosition = currentColumn + move[1];

            if (MoveHelper.isOnBoard(newRowPosition, newColumnPosition)) {

                //ChessPiece getPiece(ChessPosition position)
                ChessPosition newPosition = new ChessPosition(newRowPosition, newColumnPosition);
                ChessPiece pieceAtNewPosition = board.getPiece(newPosition);

                if (pieceAtNewPosition == null || pieceAtNewPosition.getTeamColor() != pieceColor)  {

                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

        }

        return moves;
    }
}
