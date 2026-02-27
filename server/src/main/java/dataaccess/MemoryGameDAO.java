package dataaccess;

import model.GameData;
import model.GameListOld;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    final private HashMap<Integer, GameData> gameDataHashMap = new HashMap<>();

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        gameDataHashMap.put(game.gameID(), game);
        return game;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return gameDataHashMap.get(gameID);
    }

    @Override
    public GameListOld listGames() throws DataAccessException {
        return new GameListOld(gameDataHashMap.values());
    }

    @Override
    public GameData updateGame(GameData game) throws DataAccessException {

        gameDataHashMap.put(game.gameID(), game);

        return game;
    }

    @Override
    public void clear() throws DataAccessException {
        gameDataHashMap.clear();
    }
}
