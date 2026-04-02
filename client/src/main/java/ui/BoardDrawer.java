package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.BLACK_PAWN;
import static ui.EscapeSequences.BLACK_ROOK;

public class BoardDrawer {

    public static void drawBoard(ChessBoard board, boolean whitePerspective) {
        String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};
        System.out.print(SET_BG_COLOR_DARK_GREY + EMPTY);
        for (String col : columns) {
            System.out.print(SET_BG_COLOR_DARK_GREY + col + "\u2003 ");
        }
        System.out.println("\u2003" + RESET_BG_COLOR);

        for (int row = 8; row >= 1; row--) {
            System.out.print(SET_BG_COLOR_DARK_GREY + " " + row + " ");
            for (int col = 1; col <= 8; col++) {
                if ((row + col) % 2 == 0) {
                    System.out.print(SET_BG_COLOR_PINK);
                } else {
                    System.out.print(SET_BG_COLOR_WHITE);
                }
                ChessPiece piece = board.getPiece(new ChessPosition(row, col));
                if (piece == null) {
                    System.out.print(EMPTY);
                } else {
                    System.out.print(getPieceSymbol(piece));
                }
            }
            System.out.print(SET_BG_COLOR_DARK_GREY + " " + row + " ");
            System.out.println(RESET_BG_COLOR);
        }

        System.out.print(SET_BG_COLOR_DARK_GREY + EMPTY);
        for (String col : columns) {
            System.out.print(SET_BG_COLOR_DARK_GREY +  col + "\u2003 ");
        }
        System.out.println("\u2003" + RESET_BG_COLOR);
    }

    private static String getPieceSymbol(ChessPiece piece) {
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            return switch (piece.getPieceType()) {
                case KING -> WHITE_KING;
                case QUEEN -> WHITE_QUEEN;
                case BISHOP -> WHITE_BISHOP;
                case KNIGHT -> WHITE_KNIGHT;
                case ROOK -> WHITE_ROOK;
                case PAWN -> WHITE_PAWN;
            };
        } else {
            return switch (piece.getPieceType()) {
                case KING -> BLACK_KING;
                case QUEEN -> BLACK_QUEEN;
                case BISHOP -> BLACK_BISHOP;
                case KNIGHT -> BLACK_KNIGHT;
                case ROOK -> BLACK_ROOK;
                case PAWN -> BLACK_PAWN;
            };
        }
    }


}


