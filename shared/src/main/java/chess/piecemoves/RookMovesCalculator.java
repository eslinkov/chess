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
        return MoveHelper.calculateMoves(board, myPosition, pieceColor, ROOK_MOVES);
    }
}
