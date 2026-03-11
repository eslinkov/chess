package dataaccess;

import model.AuthData;

import java.sql.SQLException;

import static dataaccess.DatabaseManager.getConnection;

public class SQLAuthDAO implements AuthDAO{

//    public SQLAuthDAO() throws SQLException {
//        configureDatabase();
//    }

    @Override
    public AuthData createAuth(AuthData auth) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }







    // createStatements method to hold all the sql statements that create the table, array of create table statements

    // configureDatabase method to create tables, uses createStatements
}
