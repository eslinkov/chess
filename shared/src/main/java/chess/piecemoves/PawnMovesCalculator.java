package chess.piecemoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator{


    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition,
                                                ChessGame.TeamColor pieceColor) {

        int currentRow = myPosition.getRow();
        int currentColumn = myPosition.getColumn();
        List<ChessMove> moves = new ArrayList<>();

        int startRow;
        int direction;
        int promotionRow;

        if (pieceColor == ChessGame.TeamColor.WHITE) {
            startRow = 2;
            direction = 1;
            promotionRow = 8;
        } else {
            startRow = 7;
            direction = -1;
            promotionRow = 1;
        }

        int newRow = currentRow + direction;
        // FORWARD
        if (MoveHelper.isOnBoard(newRow, currentColumn)) {
            ChessPosition newPosition = new ChessPosition(newRow, currentColumn);
            ChessPiece pieceAtPosition = board.getPiece(newPosition);

            if (pieceAtPosition == null) {
                if (newRow == promotionRow) {
                    moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK));
                } else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }

                if (currentRow == startRow) {
                    int twoAhead = currentRow + direction * 2;
                    ChessPosition twoAheadPosition = new ChessPosition(twoAhead, currentColumn);
                    ChessPiece pieceTwoAhead = board.getPiece(twoAheadPosition);

                    if (pieceTwoAhead == null) {
                        moves.add(new ChessMove(myPosition, twoAheadPosition, null));
                    }

                }

            }

        }

        // CAPTURE DIAG
        int[][] diagonalMoves = {
                {direction, -1}, {direction, 1}
        };

        for (int[] diagMove : diagonalMoves) {

            int newDiagRow = currentRow + diagMove[0];
            int newDiagCol = currentColumn + diagMove[1];

            if (MoveHelper.isOnBoard(newDiagRow, newDiagCol)) {
                ChessPosition newDiagPosition = new ChessPosition(newDiagRow, newDiagCol);
                ChessPiece pieceAtDiagonal = board.getPiece(newDiagPosition);

                if (pieceAtDiagonal != null && pieceAtDiagonal.getTeamColor() != pieceColor) {
                    if (newDiagRow == promotionRow) {
                        moves.add(new ChessMove(myPosition, newDiagPosition, ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, newDiagPosition, ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, newDiagPosition, ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, newDiagPosition, ChessPiece.PieceType.ROOK));
                    } else {
                        moves.add(new ChessMove(myPosition, newDiagPosition, null));
                    }

                }
            }


        }
        return moves;
    }
}
