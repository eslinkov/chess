package model;

// contains username, and authtoken

public record RegisterResult(
        String username,
        String authToken) {
}
