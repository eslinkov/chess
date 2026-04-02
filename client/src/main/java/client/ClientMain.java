package client;

import chess.*;

public class ClientMain {
    public static void main(String[] args) {
        new ChessClient("http://localhost:8080").run();
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
    }
}
