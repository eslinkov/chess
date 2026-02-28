package model;

import java.util.Collection;

// result of get list request

public record GameList(Collection<GameData> games) {
}

