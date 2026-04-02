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
            String[] user_inputs = line.split(" ");
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
                // postlogin commands

            }

        }
    }

    private void printPrompt() {

    }

    private void register(String[] user_inputs) {
        try {
            if (user_inputs.length != 4) {
                System.out.println("Expected: register <username>, <password>, <email>");
                return;
            }
            var result = server.register(user_inputs[1], user_inputs[2], user_inputs[3]);
            authToken = result.authToken();
            System.out.println("Welcome " + result.username() + "!");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }

    }

    private void login(String[] user_inputs) throws ResponseException {
        String username = user_inputs[1];
        String password = user_inputs[2];

    }




}
