package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTests {

    private UserDAO userDAO;
    private AuthDAO authDAO;

    private UserService userService;

    private UserData testUser;






    @BeforeEach
    void setUp() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();

        userService = new UserService(authDAO, userDAO);

        testUser = new UserData("testUsername", "testPassword", "testEmail");


    }

    @Test
    void registerSuccess() throws DataAccessException {

        RegisterRequest testRegisterRequest = new RegisterRequest("testUsername", "testPassword",
                "testEmail");

        RegisterResult testRegisterResult = userService.register(testRegisterRequest);

        Assertions.assertEquals("testUsername", testRegisterResult.username());

        Assertions.assertNotNull(testRegisterResult.authToken());
    }

    @Test
    void duplicateUser() throws DataAccessException {
        RegisterRequest testRegisterRequest = new RegisterRequest("testUsername", "testPassword",
                "testEmail");

        userService.register(testRegisterRequest);

        Assertions.assertThrows(DataAccessException.class, () -> {
            userService.register(testRegisterRequest);
        });


    }

    @Test
    void loginSuccess() throws DataAccessException {

        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());

        userService.register(testRegisterRequest);

        LoginRequest testLoginRequest = new LoginRequest(testUser.username(), testUser.password());

        LoginResult testLoginResult = userService.login(testLoginRequest);

        Assertions.assertEquals(testUser.username(), testLoginResult.username());

        Assertions.assertNotNull(testLoginResult.authToken());

    }

    @Test
    void loginIncorrectPassword() throws DataAccessException {

        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());

        userService.register(testRegisterRequest);

        LoginRequest testIncorrectLoginRequest = new LoginRequest(testUser.username(), "incorrectPassword");

        Assertions.assertThrows(DataAccessException.class, () -> {
            userService.login(testIncorrectLoginRequest);
        });
    }

    @Test
    void loginUserDoesNotExist() throws DataAccessException {

        LoginRequest testNotUser = new LoginRequest(testUser.username(), testUser.password());

        Assertions.assertThrows(DataAccessException.class, () -> {
            userService.login(testNotUser);
        });
    }

    @Test
    void logoutSuccess() throws DataAccessException {

        RegisterRequest testRegisterRequest = new RegisterRequest(testUser.username(), testUser.password(), testUser.email());

        RegisterResult testRegisterResult = userService.register(testRegisterRequest);

        String testAuthToken = testRegisterResult.authToken();

        Assertions.assertDoesNotThrow(() -> {
            userService.logout(testAuthToken);
        });

        Assertions.assertNull(authDAO.getAuth(testAuthToken));

    }

    @Test
    void logoutInvalidToken() throws DataAccessException {

        String invalidAuthToken = "asdf";

        Assertions.assertThrows(DataAccessException.class, () -> {
            userService.logout(invalidAuthToken);
        });


    }
}

