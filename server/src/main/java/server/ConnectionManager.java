package server;


import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<String, Session>> gameConnections
            = new ConcurrentHashMap<>();

    public void add(int gameID, String username, Session session) {
        gameConnections.computeIfAbsent(gameID, k -> new ConcurrentHashMap<>()).put(username, session);
    }

    public void remove(int gameID, String username) {
        var connections = gameConnections.get(gameID);
        if (connections != null) {
            connections.remove(username);
        }
    }

    public void broadcast(int gameID, String excludeUsername, String message) throws IOException {
        var connections = gameConnections.get(gameID);
        if (connections == null) {
            return;
        }
        List<String> closedConnections = new ArrayList<>();
        for (var entry : connections.entrySet()) {
            if (entry.getKey().equals(excludeUsername)) {
                continue;
            }
            if (entry.getValue().isOpen()) {
                entry.getValue().getRemote().sendString(message);
            } else {
                closedConnections.add(entry.getKey());
            }
        }
        for (String closed : closedConnections) {
            connections.remove(closed);
        }
    }

    public void sendToUser(int gameID, String username, String message) throws IOException {
        var connections = gameConnections.get(gameID);
        if (connections == null) {
            return;
        }
        Session session = connections.get(username);
        if (session != null && session.isOpen()) {
            session.getRemote().sendString(message);
        }
    }
}
