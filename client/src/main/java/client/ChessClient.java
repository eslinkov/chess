package client;
import chess.*;
import com.google.gson.Gson;
import ui.BoardDrawer;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.util.*;

import static ui.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private String authToken;
    private final Map<Integer, Integer> gameMap = new HashMap<>();
    private WebSocketFacade ws;
    private ChessGame currentGame;
    private boolean whitePerspective = true;
    private int currentGameID;
    private boolean inGame = false;


    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
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

    @Override
    public void notify(String message) {
        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
        switch (serverMessage.getServerMessageType()) {
            case LOAD_GAME -> {
                LoadGameMessage loadGame = new Gson().fromJson(message, LoadGameMessage.class);
                currentGame = loadGame.getGame();
                System.out.println();
                BoardDrawer.drawBoard(currentGame.getBoard(), whitePerspective);
                printPrompt();
            }
            case NOTIFICATION -> {
                NotificationMessage notification = new Gson().fromJson(message, NotificationMessage.class);
                System.out.println();
                System.out.println(SET_TEXT_COLOR_YELLOW + notification.getMessage());
                printPrompt();
            }
            case ERROR -> {
                ErrorMessage error = new Gson().fromJson(message, ErrorMessage.class);
                System.out.println();
                System.out.println(SET_TEXT_COLOR_RED + error.getErrorMessage());
                printPrompt();
            }
        }
    }

    private void printPrompt() {
        System.out.print(RESET_TEXT_COLOR + "[IN_GAME] >>> ");
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

    private void gameplayLoop() {
        Scanner scanner = new Scanner(System.in);
        inGame = true;
        while (inGame) {
            System.out.print(RESET_TEXT_COLOR + "[IN_GAME] >>> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            String command = parts[0].toLowerCase();

            switch (command) {
                case "help" -> System.out.print(SET_TEXT_COLOR_BLUE + gameplayHelp());
                case "redraw" -> redrawBoard();
                case "leave" -> leaveGame();
                case "move" -> movePiece(parts);
                case "resign" -> resignGame(scanner);
                case "highlight" -> highlightMoves(parts);
                default -> System.out.println(RESET_TEXT_COLOR + "Unknown command. Type 'help' for options.");
            }
        }
    }

    private String gameplayHelp() {
        return """
               redraw - redraw the chess board
               leave - leave the current game
               move <FROM> <TO> [PROMOTION] - make a move (e.g. move e2 e4)
               resign - forfeit the game
               highlight <POSITION> - show legal moves for a piece (e.g. highlight e2)
               help - list possible commands
               """;
    }

    private void redrawBoard() {
        if (currentGame != null) {
            BoardDrawer.drawBoard(currentGame.getBoard(), whitePerspective);
        }
    }

    private void leaveGame() {
        try {
            ws.leave(authToken, currentGameID);
            inGame = false;
        } catch (ResponseException e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
        }
    }

    private void movePiece(String[] parts) {
        try {
            if (parts.length < 3) {
                System.out.println(RESET_TEXT_COLOR + "Expected: move <FROM> <TO> [PROMOTION]");
                return;
            }
            ChessPosition from = parsePosition(parts[1]);
            ChessPosition to = parsePosition(parts[2]);
            ChessPiece.PieceType promotion = null;
            if (parts.length >= 4) {
                promotion = parsePieceType(parts[3]);
            }
            ChessMove move = new ChessMove(from, to, promotion);
            ws.makeMove(authToken, currentGameID, move);

        } catch (Exception e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
        }
    }

    private ChessPosition parsePosition(String input) {
        if (input.length() != 2) {
            throw new IllegalArgumentException("Invalid position: " + input);
        }
        int col = input.charAt(0) - 'a' + 1;
        int row = input.charAt(1) - '0';
        if (col < 1 || col > 8 || row < 1 || row > 8) {
            throw new IllegalArgumentException("Invalid position: " + input);
        }
        return new ChessPosition(row, col);
    }

    private ChessPiece.PieceType parsePieceType(String input) {
        return switch (input.toLowerCase()) {
            case "queen" -> ChessPiece.PieceType.QUEEN;
            case "rook" -> ChessPiece.PieceType.ROOK;
            case "bishop" -> ChessPiece.PieceType.BISHOP;
            case "knight" -> ChessPiece.PieceType.KNIGHT;
            default -> throw new IllegalArgumentException("Invalid piece type: " + input);
        };
    }

    private void resignGame(Scanner scanner) {
        System.out.print(RESET_TEXT_COLOR + "Are you sure you want to resign? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("yes")) {
            try {
                ws.resign(authToken, currentGameID);
            } catch (ResponseException e) {
                System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
            }

        } else {
            System.out.println(RESET_TEXT_COLOR + "Resign cancelled.");
        }
    }

    private void highlightMoves(String[] parts) {
        if (parts.length < 2) {
            System.out.println(RESET_TEXT_COLOR + "Expected: highlight <POSITION>");
            return;
        }
        try {
            ChessPosition position = parsePosition(parts[1]);
            Collection<ChessMove> validMoves = currentGame.validMoves(position);
            if(validMoves == null || validMoves.isEmpty()) {
                System.out.println(RESET_TEXT_COLOR + "No logal moves for that piece.");
                return;
            }
            BoardDrawer.drawBoardWithHighlights(currentGame.getBoard(), whitePerspective, position, validMoves);
        } catch (Exception e) {
            System.out.println(SET_TEXT_COLOR_RED + e.getMessage());
        }

    }




}
