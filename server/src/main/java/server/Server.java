package server;

import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }




//    private static <T> T getBodyObject(Context context, Class<T> clazz) {
//        var bodyObject = new Gson().fromJson(context.body(), clazz);
//
//        if (bodyObject == null) {
//            throw new RuntimeException("missing required body");
//        }
//
//        return bodyObject;
//    }



    public void stop() {
        javalin.stop();
    }
}
