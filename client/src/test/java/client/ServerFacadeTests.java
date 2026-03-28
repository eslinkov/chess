package client;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.SQLGameDAO;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        var url = "http://localhost:" + port;
        facade = new ServerFacade(url);
    }

    @BeforeEach
    void clearDB() throws ResponseException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void testRegister() throws ResponseException {
        RegisterResult authData = facade.register("player1", "password", "test@gmail.com");

        Assertions.assertNotNull(authData.authToken());
        Assertions.assertNotNull(authData.username());
        Assertions.assertEquals("player1", authData.username());
    }

    @Test
    public void testRegisterUserAlreadyExists() throws ResponseException {
        facade.register("player1", "password1", "p1@gmail.com");

        Assertions.assertThrows(ResponseException.class, () -> {
            facade.register("player1", "password2", "p2@gmail.com");
        });
    }

    @Test
    public void testLoginSuccess() throws ResponseException {
        facade.register("player1", "password", "test@gmail.com");

        LoginResult authData = facade.login("player1", "password");

        Assertions.assertNotNull(authData.authToken());
        Assertions.assertNotNull(authData.username());
    }

    @Test
    public void testLoginUserDoesNotExist() {
        Assertions.assertThrows(ResponseException.class, () -> {
            facade.login("player1", "password");
        });
    }

    @Test
    public void testLoginIncorrectPassword() throws ResponseException {
        facade.register("player1", "password", "test@gmail.com");

        Assertions.assertThrows(ResponseException.class, () -> {
            facade.login("player1", "PaSsWoRd");
        });
    }

    @Test
    public void testLogoutSuccess() throws ResponseException {
        facade.register("player1", "password", "test@gmail.com");
        LoginResult authData = facade.login("player1", "password");

        Assertions.assertDoesNotThrow(() -> {
            facade.logout(authData.authToken());
        });
    }

    @Test
    public void testLogoutInvalidToken() {
        String authData = "asdf";

        Assertions.assertThrows(ResponseException.class, () -> {
            facade.logout(authData);
        });
    }

    @Test
    public void testCreateGameSuccess() throws ResponseException {
        facade.register("player1", "password", "test@gmail.com");
        LoginResult authData = facade.login("player1", "password");

        CreateGameResult newGameID = facade.createGame("New Game", authData.authToken());

        Assertions.assertNotNull(newGameID);
        Assertions.assertTrue(newGameID.gameID() > 0);
    }

    @Test
    public void testCreateGameInvalidAuth() {
        Assertions.assertThrows(ResponseException.class, () -> {
            facade.createGame("New Game", "asdf");
        });
    }

    @Test
    public void testListGames() throws ResponseException {
        facade.register("player1", "password", "test@gmail.com");
        LoginResult authData = facade.login("player1", "password");

        facade.createGame("New Game 1", authData.authToken());
        facade.createGame("New Game 2", authData.authToken());
        facade.createGame("New Game 3", authData.authToken());

        GameList gameList = facade.listGames(authData.authToken());

        Assertions.assertNotNull(gameList.games());
        Assertions.assertEquals(3, gameList.games().size());
    }

    @Test
    void testListGamesInvalidAuthToken() {
        Assertions.assertThrows(ResponseException.class, () -> {
            facade.listGames("asdf");
        });
    }

    @Test
    void testJoinGameSuccessWhite() throws ResponseException, DataAccessException {
        facade.register("player1", "password", "test@gmail.com");
        LoginResult authData = facade.login("player1", "password");

        CreateGameResult newGameID = facade.createGame("New Game", authData.authToken());
        facade.joinGame("WHITE", newGameID.gameID(), authData.authToken());
        GameDAO gameDAO = new SQLGameDAO();
        GameData game = gameDAO.getGame(newGameID.gameID());

        Assertions.assertEquals("player1", game.whiteUsername());
    }

    @Test
    void testJoinGameSuccessBlack() throws ResponseException, DataAccessException {
        facade.register("player1", "password", "test@gmail.com");
        LoginResult authData = facade.login("player1", "password");

        CreateGameResult newGameID = facade.createGame("New Game", authData.authToken());
        facade.joinGame("BLACK", newGameID.gameID(), authData.authToken());
        GameDAO gameDAO = new SQLGameDAO();
        GameData game = gameDAO.getGame(newGameID.gameID());

        Assertions.assertEquals("player1", game.blackUsername());
    }

    @Test
    void testJoinGameColorTaken() throws ResponseException {
        facade.register("player1", "password", "test@gmail.com");
        facade.register("player2", "password2", "test2@gmail.com");

        LoginResult authData1 = facade.login("player1", "password");
        LoginResult authData2 = facade.login("player2", "password2");

        CreateGameResult newGameID = facade.createGame("New Game", authData1.authToken());

        facade.joinGame("WHITE", newGameID.gameID(), authData1.authToken());

        Assertions.assertThrows(ResponseException.class, () -> {
            facade.joinGame("WHITE", newGameID.gameID(), authData2.authToken());
        });
    }

}
