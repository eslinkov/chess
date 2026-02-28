package dataaccess;

import model.AuthData;

public interface AuthDAO {
    AuthData createAuth(AuthData auth) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void clear() throws DataAccessException;;
}
