package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import model.*;
import service.*;
import java.util.Map;


public class Server {

    private final Javalin javalin;

    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    private final ClearService clearService;
    private final UserService userService;
    private final GameService gameService;

    private final Gson serializer = new Gson();

    public Server() {
        try {
            userDAO = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            authDAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            gameDAO = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        clearService = new ClearService(authDAO, gameDAO, userDAO);
        userService = new UserService(authDAO, userDAO);
        gameService = new GameService(authDAO, gameDAO);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.delete("/db", this::handleClear);
        javalin.delete("/session", this::handleLogout);
        javalin.post("/user", this::handleRegister);
        javalin.post("/session", this::handleLogin);
        javalin.get("/game", this::handleListGames);
        javalin.post("/game", this::handleCreateGame);
        javalin.put("/game", this::handleJoinGame);
        javalin.exception(DataAccessException.class, this::exceptionHandler);
    }

    private void exceptionHandler(DataAccessException e, Context context) {
        int statusCode;
        String body;

        if (e.getMessage().contains("bad request")) {
            statusCode = 400;
            body = e.getMessage();
        } else if (e.getMessage().contains("unauthorized")) {
            statusCode = 401;
            body = e.getMessage();
        } else if (e.getMessage().contains("already taken")) {
            statusCode = 403;
            body = e.getMessage();
        } else {
            statusCode = 500;
            body = "Error: " + e.getMessage();
        }

        context.status(statusCode);

        context.result(serializer.toJson(Map.of("message", body)));
    }

    private void handleJoinGame(Context context) throws DataAccessException {
        JoinRequest joinRequest = serializer.fromJson(context.body(), JoinRequest.class);

        gameService.joinGame(joinRequest, context.header("authorization"));

        context.status(200);

        context.result("{}");
    }

    private void handleCreateGame(Context context) throws DataAccessException {
        CreateGameRequest createRequest = serializer.fromJson(context.body(), CreateGameRequest.class);

        CreateGameResult createResult = gameService.createGame(createRequest, context.header("authorization"));

        context.status(200);

        context.result(serializer.toJson(createResult));
    }

    private void handleListGames(Context context) throws DataAccessException {
        GameList gameList = gameService.listGames(context.header("authorization"));

        context.status(200);

        context.result(serializer.toJson(gameList));
    }

    private void handleLogout(Context context) throws DataAccessException {
        userService.logout(context.header("authorization"));

        context.status(200);

        context.result("{}");
    }

    private void handleLogin(Context context) throws DataAccessException {
        LoginRequest loginRequest = serializer.fromJson(context.body(), LoginRequest.class);

        LoginResult loginResult = userService.login(loginRequest);

        context.status(200);

        context.result(serializer.toJson(loginResult));
    }

    private void handleRegister(Context context) throws DataAccessException {
        RegisterRequest newRegisterRequest = serializer.fromJson(context.body(), RegisterRequest.class);

        RegisterResult registerResult = userService.register(newRegisterRequest);

        context.status(200);

        context.result(serializer.toJson(registerResult));

    }

    private void handleClear(Context context) throws DataAccessException {
        clearService.clear();

        context.status(200);

        context.result("{}");
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);

        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
