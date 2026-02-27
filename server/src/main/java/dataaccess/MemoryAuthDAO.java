package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, AuthData> authDataHashMap = new HashMap<>();

    public AuthData createAuth(AuthData auth) throws DataAccessException {
        authDataHashMap.put(auth.authToken(), auth);

        return auth;
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return authDataHashMap.get(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        authDataHashMap.remove(authToken);
    }

    public void clear() throws DataAccessException {
        authDataHashMap.clear();
    }

}
