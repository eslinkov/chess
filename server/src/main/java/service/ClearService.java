package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {
    // implements clear endpoint, returns success response

    private final AuthDAO authData;
    private final GameDAO gameData;
    private final UserDAO userData;

    public ClearService(AuthDAO authData, GameDAO gameData, UserDAO userData) {
        this.authData = authData;
        this.gameData = gameData;
        this.userData = userData;
    }



    public void clear() throws DataAccessException {
            authData.clear();
            gameData.clear();
            userData.clear();
    }



}
