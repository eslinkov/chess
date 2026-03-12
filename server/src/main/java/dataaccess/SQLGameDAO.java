package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.GameList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLGameDAO implements GameDAO {

    public SQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public GameData createGame(GameData gameData) throws DataAccessException {
        var statement = "INSERT INTO games (white_username, black_username, game_name, game) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(statement, PreparedStatement.RETURN_GENERATED_KEYS)) {
                String gameJson = new Gson().toJson(gameData.game());
                stmt.setString(1, gameData.whiteUsername());
                stmt.setString(2, gameData.blackUsername());
                stmt.setString(3, gameData.gameName());
                stmt.setString(4, gameJson);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                int game_id = rs.getInt(1);

                return new GameData(game_id, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(),
                        gameData.game());
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to insert to game table", e);
        }

    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        var statement = "SELECT * FROM games WHERE game_id=?";

        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(statement)) {
                stmt.setInt(1, gameID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        ChessGame game = new Gson().fromJson(rs.getString("game"), ChessGame.class);
                        return new GameData(gameID, rs.getString("white_username"), rs.getString("black_username"),
                                rs.getString("game_name"), game);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to get game", e);
        }
    }

    @Override
    public GameList listGames() throws DataAccessException {
        return null;
    }

    @Override
    public GameData updateGame(GameData game) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
                `game_id` int NOT NULL AUTO_INCREMENT,
                `white_username` varchar(256),
                `black_username` varchar(256),
                `game_name` varchar(256) NOT NULL,
                `game` TEXT NOT NULL,
                PRIMARY KEY (`game_id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("unable to configure database", ex);
        }
    }
}
