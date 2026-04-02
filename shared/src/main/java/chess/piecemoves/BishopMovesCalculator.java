package chess.piecemoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator{

    private static final int[][] BISHOP_MOVES = {
            {1, -1},    {1, 1},
            {-1, -1},   {-1, 1}
    };

    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor) {
        return MoveHelper.calculateMoves(board, myPosition, pieceColor, BISHOP_MOVES);
    }
}
