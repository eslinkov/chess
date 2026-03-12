package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class UserService {

    private final AuthDAO authDAO;
    private final UserDAO userDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {

        if (registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null){
            throw new DataAccessException("Error: bad request");
        }

        if (userDAO.getUser(registerRequest.username()) != null ) {
            throw new DataAccessException("Error: already taken");
        }

        String hashedPassword = BCrypt.hashpw(registerRequest.password(), BCrypt.gensalt());

        UserData newUserData = new UserData(registerRequest.username(), hashedPassword, registerRequest.email());

        userDAO.createUser(newUserData);

        String authToken = generateToken();

        AuthData newAuthData = new AuthData(authToken, newUserData.username());

        authDAO.createAuth(newAuthData);

        return new RegisterResult(newUserData.username(), newAuthData.authToken());

    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException{

        if (loginRequest.username() == null || loginRequest.password() == null) {
            throw new DataAccessException("Error: bad request");
        }

        UserData userData = userDAO.getUser(loginRequest.username());

        if (userData == null) {
            throw new DataAccessException("Error: unauthorized");
        }

        if (!BCrypt.checkpw(loginRequest.password(), userData.password())) {
            throw new DataAccessException("Error: unauthorized");
        }

        String authToken = generateToken();

        AuthData newAuthData = new AuthData(authToken, loginRequest.username());

        authDAO.createAuth(newAuthData);

        return new LoginResult(loginRequest.username(), authToken);
    }

    public void logout(String authToken) throws DataAccessException {

        if (authDAO.getAuth(authToken) == null) {
            throw new DataAccessException("Error: unauthorized");
        }

        authDAO.deleteAuth(authToken);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

}
