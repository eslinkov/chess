package chess;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {

        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(position);

        int newRow = position.getRow();
        int newCol = position.getColumn();

        int direction;
        int startRow;
        int promotionRow;

        if (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            direction = 1;
            startRow = 2;
            promotionRow = 8;
        }
        else {
            direction = -1;
            startRow = 7;
            promotionRow = 1;
        }

        newRow = position.getRow() + direction;

        if (newRow >= 1 && newRow <= 8) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {

                if (newRow == promotionRow) {
                    moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));

                } else {
                    ChessMove move = new ChessMove(position, newPosition, null);
                    moves.add(move);
                }


                if (position.getRow() == startRow) {
                    int twoAheadRow = position.getRow() + (direction * 2);
                    ChessPosition twoAheadPosition = new ChessPosition(twoAheadRow, newCol);
                    ChessPiece pieceTwoAhead = board.getPiece(twoAheadPosition);

                    if (pieceTwoAhead == null) {
                        ChessMove move = new ChessMove(position, twoAheadPosition, null);
                        moves.add(move);
                    }
                }
            }

        }

        int captureRow = position.getRow() + direction;
        int leftCol = position.getColumn() - 1;

        if (captureRow >= 1 && captureRow <= 8 && leftCol >= 1) {
            ChessPosition leftCapture = new ChessPosition(captureRow, leftCol);
            ChessPiece pieceAtLeft = board.getPiece(leftCapture);


            if (pieceAtLeft != null && pieceAtLeft.getTeamColor() != myPiece.getTeamColor()) {

                if (captureRow == promotionRow) {
                    moves.add(new ChessMove(position, leftCapture, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, leftCapture, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(position, leftCapture, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, leftCapture, ChessPiece.PieceType.KNIGHT));

                } else {
                    ChessMove move = new ChessMove(position, leftCapture, null);
                    moves.add(move);

                }

            }
        }

        captureRow = position.getRow() + direction;
        int rightCol = position.getColumn() + 1;

        if (captureRow >= 1 && captureRow <= 8 && rightCol <= 8) {
            ChessPosition rightCapture = new ChessPosition(captureRow, rightCol);
            ChessPiece pieceAtRight = board.getPiece(rightCapture);


            if (pieceAtRight != null && pieceAtRight.getTeamColor() != myPiece.getTeamColor()) {

                if (captureRow == promotionRow) {
                    moves.add(new ChessMove(position, rightCapture, ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, rightCapture, ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(position, rightCapture, ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, rightCapture, ChessPiece.PieceType.KNIGHT));

                } else {
                    ChessMove move = new ChessMove(position, rightCapture, null);
                    moves.add(move);
                }
            }
        }

        return moves;
    }




}
