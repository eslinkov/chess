package chess.piecemoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator{

    private static final int[][] ROOK_MOVES = {
            {1, 0},
            {0, -1},        {0, 1},
            {-1, 0}
    };



    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        List<ChessMove> moves = new ArrayList<>();

        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        for (int[] move : ROOK_MOVES) {
            int newRow = currentRow;
            int newCol = currentCol;

            while (true) {
                newRow += move[0];
                newCol += move[1];

                if (!MoveHelper.isOnBoard(newRow, newCol)) {
                    break;
                }

                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece pieceAtPosition = board.getPiece(newPosition);

                if (pieceAtPosition ==  null) {
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
