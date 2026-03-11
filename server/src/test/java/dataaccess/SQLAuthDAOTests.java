package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

public class SQLAuthDAOTests {

    private AuthData testAuthData;

    private AuthDAO authDAO;

    @BeforeEach
    void setup() throws DataAccessException {
        authDAO = new SQLAuthDAO();
        authDAO.clear();
        testAuthData = new AuthData("testAuthToken", "testUsername");
    }

    @Test
    public void testTableCreation() throws DataAccessException {
        new SQLAuthDAO();
    }

    @Test
    void testClear() throws DataAccessException {
        authDAO.createAuth(testAuthData);

        Assertions.assertDoesNotThrow(() -> {
            authDAO.clear();
        });

        authDAO.createAuth(testAuthData);
        authDAO.clear();
        Assertions.assertNull(authDAO.getAuth(testAuthData.authToken()));
    }

    @Test
    void testCreateAuth() throws DataAccessException {
        AuthData createdAuth = authDAO.createAuth(testAuthData);

        Assertions.assertNotNull(createdAuth);

        Assertions.assertEquals(testAuthData.authToken(), createdAuth.authToken());
        Assertions.assertEquals(testAuthData.username(), createdAuth.username());
    }

    @Test
    void testCreateAuthAlreadyExists() throws DataAccessException {
        authDAO.createAuth(testAuthData);

        Assertions.assertThrows(DataAccessException.class, () -> {
            authDAO.createAuth(testAuthData);
        });
    }

    @Test
    void testGetAuth() throws DataAccessException {
        authDAO.createAuth(testAuthData);

        AuthData foundAuth = authDAO.getAuth(testAuthData.authToken());

        Assertions.assertEquals(testAuthData.authToken(), foundAuth.authToken());
        Assertions.assertEquals(testAuthData.username(), foundAuth.username());
    }

    @Test
    void testGetAuthDoesNotExist() throws DataAccessException {
        Assertions.assertNull(authDAO.getAuth(testAuthData.authToken()));
    }

    @Test
    void testDeleteAuth() throws DataAccessException {
        authDAO.createAuth(testAuthData);

        authDAO.deleteAuth(testAuthData.authToken());

        Assertions.assertNull(authDAO.getAuth(testAuthData.authToken()));
    }

    @Test
    void testDeleteAuthDoesntExist() throws DataAccessException {
        authDAO.deleteAuth("asdf");
        Assertions.assertNull(authDAO.getAuth("asdf"));
    }
}
