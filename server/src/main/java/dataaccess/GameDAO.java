package dataaccess;

import model.GameData;
import model.GameList;

public interface GameDAO {
    GameData createGame(GameData game) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    GameList listGames() throws DataAccessException;

    GameData updateGame(GameData game) throws DataAccessException;

    void clear() throws DataAccessException;
}
