package client;

import dataaccess.DataAccessException;
import model.CreateGameResult;
import model.LoginResult;
import model.RegisterResult;
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
    public void sampleTest() {
        Assertions.assertTrue(true);
    }
        // replace with my tests

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

}
