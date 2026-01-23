package chess;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        if (piece.getPieceType() == PieceType.BISHOP) {
            /* return call to the move calculator for bishop
             */
            /* create an instance of the BishopMovesCalculator

             */
            BishopMovesCalculator calculator = new BishopMovesCalculator();
            return calculator.pieceMoves(board, myPosition);
        }
        if (piece.getPieceType() == PieceType.ROOK) {

            RookMovesCalculator calculator = new RookMovesCalculator();
            return calculator.pieceMoves(board, myPosition);
        }

        if (piece.getPieceType() == PieceType.QUEEN) {
            QueenMovesCalculator calculator = new QueenMovesCalculator();
            return calculator.pieceMoves(board, myPosition);
        }

        if (piece.getPieceType() == PieceType.KING) {
            KingMovesCalculator calculator = new KingMovesCalculator();
            return calculator.pieceMoves(board, myPosition);
        }

        if (piece.getPieceType() == PieceType.KNIGHT) {
            KnightMovesCalculator calculator = new KnightMovesCalculator();
            return calculator.pieceMoves(board, myPosition);
        }

        if (piece.getPieceType() == PieceType.PAWN) {
            PawnMovesCalculator calculator = new PawnMovesCalculator();
            return calculator.pieceMoves(board, myPosition);
        }

        return List.of();
    }
}
