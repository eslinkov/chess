package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator{

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(position);

        int newRow = position.getRow();
        int newCol = position.getColumn();

        while (true) {
            newRow = newRow + 1;

            if (newRow > 8) {
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

        newRow = position.getRow();
        newCol = position.getColumn();

        while (true) {
            newRow = newRow - 1;

            if (newRow < 1) {
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

        newRow = position.getRow();
        newCol = position.getColumn();

        while (true) {
            newCol = newCol + 1;

            if (newCol > 8) {
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

        newRow = position.getRow();
        newCol = position.getColumn();

        while (true) {
            newCol = newCol - 1;

            if (newCol < 1) {
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
