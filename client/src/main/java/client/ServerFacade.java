package client;

import com.google.gson.Gson;
import model.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {
    private String serverUrl;
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public RegisterResult register(String username, String password, String email) throws ResponseException {
        RegisterRequest registerRequest = new RegisterRequest(username, password, email);
        var request = buildRequest("POST", "/user", registerRequest, null);
        var response = sendRequest(request);
        return handleResponse(response, RegisterResult.class);

    }

    public LoginResult login(String username, String password) throws ResponseException {
        LoginRequest loginRequest = new LoginRequest(username, password);
        var request = buildRequest("POST", "/session", loginRequest, null);
        var response = sendRequest(request);
        return handleResponse(response, LoginResult.class);
    }

    public void logout(String authToken) throws ResponseException {
        var request = buildRequest("DELETE", "/session", null, authToken);
        var response = sendRequest(request);
        handleResponse(response, null);
    }

    public CreateGameResult createGame(String gameName, String authToken) throws ResponseException {
        CreateGameRequest createGameRequest = new CreateGameRequest(gameName);
        var request = buildRequest("POST", "/game", createGameRequest, authToken);
        var response = sendRequest(request);
        return handleResponse(response, CreateGameResult.class);
    }



    public void clear() throws ResponseException {
        var request = buildRequest("DELETE", "/db", null, null);
        sendRequest(request);
    }

    private HttpRequest buildRequest(String method, String path, Object body, String header) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        if (header != null) {
            request.setHeader("Authorization", header);
        }
        return request.build();
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw ResponseException.fromJson(body);
            }

            throw new ResponseException(ResponseException.fromHttpStatusCode(status), "other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}



