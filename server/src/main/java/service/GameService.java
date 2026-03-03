package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.*;

public class GameService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public GameList listGames(String authToken) throws DataAccessException {
        validateAuth(authToken);

        return gameDAO.listGames();
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest, String authToken) throws DataAccessException {
        validateAuth(authToken);

        if (createGameRequest.gameName() == null) {
            throw new DataAccessException("Error: bad request");
        }

        GameData emptyGameData = new GameData(0, null, null, createGameRequest.gameName(),
                new ChessGame());

        GameData newGame = gameDAO.createGame(emptyGameData);

        return new CreateGameResult(newGame.gameID());
    }



    public void joinGame(JoinRequest joinRequest, String authToken) throws DataAccessException{
        validateAuth(authToken);

        if (joinRequest.playerColor() == null || gameDAO.getGame(joinRequest.gameID()) == null)  {
            throw new DataAccessException("Error: bad request");
        }

        GameData gameToJoin = gameDAO.getGame(joinRequest.gameID());

        String playerUsername = authDAO.getAuth(authToken).username();

        if (joinRequest.playerColor().equals("WHITE")) {
            if (gameToJoin.whiteUsername() != null) {
                throw new DataAccessException("Error: already taken");

            } else {
                GameData updatedGameData = new GameData(gameToJoin.gameID(), playerUsername, gameToJoin.blackUsername(),
                        gameToJoin.gameName(), gameToJoin.game());

                gameDAO.updateGame(updatedGameData);
            }
        } else if (joinRequest.playerColor().equals("BLACK")){
            if (gameToJoin.blackUsername() != null) {
                throw new DataAccessException("Error: already taken");

            } else {
                GameData updatedGameData = new GameData(gameToJoin.gameID(), gameToJoin.whiteUsername(), playerUsername,
                        gameToJoin.gameName(), gameToJoin.game());

                gameDAO.updateGame(updatedGameData);
            }

        } else {
            throw new DataAccessException("Error: bad request");
        }
    }

    private void validateAuth(String authToken) throws DataAccessException {
        if (authToken == null || authDAO.getAuth(authToken) == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }


}
