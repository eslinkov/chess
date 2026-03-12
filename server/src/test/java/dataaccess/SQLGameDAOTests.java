package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SQLGameDAOTests {


    private GameDAO gameDAO;

    @BeforeEach
    void setup() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clear();

    }

    @Test
    public void testTableCreation() throws DataAccessException {
        new SQLGameDAO();
    }

    @Test
    void testClear() throws DataAccessException {
        GameData testGame = new GameData(0, null, null, "testGame",
                new ChessGame());

        for (int i = 0; i < 5; i++) {
            gameDAO.createGame(testGame);
        }

        Assertions.assertDoesNotThrow(() -> {
            gameDAO.clear();
        });

        for (int i = 0; i < 5; i++) {
            gameDAO.createGame(testGame);
        }

        gameDAO.clear();

        for (int i = 1; i < 6; i++) {
            Assertions.assertNull(gameDAO.getGame(i));
        }
    }

    @Test
    void testCreateGame() throws DataAccessException {
        GameData testGameData = new GameData(0, null, null, "testGame",
                new ChessGame());

        GameData createdGame = gameDAO.createGame(testGameData);

        Assertions.assertNotNull(createdGame);
        Assertions.assertEquals(testGameData.gameName(), createdGame.gameName());

        System.out.println(createdGame.gameID());
        System.out.println(createdGame.whiteUsername());
        System.out.println(createdGame.blackUsername());
        System.out.println(createdGame.gameName());

        String gameJson = new Gson().toJson(createdGame.game());
        System.out.println(gameJson);
    }

    @Test
    void testCreateGameNoName() throws DataAccessException {
        GameData testGameData = new GameData(0, "whitePlayer", "blackPlayer", null,
                new ChessGame());

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameDAO.createGame(testGameData);
        });
    }

    @Test
    void testGetGame() throws DataAccessException {
        GameData testGameData = new GameData(0, "whitePlayer", "blackPlayer", "testGame",
                new ChessGame());

        GameData createdGame = gameDAO.createGame(testGameData);

        GameData foundGame = gameDAO.getGame(createdGame.gameID());

        System.out.println(foundGame.gameID());
        System.out.println(foundGame.whiteUsername());
        System.out.println(foundGame.blackUsername());
        System.out.println(foundGame.gameName());
        System.out.println(foundGame.game());

        Assertions.assertInstanceOf(ChessGame.class, foundGame.game());

        Assertions.assertEquals(createdGame.gameID(), foundGame.gameID());
        Assertions.assertEquals(createdGame.whiteUsername(), foundGame.whiteUsername());
        Assertions.assertEquals(createdGame.blackUsername(), foundGame.blackUsername());
        Assertions.assertEquals(createdGame.gameName(), foundGame.gameName());
        Assertions.assertEquals(createdGame.gameID(), foundGame.gameID());
    }

    @Test
    void testGetGameDoesNotExist() throws DataAccessException {
        Assertions.assertNull(gameDAO.getGame(45));
    }

}
