package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{

    final private HashMap<String, UserData> userDataHashMap = new HashMap<>();  // key = username

    @Override
    public UserData createUser(UserData user) throws DataAccessException {
        userDataHashMap.put(user.username(), user);

        return user;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return userDataHashMap.get(username);
    }

    @Override
    public void deleteUser(String username) throws DataAccessException {
        userDataHashMap.remove(username);
    }

    @Override
    public void clear() throws DataAccessException {
        userDataHashMap.clear();
    }
}
