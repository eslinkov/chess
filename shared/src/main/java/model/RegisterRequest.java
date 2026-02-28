package model;

// contains a username, password, and email

public record RegisterRequest(
        String username,
        String password,
        String email
) {
}
