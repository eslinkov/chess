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

}
