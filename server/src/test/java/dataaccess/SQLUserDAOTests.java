package dataaccess;

import org.junit.jupiter.api.Test;

public class SQLUserDAOTests {
    @Test
    public void testTableCreation() throws DataAccessException {
        new SQLUserDAO();
    }
}
