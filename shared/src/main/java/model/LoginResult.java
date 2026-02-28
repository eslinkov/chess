package model;

// contains username, authTOken

public record LoginResult(
        String username,
        String authToken) {
}
