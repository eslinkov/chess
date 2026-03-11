package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;



public class SQLUserDAOTests {

    private UserData testUser;
    private UserDAO userDAO;


    @BeforeEach
    void setUp() throws DataAccessException {
        userDAO = new SQLUserDAO();
        userDAO.clear();
        testUser = new UserData("testUsername", "testPassword", "testEmail");
    }

    @Test
    void testClear() throws DataAccessException {
        userDAO.createUser(testUser);

        Assertions.assertDoesNotThrow(() -> {

            userDAO.clear();
        });

        userDAO.createUser(testUser);
        userDAO.clear();
        Assertions.assertNull(userDAO.getUser(testUser.username()));
    }

    @Test
    public void testTableCreation() throws DataAccessException {
        new SQLUserDAO();
    }

    @Test
    void testCreateUser() throws DataAccessException {

        UserData createdUser = userDAO.createUser(testUser);

        Assertions.assertNotNull(createdUser);

        Assertions.assertEquals("testUsername", createdUser.username());

    }

    @Test
    void testCreateUserSameUsername() throws DataAccessException {
        userDAO.createUser(testUser);

        Assertions.assertThrows(DataAccessException.class, () -> {
            userDAO.createUser(testUser);
        });
    }

    @Test
    void testGetUser() throws DataAccessException {
        userDAO.createUser(testUser);

        UserData foundUser = userDAO.getUser(testUser.username());

        Assertions.assertEquals("testUsername", foundUser.username());
        Assertions.assertEquals("testPassword", foundUser.password());
        Assertions.assertEquals("testEmail", foundUser.email());
    }

    @Test
    void testGetUserDoesNotExist() throws DataAccessException {
        Assertions.assertNull(userDAO.getUser(testUser.username()));
    }

    @Test
    void testDeleteUser() throws DataAccessException {
        userDAO.createUser(testUser);

        userDAO.deleteUser(testUser.username());

        Assertions.assertNull(userDAO.getUser(testUser.username()));
    }

    @Test
    void testDeleteUserDoesntExist() throws DataAccessException {
        userDAO.deleteUser("asdf");
        Assertions.assertNull(userDAO.getUser("asdf"));
    }

}
