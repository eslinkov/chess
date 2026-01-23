package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(position);

        int newRow = position.getRow() + 2;
        int newCol = position.getColumn() - 1;

        if (newRow <= 8 && newCol >= 1) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() != myPiece.getTeamColor()){
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
        }

        newRow = position.getRow() + 2;
        newCol = position.getColumn() + 1;

        if (newRow <= 8 && newCol <= 8) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() != myPiece.getTeamColor()){
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
        }

        newRow = position.getRow() + 1;
        newCol = position.getColumn() - 2;

        if ((newRow <= 8) && (newCol >= 1)) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() != myPiece.getTeamColor()){
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
        }

        newRow = position.getRow() + 1;
        newCol = position.getColumn() + 2;

        if (newRow <= 8 && newCol <= 8) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() != myPiece.getTeamColor()){
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
        }

        newRow = position.getRow() - 1;
        newCol = position.getColumn() - 2;

        if ((newRow >= 1) && (newCol >= 1)) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() != myPiece.getTeamColor()){
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
        }

        newRow = position.getRow() - 1;
        newCol = position.getColumn() + 2;

        if ((newRow >= 1) && (newCol <= 8)) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() != myPiece.getTeamColor()){
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
        }

        newRow = position.getRow() - 2;
        newCol = position.getColumn() + 1;

        if ((newRow >= 1) && (newCol  <= 8)) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() != myPiece.getTeamColor()){
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
        }

        newRow = position.getRow() - 2;
        newCol = position.getColumn() - 1;

        if ((newRow >= 1) && (newCol >= 1)) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceAtSquare = board.getPiece(newPosition);

            if (pieceAtSquare == null) {
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }
            else if (pieceAtSquare.getTeamColor() != myPiece.getTeamColor()){
                ChessMove move = new ChessMove(position, newPosition, null);
                moves.add(move);
            }

        }
        return moves;
    }

}
