package client;
import chess.ChessBoard;
import ui.BoardDrawer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade server;
    private String authToken;
    private final Map<Integer, Integer> gameMap = new HashMap<>();


    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public void run() {
        System.out.println("♕ Welcome to 240 chess. Type Help to get started. ♕");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            if (authToken == null) {
                System.out.print(RESET_TEXT_COLOR + "[LOGGED_OUT] >>> ");
            } else {
                System.out.print(RESET_TEXT_COLOR + "[LOGGED_IN] >>> ");
            }

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] userInputs = line.split("\\s+");
            String command = userInputs[0].toLowerCase();

            if (authToken == null) {
                switch (command) {
                    case "help" -> System.out.print(SET_TEXT_COLOR_BLUE + help());
                    case "quit" -> result = "quit";
                    case "login" -> login(userInputs);
                    case "register" -> register(userInputs);
                    default -> System.out.println(RESET_TEXT_COLOR + "Unknown command. Type 'help for options.'");
                }
            } else {
                switch (command) {
                    case "help" -> System.out.print(SET_TEXT_COLOR_BLUE + help());
                    case "quit" -> result = "quit";
                    case "create" -> create(userInputs);
                    case "list" -> list();
                    case "join" -> play(userInputs);
                    case "observe" -> observe(userInputs);
                    case "logout" -> logout();
                    default -> System.out.println(RESET_TEXT_COLOR + "Unknown command. Type 'help for options.'");
                }

            }

        }
    }

    private String help() {
        if (authToken == null) {
            return """
               register <USERNAME> <PASSWORD> <EMAIL> - to create an account
               login <USERNAME> <PASSWORD> - to play chess
               quit - stop playing chess
               help - list possible commands
               """;
        } else {
            return """
               create <NAME> - to create an new game
               list - show all games
               join <ID> [WHITE|BLACK] - join a game
               observe <ID> - watch an ongoing game
               logout - logout of account
               quit - stop playing chess
               help - list possible commands
               """;
        }


    }

    private void register(String[] userInputs) {
        try {
            if (userInputs.length != 4) {
                System.out.println(RESET_TEXT_COLOR + "Expected: register <username> <password> <email>");
                return;
            }
            var result = server.register(userInputs[1], userInputs[2], userInputs[3]);
            authToken = result.authToken();
            System.out.println(RESET_TEXT_COLOR + "Welcome " + result.username() + "!");
        } catch (ResponseException e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
        }

    }

    private void login(String[] userInputs) {
        try {
            if (userInputs.length != 3) {
                System.out.println(RESET_TEXT_COLOR + "Expected: login <username> <password>");
                return;
            }
            var result = server.login(userInputs[1], userInputs[2]);
            authToken = result.authToken();
            System.out.println(RESET_TEXT_COLOR + "Welcome back " + result.username() + "!");
        } catch (ResponseException e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
        }
    }

    private void logout() {
        try {
            server.logout(authToken);
            authToken = null;
            System.out.println(RESET_TEXT_COLOR + "Successfully logged out.");
        } catch (ResponseException e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
        }

    }

    private void create(String[] userInputs) {
        try {
            if (userInputs.length < 2) {
                System.out.println(RESET_TEXT_COLOR + "Expected: create <NAME>");
                return;
            }
            String gameName = String.join(" ", Arrays.copyOfRange(userInputs, 1, userInputs.length));
            var result = server.createGame(gameName, authToken);
            System.out.println(RESET_TEXT_COLOR + "Created game: " + gameName);
        } catch (ResponseException e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
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

                System.out.println(RESET_TEXT_COLOR + index + ". " + game.gameName() + " | " + "White: " + white + " | " +
                        "Black: " + black);
                index++;
            }
        } catch (ResponseException e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
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

    private void play(String[] userInputs) {
        try {
            if (userInputs.length != 3) {
                System.out.println(RESET_TEXT_COLOR + "Expected: join <ID> [WHITE|BLACK]");
                return;
            }
            if (!userInputs[2].toUpperCase().equals("WHITE") && !userInputs[2].toUpperCase().equals("BLACK")) {
                System.out.println(RESET_TEXT_COLOR + "Game color must be WHITE or BLACK");
                return;
            }
            updateList();
            int listNumber = Integer.parseInt(userInputs[1]);
            int gameID = gameMap.get(listNumber);
            server.joinGame(userInputs[2].toUpperCase(), gameID, authToken);
            System.out.println(RESET_TEXT_COLOR + "Joined game as " + userInputs[2]);

            ChessBoard board = new ChessBoard();
            board.resetBoard();
            BoardDrawer.drawBoard(board, !userInputs[2].equalsIgnoreCase("BLACK"));
        } catch (ResponseException e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(RESET_TEXT_COLOR + "Please enter a valid game ID.");
        }
    }

    private void observe(String[] userInputs) {
        try {
            if (userInputs.length != 2) {
                System.out.println(RESET_TEXT_COLOR + "Expected: observe <ID>");
                return;
            }
            updateList();
            int listNumber = Integer.parseInt(userInputs[1]);
            int gameID = gameMap.get(listNumber);
            System.out.println(RESET_TEXT_COLOR + "Observing game: " + userInputs[1]);

            ChessBoard board = new ChessBoard();
            board.resetBoard();
            BoardDrawer.drawBoard(board, true);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(RESET_TEXT_COLOR + "Please enter a valid game ID.");
        } catch (ResponseException e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
        }
    }
}
