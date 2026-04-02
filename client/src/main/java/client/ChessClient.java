package client;


import model.LoginResult;
import org.junit.jupiter.api.Assertions;

import java.util.Scanner;

public class ChessClient {
    private final ServerFacade server;
    private String authToken;


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
//                    case "list"
//                    case "join"
//                    case "observe"
                    case "logout" -> logout();
                    default -> System.out.println("Unknown command. Type 'help for options.'");
                }

            }

        }
    }

    private void printPrompt() {

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
            if (user_inputs.length != 2) {
                System.out.println("Expected: create <NAME>");
                return;
            }
            var result = server.createGame(user_inputs[1], authToken);
            System.out.println("Created game: " + user_inputs[1] + " gameID: " + result.gameID());
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }


// register testuser1 password test@email.com

}
