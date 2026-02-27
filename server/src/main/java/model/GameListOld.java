package model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

public class GameListOld extends ArrayList<GameData> {
    public GameListOld() {

    }

    public GameListOld(Collection<GameData> games) {
        super(games);
    }

    public String toString() {
        return new Gson().toJson(this.toArray());
    }
}
