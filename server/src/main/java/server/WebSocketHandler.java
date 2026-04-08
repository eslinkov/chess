package server;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import io.javalin.websocket.*;
import model.AuthData;
import model.GameData;

import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import websocket.commands.*;


import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public WebSocketHandler(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }


    @Override
    public void handleClose(@NotNull WsCloseContext ctx) throws Exception {
        System.out.println("Websocket closed");
    }

    @Override
    public void handleConnect(@NotNull WsConnectContext ctx) throws Exception {
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(@NotNull WsMessageContext ctx) throws Exception {
        try {
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            Session session = ctx.session;

            String username = getUsername(command.getAuthToken());
            if (username == null) {
                sendError(session, "Error: unauthorized");
                return;
            }

            switch (command.getCommandType()) {
                case CONNECT -> connect(username, session, command);
                case MAKE_MOVE -> {
                    MakeMoveCommand moveCommand = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
                    makeMove(username, session, moveCommand);
                }
                case LEAVE -> leave(username, command);
                case RESIGN -> resign(username, session, command);

            }

        } catch (Exception e) {
            try {
                sendError(ctx.session, e.getMessage());
            } catch (IOException ignored){
            }

        }



    }

    private void connect(String username, Session session, UserGameCommand command) throws IOException,
            DataAccessException {
        int gameID = command.getGameID();
        GameData game = gameDAO.getGame(gameID);

        if (game == null) {
            sendError(session, "Error: game not found");
            return;
        }

        connections.add(gameID, username, session);

        String loadGame = new Gson().toJson(new LoadGameMessage(game.game()));
        session.getRemote().sendString((loadGame));

        String role;
        if (username.equals(game.whiteUsername())) {
            role = "white";
        } else if (username.equals(game.blackUsername())) {
            role = "black";
        } else {
            role = "an observer";
        }
        String message = String.format("%s joined the game as %s", username, role);
        String notification = new Gson().toJson(new NotificationMessage(message));
        connections.broadcast(gameID, username, notification);
    }

    private void makeMove(String username, Session session, MakeMoveCommand command)
            throws IOException, DataAccessException {

        int gameID = command.getGameID();
        GameData gameData = gameDAO.getGame(gameID);
        ChessGame game = gameData.game();

        if (game.getTeamTurn() == null) {
            sendError(session, "Error: game is over");
            return;

        }

        ChessGame.TeamColor playerColor = getPlayerColor(username, gameData);
        if (playerColor == null) {
            sendError(session, "Error: you are an observer");
            return;
        }

        if (game.getTeamTurn() != playerColor) {
            sendError(session, "Error: it is not your turn");
            return;
        }

        ChessMove move = command.getMove();
        var piece = game.getBoard().getPiece(move.getStartPosition());
        if (piece == null || piece.getTeamColor() != playerColor) {
            sendError(session, "Error: that is not your piece");
            return;
        }

        try {
            game.makeMove(move);
        } catch (InvalidMoveException e) {
            sendError(session, "Error: invalid move");
            return;
        }

        GameData updatedGameData = new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(),
                gameData.gameName(), game);
        gameDAO.updateGame(updatedGameData);

        String loadGame = new Gson().toJson(new LoadGameMessage(game));
        connections.broadcast(gameID, null, loadGame);

        String moveDescription = String.format("%s moved %s to %s", username,
                move.getStartPosition(), move.getEndPosition());
        String notification = new Gson().toJson(new NotificationMessage(moveDescription));
        connections.broadcast(gameID, username, notification);

        ChessGame.TeamColor opponent = (playerColor == ChessGame.TeamColor.WHITE)
                ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;

        if (game.isInCheckmate(opponent)) {
            String opponentName = getOpponentUsername(opponent, gameData);
            String msg = new Gson().toJson(new NotificationMessage(opponentName + " is in checkmate"));
            connections.broadcast(gameID, null, msg);
            game.setTeamTurn(null);
            gameDAO.updateGame(new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(),
                    gameData.gameName(), game));
        } else if (game.isInStalemate(opponent)) {
            String msg = new Gson().toJson(new NotificationMessage("Stalemate"));
            connections.broadcast(gameID, null, msg);
            game.setTeamTurn(null);
            gameDAO.updateGame(new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(),
                    gameData.gameName(), game));
        } else if (game.isInCheck(opponent)) {
            String opponentName = getOpponentUsername(opponent, gameData);
            String msg = new Gson().toJson(new NotificationMessage(opponentName + " is in check"));
            connections.broadcast(gameID, null, msg);
        }
    }

    private void leave(String username, UserGameCommand command) throws IOException, DataAccessException {
        int gameID = command.getGameID();
        GameData gameData = gameDAO.getGame(gameID);

        ChessGame.TeamColor playerColor = getPlayerColor(username, gameData);
        if (playerColor != null) {
            String white = gameData.whiteUsername();
            String black = gameData.blackUsername();
            if (playerColor == ChessGame.TeamColor.WHITE) {
                white = null;
            } else {
                black = null;
            }
            gameDAO.updateGame(new GameData(gameID, white, black, gameData.gameName(), gameData.game()));
        }

        connections.remove(gameID, username);

        String notification = new Gson().toJson(new NotificationMessage(username + " left the game"));
        connections.broadcast(gameID, username, notification);
    }

    private void resign(String username, Session session, UserGameCommand command)
            throws IOException, DataAccessException {

        int gameID = command.getGameID();
        GameData gameData = gameDAO.getGame(gameID);
        ChessGame game = gameData.game();

        if (game.getTeamTurn() == null) {
            sendError(session, "Error: game is already over");
            return;
        }

        ChessGame.TeamColor playerColor = getPlayerColor(username, gameData);
        if (playerColor == null) {
            sendError(session, "Error: observers cannot resign");
            return;
        }

        game.setTeamTurn(null);
        gameDAO.updateGame(new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(),
                gameData.gameName(), game));

        String notification = new Gson().toJson(new NotificationMessage(username + " resigned the game"));
        connections.broadcast(gameID, null, notification);

    }

    private String getUsername(String authToken) {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            if (auth != null) {
                return auth.username();
            }
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void sendError(Session session, String message) throws IOException {
        String error = new Gson().toJson(new ErrorMessage(message));
        session.getRemote().sendString(error);
    }

    private ChessGame.TeamColor getPlayerColor(String username, GameData gameData) {
        if (username.equals(gameData.whiteUsername())) {
            return ChessGame.TeamColor.WHITE;
        } else if (username.equals(gameData.blackUsername())) {
            return ChessGame.TeamColor.BLACK;
        }
        return null;
    }

    private String getOpponentUsername(ChessGame.TeamColor color, GameData gameData) {
        if (color == ChessGame.TeamColor.WHITE) {
            return gameData.whiteUsername();
        }
        return gameData.blackUsername();
    }




}
