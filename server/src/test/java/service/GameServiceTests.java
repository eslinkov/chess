package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTests {

    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    private GameService gameService;
    private UserService userService;

    private UserData testUser;

//    private String testAuthToken;



    @BeforeEach
    void setUp() {
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        userDAO = new MemoryUserDAO();

        gameService = new GameService(authDAO, gameDAO);
        userService = new UserService(authDAO, userDAO);

        testUser = new UserData("testUsername", "testPassword", "testEmail");
    }

//    @BeforeEach
//    void registerUser() throws DataAccessException {
//        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());
//
//
//
//        RegisterResult testRegisterResult = userService.register(testRegisterRequest);
//
//        testAuthToken = testRegisterResult.authToken();
//
//    }



    @Test
    void createGame() throws DataAccessException {
        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());

        RegisterResult testRegisterResult = userService.register(testRegisterRequest);

        String testAuthToken = testRegisterResult.authToken();

        CreateGameRequest testCreateGameRequest = new CreateGameRequest("My Game");

        CreateGameResult testCreateGameResult = gameService.createGame(testCreateGameRequest, testAuthToken);


        Assertions.assertTrue(testCreateGameResult.gameID() > 0);

        Assertions.assertNotNull(gameDAO.getGame(testCreateGameResult.gameID()));

    }

    @Test
    void createGameInvalidAuthToken() throws DataAccessException {
        String invalidAuthToken = "asdf";

        CreateGameRequest testCreateGameRequest = new CreateGameRequest("My Game");

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameService.createGame(testCreateGameRequest, invalidAuthToken);
        });

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameService.createGame(testCreateGameRequest, null);
        });
    }

    @Test
    void createGameNoNamePassedIn() throws DataAccessException {
        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());

        RegisterResult testRegisterResult = userService.register(testRegisterRequest);

        String testAuthToken = testRegisterResult.authToken();

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameService.createGame(new CreateGameRequest(null), testAuthToken);
        });
    }

    @Test
    void joinGameSuccessWhite() throws DataAccessException {
        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());

        RegisterResult testRegisterResult = userService.register(testRegisterRequest);

        String testAuthToken = testRegisterResult.authToken();

        CreateGameRequest testCreateGameRequest = new CreateGameRequest("My Game");

        CreateGameResult testCreateGameResult = gameService.createGame(testCreateGameRequest, testAuthToken);


        JoinRequest testJoinRequest = new JoinRequest("WHITE", testCreateGameResult.gameID());

        gameService.joinGame(testJoinRequest, testAuthToken);

        GameData joinedGame = gameDAO.getGame(testCreateGameResult.gameID());

        Assertions.assertEquals(testUser.username(), joinedGame.whiteUsername());

    }

    @Test
    void joinGameSuccessBlack() throws DataAccessException {
        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());

        RegisterResult testRegisterResult = userService.register(testRegisterRequest);

        String testAuthToken = testRegisterResult.authToken();

        CreateGameRequest testCreateGameRequest = new CreateGameRequest("My Game");

        CreateGameResult testCreateGameResult = gameService.createGame(testCreateGameRequest, testAuthToken);


        JoinRequest testJoinRequest = new JoinRequest("BLACK", testCreateGameResult.gameID());

        gameService.joinGame(testJoinRequest, testAuthToken);

        GameData joinedGame = gameDAO.getGame(testCreateGameResult.gameID());

        Assertions.assertEquals(testUser.username(), joinedGame.blackUsername());

    }

    @Test
    void joinGameColorChosen() throws DataAccessException {
        RegisterRequest registerUser1 = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());
        RegisterRequest registerUser2 = new RegisterRequest("user2", "password2", "email2");


        RegisterResult registerUser1Result = userService.register(registerUser1);
        RegisterResult registerUser2Result = userService.register(registerUser2);


        CreateGameRequest testCreateGameRequest = new CreateGameRequest("My Game");

        CreateGameResult newGameResult = gameService.createGame(testCreateGameRequest, registerUser1Result.authToken());

        JoinRequest user1JoinRequest = new JoinRequest("WHITE", newGameResult.gameID());
        gameService.joinGame(user1JoinRequest, registerUser1Result.authToken());

        JoinRequest user2JoinRequest = new JoinRequest("WHITE", newGameResult.gameID());

        Assertions.assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(user2JoinRequest, registerUser2Result.authToken());
        });

    }

    @Test
    void listGamesSuccess() throws DataAccessException {
        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());

        RegisterResult testRegisterResult = userService.register(testRegisterRequest);

        for (int i = 0; i < 6; i++) {
            String newGameName = String.format("New Game %s", i);

            CreateGameRequest testCreateGameRequest = new CreateGameRequest(newGameName);

            gameService.createGame(testCreateGameRequest, testRegisterResult.authToken());
        }

        GameList listGames = gameService.listGames(testRegisterResult.authToken());

        Assertions.assertNotNull(listGames.games());

        Assertions.assertEquals(6, listGames.games().size());
    }

    @Test
    void listGamesInvalidAuthToken() {
        Assertions.assertThrows(DataAccessException.class, () -> {
            gameService.listGames("asdf");
        });
    }
}