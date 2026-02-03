package chess.piecemoves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;

public interface PieceMovesCalculator {
    Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor);
}

