package dataaccess;

import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dataaccess.DatabaseManager.getConnection;

public class SQLAuthDAO implements AuthDAO{

    public SQLAuthDAO() throws DataAccessException {
        DatabaseManager.configureDatabase(createStatements);
    }

    @Override
    public AuthData createAuth(AuthData auth) throws DataAccessException {
        var statement = "INSERT INTO auth (authtoken, username) VALUES(?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, auth.authToken());
                stmt.setString(2, auth.username());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to create authentication data", e);
        }
        return auth;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authtoken=?";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, authToken);
                stmt.executeUpdate();
            }
        }  catch (SQLException e) {
            throw new DataAccessException("unable to delete auth data", e);
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        var statement = "SELECT * FROM auth WHERE authtoken=?";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(statement)) {
                stmt.setString(1, authToken);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new AuthData(rs.getString("authtoken"),
                                rs.getString("username"));
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to get auth data", e);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auth";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(statement)) {
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to delete database", e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS auth (
                `authtoken` varchar(256) NOT NULL,
                `username` varchar(256) NOT NULL,
                PRIMARY KEY (`authtoken`),
                INDEX(authtoken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
