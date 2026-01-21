package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(position);

        /* Move diagonal up & right
         */
        int newRow = position.getRow();
        int newCol = position.getColumn();
        while (true) {
            newRow = newRow + 1;
            newCol = newCol + 1;
            if (newRow > 8 || newCol > 8) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() == myPiece.getTeamColor()) {
                break;
            }
            else {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
                break;
            }


        }

        return moves;
    }
}
