package model;

// contains username and password

public record LoginRequest(
        String username,
        String password
) {
}
