package chess.piecemoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator implements PieceMovesCalculator {

    private static final int [][] KING_MOVES = {
            {1, -1},  {1, 0},  {1, 1},
            {0, -1},           {0, 1},
            {-1, -1}, {-1, 0}, {-1, 1}
    };

    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition,
                                                ChessGame.TeamColor pieceColor) {

        List<ChessMove> moves = new ArrayList<>();

        int currentRow = myPosition.getRow();
        int currentColumn = myPosition.getColumn();

        for (int[] move : KING_MOVES) {
            int newRow = currentRow + move[0];
            int newCol = currentColumn + move[1];

            if (MoveHelper.isOnBoard(newRow, newCol)) {

                ChessPosition newPosition = new ChessPosition(newRow, newCol);

                ChessPiece pieceAtPosition = board.getPiece(newPosition);

                if (pieceAtPosition == null || pieceAtPosition.getTeamColor() != pieceColor) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    moves.add(validMove);


                }
            }
        }

        return moves;
    }


}
