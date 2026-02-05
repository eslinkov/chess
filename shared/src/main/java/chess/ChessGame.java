package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor currentTurn;

    public ChessGame() {
        this.currentTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTurn = team;
    }



    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();

        if (piece == null) {
            return null;
        }
        Collection<ChessMove> potentialMoves = piece.pieceMoves(board, startPosition);

        for (ChessMove move : potentialMoves) {
            ChessBoard testBoard = copyBoard();
            testBoard.addPiece(move.getEndPosition(), piece);
            testBoard.addPiece(startPosition, null);

            ChessBoard originalBoard = board;

            board = testBoard;

            if (!isInCheck(piece.getTeamColor())) {
                validMoves.add(move);
            }

            board = originalBoard;

        }
        return validMoves;

    }

    public ChessBoard copyBoard() {
        ChessBoard copy = new ChessBoard();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {

                ChessPosition currentSquare = new ChessPosition(row, col);
                ChessPiece pieceAtSquare = board.getPiece(currentSquare);
                copy.addPiece(currentSquare, pieceAtSquare);

            }
        }

        return copy;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        ChessPiece piece = board.getPiece(move.getStartPosition());

        if (piece != null && validMoves.contains(move) && currentTurn == piece.getTeamColor()) {

            if (move.getPromotionPiece() != null) {
                ChessPiece.PieceType promotionPieceType = move.getPromotionPiece();
                ChessPiece promotionPiece = new ChessPiece(piece.getTeamColor(), promotionPieceType);
                board.addPiece(move.getEndPosition(), promotionPiece);
                board.addPiece(move.getStartPosition(), null);
            } else {
                board.addPiece(move.getEndPosition(), piece);
                board.addPiece(move.getStartPosition(), null);
            }

            if (currentTurn == TeamColor.BLACK) {
                currentTurn = TeamColor.WHITE;
            } else {
                currentTurn = TeamColor.BLACK;
            }

        } else {
            throw new InvalidMoveException("Invalid Move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPosition = null;

        // loop through all the squares on the chess board and test what piece is at each
        // square and check if it is the king that is the teamColor
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentSquare = new ChessPosition(row, col);
                ChessPiece pieceAtSquare = board.getPiece(currentSquare);

                if (pieceAtSquare != null && pieceAtSquare.getTeamColor() == teamColor && pieceAtSquare.getPieceType() == ChessPiece.PieceType.KING) {
                    // get postion king is at
                    kingPosition = currentSquare;
                }

            }
        }

        // calculate any opposing team potential move choices that would land on king position
        // itereate through the moves and compare to the king position

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentSquare = new ChessPosition(row, col);
                ChessPiece pieceAtSquare = board.getPiece(currentSquare);

                if (pieceAtSquare != null && pieceAtSquare.getTeamColor() != teamColor) {
                    Collection<ChessMove> potentialMoves = pieceAtSquare.pieceMoves(board, currentSquare);

                    for (ChessMove move : potentialMoves) {
                        if (move.getEndPosition().equals(kingPosition)) {
                            return true;
                        }
                    }
                }
            }
        }



        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentSquare = new ChessPosition(row, col);
                ChessPiece pieceAtSquare = board.getPiece(currentSquare);

                if (pieceAtSquare != null && pieceAtSquare.getTeamColor() == teamColor) {
                    Collection<ChessMove> validMoves = validMoves(currentSquare);
                    if (!validMoves.isEmpty()) {
                        return false;
                    }
                }

            }
        }

        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", currentTurn=" + currentTurn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessGame chessGame)) {
            return false;
        }
        return Objects.equals(board, chessGame.board) && currentTurn == chessGame.currentTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, currentTurn);
    }
}
