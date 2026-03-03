package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private ClearService clearService;
    private UserData testUser;
    private GameData testGame;
    private AuthData testAuth;




    @BeforeEach
    void setUp() throws DataAccessException {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        clearService = new ClearService(authDAO, gameDAO, userDAO);

        testUser = new UserData("testUsername", "testPassword", "testEmail");
        testGame = new GameData(1234, "whiteUsername", "blackUsername",
                "testGameName", new ChessGame());
        testAuth = new AuthData("authToken", testUser.username());



    }

    @Test
    void clear() throws DataAccessException {
        userDAO.createUser(testUser);
        authDAO.createAuth(testAuth);
        gameDAO.createGame(testGame);

        clearService.clear();

        Assertions.assertNull(userDAO.getUser("testUsername"));
        Assertions.assertNull(gameDAO.getGame(1234));
        Assertions.assertNull(authDAO.getAuth("authToken"));
    }
}