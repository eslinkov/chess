package client;

import dataaccess.DataAccessException;
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
}
