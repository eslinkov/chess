package client;
import chess.ChessBoard;
import ui.BoardDrawer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChessClient {
    private final ServerFacade server;
    private String authToken;
    private final Map<Integer, Integer> gameMap = new HashMap<>();


    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public void run() {
        System.out.println("Welcome to 240 chess. Type Help to get started.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            if (authToken == null) {
                System.out.print("[LOGGED_OUT] >>> ");
            } else {
                System.out.print("[LOGGED_IN] >>> ");
            }

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] user_inputs = line.split("\\s+");
            String command = user_inputs[0].toLowerCase();

            if (authToken == null) {
                switch (command) {
                    case "help" -> System.out.println("Prelogin commands: register, login, quit, help");
                    case "quit" -> result = "quit";
                    case "login" -> login(user_inputs);
                    case "register" -> register(user_inputs);
                    default -> System.out.println("Unknown command. Type 'help for options.'");
                }
            } else {
                switch (command) {
                    case "help" -> System.out.println("Postlogin commands: create, list, join, observe, logout, quit, help");
                    case "quit" -> result = "quit";
                    case "create" -> create(user_inputs);
                    case "list" -> list();
                    case "join" -> play(user_inputs);
                    case "observe" -> observe(user_inputs);
                    case "logout" -> logout();
                    default -> System.out.println("Unknown command. Type 'help for options.'");
                }

            }

        }
    }

    private void register(String[] user_inputs) {
        try {
            if (user_inputs.length != 4) {
                System.out.println("Expected: register <username> <password> <email>");
                return;
            }
            var result = server.register(user_inputs[1], user_inputs[2], user_inputs[3]);
            authToken = result.authToken();
            System.out.println("Welcome " + result.username() + "!");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }

    }

    private void login(String[] user_inputs) {
        try {
            if (user_inputs.length != 3) {
                System.out.println("Expected: login <username> <password>");
                return;
            }
            var result = server.login(user_inputs[1], user_inputs[2]);
            authToken = result.authToken();
            System.out.println("Welcome back " + result.username() + "!");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void logout() {
        try {
            server.logout(authToken);
            authToken = null;
            System.out.println("Successfully logged out.");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }

    }

    private void create(String[] user_inputs) {
        try {
            if (user_inputs.length < 2) {
                System.out.println("Expected: create <NAME>");
                return;
            }
            String gameName = String.join(" ", Arrays.copyOfRange(user_inputs, 1, user_inputs.length));
            var result = server.createGame(gameName, authToken);
            System.out.println("Created game: " + gameName);
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void list() {
        try {
            var gameList = server.listGames(authToken);
            gameMap.clear();
            int index = 1;
            for (var game : gameList.games()) {
                gameMap.put(index, game.gameID());

                String white = game.whiteUsername() != null ? game.whiteUsername() : "--";
                String black = game.blackUsername() != null ? game.blackUsername() : "--";

                System.out.println(index + ". " + game.gameName() + " | " + "White: " + white + " | " +
                        "Black: " + black);
                index++;
            }
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateList() throws ResponseException {
        var gameList = server.listGames(authToken);
        gameMap.clear();
        int index = 1;
        for (var game : gameList.games()) {
            gameMap.put(index, game.gameID());
            index++;
        }
    }

    private void play(String[] user_inputs) {
        try {
            if (user_inputs.length != 3) {
                System.out.println("Expected: join <ID> [WHITE|BLACK]");
                return;
            }
            if (!user_inputs[2].toUpperCase().equals("WHITE") && !user_inputs[2].toUpperCase().equals("BLACK")) {
                System.out.println("Game color must be WHITE or BLACK");
                return;
            }
            updateList();
            int listNumber = Integer.parseInt(user_inputs[1]);
            int gameID = gameMap.get(listNumber);
            server.joinGame(user_inputs[2].toUpperCase(), gameID, authToken);
            System.out.println("Joined game as " + user_inputs[2]);

            ChessBoard board = new ChessBoard();
            board.resetBoard();
            BoardDrawer.drawBoard(board, !user_inputs[2].equalsIgnoreCase("BLACK"));
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Please enter a valid game ID.");
        }
    }

    private void observe(String[] user_inputs) {
        try {
            if (user_inputs.length != 2) {
                System.out.println("Expected: observe <ID>");
                return;
            }
            updateList();
            int listNumber = Integer.parseInt(user_inputs[1]);
            int gameID = gameMap.get(listNumber);
            System.out.println("Observing game: " + user_inputs[1]);

            ChessBoard board = new ChessBoard();
            board.resetBoard();
            BoardDrawer.drawBoard(board, true);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Please enter a valid game ID.");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }
}
