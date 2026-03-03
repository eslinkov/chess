package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import model.*;
import org.jetbrains.annotations.NotNull;
import service.*;

public class Server {

    private final Javalin javalin;

    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    private final ClearService clearService;
    private final UserService userService;
    private final GameService gameService;



    public Server() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

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
    }

    public void handleJoinGame(Context context) throws DataAccessException {
        var serializer = new Gson();
        JoinRequest joinRequest = serializer.fromJson(context.body(), JoinRequest.class);

        gameService.joinGame(joinRequest, context.header("authorization"));

        context.status(200);

        context.result("{}");
    }

    public void handleCreateGame(Context context) throws DataAccessException {
        var serializer = new Gson();
        CreateGameRequest createRequest = serializer.fromJson(context.body(), CreateGameRequest.class);

        CreateGameResult createResult = gameService.createGame(createRequest, context.header("authorization"));

        context.status(200);
        context.result(serializer.toJson(createResult));
    }

    public void handleListGames(Context context) throws DataAccessException {
        var serializer = new Gson();
        GameList listGames = gameService.listGames(context.header("authorization"));

        context.status(200);
        context.result(serializer.toJson(listGames));
    }

    public void handleLogout(Context context) throws DataAccessException {
        userService.logout(context.header("authorization"));

        context.status(200);

        context.result("{}");
    }

    public void handleLogin(Context context) throws DataAccessException {
        var serializer = new Gson();
        LoginRequest loginRequest = serializer.fromJson(context.body(), LoginRequest.class);
        LoginResult loginResult = userService.login(loginRequest);

        context.status(200);
        context.result(serializer.toJson(loginResult));
    }

    public void handleRegister(Context context) throws DataAccessException {
        var serializer = new Gson();
        RegisterRequest newRegisterRequest = serializer.fromJson(context.body(), RegisterRequest.class);
        RegisterResult registerResult = userService.register(newRegisterRequest);

        context.status(200);
        context.result(serializer.toJson(registerResult));

    }

    public void handleClear(Context context) throws DataAccessException {
        clearService.clear();
        context.status(200);
        context.result("{}");
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }






//    private static <T> T getBodyObject(Context context, Class<T> clazz) {
//        var bodyObject = new Gson().fromJson(context.body(), clazz);
//
//        if (bodyObject == null) {
//            throw new RuntimeException("missing required body");
//        }
//
//        return bodyObject;
//    }



    public void stop() {
        javalin.stop();
    }
}
