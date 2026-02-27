# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram]
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5TAYm8+fMAEoo9kiqFnJIEGgwvj6m9lAQAK7YngAsAMwAHABMbiDRyPYAFmA6CImGgcGhUOGRmIiopAC0AHzklDRQAFwwANoACgDyZAAqALowAPSJBlAAOmgA3gBEs5RowAC2KCvdKzArADSHuOoA7tAcewfHhyhbwEgIN4cAvpjCXTBtrOxclF6q3WUE2O1edxWZ1Ulyg132hxOKweTxeCJWHzYnG4sF+X1EvSgQRCYEoAAoqiTKIEAI4VUIASk+nVEP3asnkShU6l69hQYAAqnMySCwSgmRzFMo1Ko2UYdN0AGJITgwIWUSUwHSWGCi7ZiHRE4AAazVcxg5yQYCKurmYpgwAQRo4OpQAA8SRpJVyZT88SyVL11VBJcyRCo-e0voCYAonShgC7Rib0ABRN0qbAEerR3G-RrmXqpJzpRarHbqYB8vaHVNQBI9W0bfUO+OJnXyY3oDFmTiYb3S9SRjrhlC9NCJBAIMNUVm-Afc1TdEBG0nBkVzSUS7Q+oe-YzdBQcDhmjXaGdz9k7wdLlcJ0kKRLWsnAJ9FLf96+LuUHo8nx-WqG+IRs0vxYgCjaUqE1JqJOWDgTiw65kCax2vqBy9LcSKvtaowQF2aAYYc7wzpQw4FhgvTZE4ThlqhzbgjAmGIocOFFHhBFEbcHzoBwXgxH4AAyEDBFEAmeHECTJJ40DsHy0SKnAqbSHACgwMJomYDA8DIOYcrIf0wxjJMUwGOoERoHRerguiSLQrC8LcVpI7fGB-w4ih1m7LZpz6DCVwQh82kIWR-qjr0CAiSqZIaSqtL0mATLacBhjzl+Mq8vy65eUlMjpXu7QHjAyqqsGmrak2oL6s5hoJqawYwGgEDMAAZkkfbaQuvqgVGAZjo1k7TslfVIZ0MYCpsb7QEgABeKAcOmmbZmgzm5uRumUTAxYAIx0RWqhVt5tb1tAvSJJN1rTXN1xvNpzm8c5XV7mFs6BqeIbnsNo76X1vQALJyCANr2VcMAtQkWyVYs-zvaG2m1SaMADH5DlGBAaiNc1MCPGAQOrSNPUueNF1FFd82LSgWYWfj1ChfmG1gEWTi7csKz7YdNYrHWDZnSTZM3fdHUuZeeWcjevR3nIKAAUUL5vh+nX5bK+4KrGx6xvLn3CyBbnYjGsVoIEqhwc5IV5r1tONsCaGMcxkJsRx3ZMcRPZfa59NNIzMDUbRrNeVxLErMAvZ8Zg3jqSJSSwN4klJCk8QoOgBRIMUYAJ0nGlJFgFHMC9MZ9NIqaCamoypiZZmqBZ0Nvo7K1rbrEHOw7+HoJgZs-eFMCRfYWcxZHT7xWoiUXiBV5i4umVgDLcuAdo27j91hWqy1KonmxoYI6aMtXXUURaPIaMY01zDuiSn4L89Fuvf1E5TiPqV542E1sfzFNUzmY3mzpntMyz5YyhzTC3NTq6j5lAWa80ewPSesrF6BIHSa3kPfOUMDegcBQNwB8b4Z7vjnufKU34VZoIwfyQw69zwpWHGbXomcB6wQQPBdydMr4xiWB8euHtCzexomWHifYw6eHUiSGAABxfUsoY6YHiHHTwRITyeGwCqU0glhFiIrA0BmHcui9D6CIkuJl7D6mrrhFuddP5ymoQgkxBE25MNxFfeByBQhqLUDg2uJwVGhEHoyZBaUL5LhgHyKe2Dm4EXngQxe8peglX-Ig4O8MjSmlCUnFUVBHRID4orfxo1O63yGtrB+LCn5gIgQtDMlNlo03du0HOv89oAOrEAk6jZzov3AddV2MAHpZIiZfApvQZZAQJmPXpASnFgBcaoMk4TdzKyXr0FeqpDHqM3qIls+9gCH1lMfLpHpQj4NmTk6+45Bq+Mfr0AAcs1RU7UylLWphwmpDMmapHqZWRpx0eaY1arcqBfYYFHPgS4oZ31CaWM8WAI2Jt26EwMqsZZagay6P1JKcY7DzGE1qdw32MB4XiKRcC7QaKQ78UEXAVchgXHREkdI6SHAADsbgnAoCcNEVM6Q4BKQAGzwApWsnYMANGey0fnIyExpgIuMexUxZYEUXP1Oiy2Fi7EoWSYRfYugQCQjlehfYf0dQuJ7DChxb1JakjJOS+8KBvHD0oX40Zk9p5qpmTeH8qsYka1nkghJdUrHSoIjAVJ6TMmi1GYCt6eSqlznOWqEp1036VLdsw7+XCdpvIOh8rmzTeZtNKZ07pobDmwt+p63BSC7UjNmRLClLiyQ6oeC6wh8y-z8pQKGAFxbO6EvLQTBuHleVWqhQw2xesv5wpWDq22LskRcR7I8lNm0fZ0UnbsZ2WFDgBx7CSgRMAABSEAVStupbHaSlgMGRXOMnVOZ6pwQEvfuw9VLPBlFAMaIVekY2DAFOKqYkqSa1zLNgBAwAz1QDgBASKUATgIoAJLSEVa5doljVhqpuJqyEAxgOgfA5Bm4AAhYDIBjTav1HBm421sjpFSMcTEditHwIAFYHrQGSB9hsUB0iHkyCthbxaBP5E6mupjG2RKKh6wZWtVlqsDWgNJyAQ0drgRG05bto1FKDHG8m5T34rSTV-LFabWbs0zcAlpmmBbaQLYpk1-UJM9tBZWvjiyOC1tg9IETBUonFVXoElsFU3M1USb5gVOy2qJA4Lp3jhClM3xUwUo5MYrlgBueFtACbqZ6fWj-LaThXlGYaUdLNXyqWhd+UFLp-ylbhv6t24OqyEWBtlJ2Vu1n+mtpBdfBLjY4zOksMmAi6WP5Ksxc8nLpZ8vvMK6Z3kLYQhg0Gn8viPHUG6mwPvFAtbasebmV5gUa2pbtfPK1lKvRatnKQyqvdzGh2MNHV1lCbnN0zrXXOjFnDF08NZo9tdgdN18NDrS+OtQdSeEKCUeIIGdIJlgMAbAQHCC70FTnEVjYC5FxLmXaYxhSL2L+KO3oI7G4xYltwPA0zfGOYnjAEApOoDk47fMowJDSSVS9FVohTPMGGARWz7JHP0Fc79bXXnYbO3HMFXavtMZEDQ5u4TxCYvWEIeTVipdUQ2EkqAA

```
actor Client
participant Server
participant Handler
participant Service
participant DataAccess
database db

entryspacing 0.9

######### Registration #######

group#43829c #lightblue Registration
Client -> Server: [POST] /user\n{"username":" ", "password":" ", "email":" "}
Server -> Handler: {"username":" ", "password":" ", "email":" "}
Handler -> Service: register(RegisterRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db:Find UserData by username
break User with username already exists
DataAccess --> Service: UserData
Service --> Server: AlreadyTakenException
Server --> Client: 403\n{"message": "Error: username already taken"}
end
DataAccess --> Service: null
Service -> DataAccess:createUser(userData)
DataAccess -> db:Add UserData
Service -> DataAccess:createAuth(authData)
DataAccess -> db:Add AuthData
Service --> Handler: RegisterResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

######### Login #######

group#orange #FCEDCA Login
Client -> Server: [POST] /session\n{"username":" ", "password":" "}
Server -> Handler: {"username":" ", "password":" "}
Handler -> Service: login(LoginRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db: Find UserData by username
break User not found
DataAccess --> Service: null
Service --> Server: UnauthorizedException
Server --> Client: 401\n{"message": "Error: unauthorized"}  
end
DataAccess --> Service: UserData
Service -> Service: Match password from user\nand UserData
break Password does not match
Service --> Server: UnauthorizedException
Server --> Client: 401\n{"message": "Error: unauthorized"}
end
Service -> DataAccess: createAuth(authData)
DataAccess -> db: Add AuthData
Service --> Handler: LoginResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "a
end

## Logour ##

group#green #lightgreen Logout
Client -> Server: [DELETE] /session\nauthToken
Server -> Handler : authToken
Handler -> Service: logout(LogoutRequest)
Service -> DataAccess: getAuth(authData)
DataAccess -> db: find authData
break Authorization data does not exist
DataAccess --> Service: null
Service --> Server: UnauthorizedException
Server --> Client: 401\n{"message": "Error: unauthorized"}
end
DataAccess --> Service: authData
Service -> DataAccess: deleteAuth(authData)
DataAccess -> db: delete authData
Service --> Handler: LogoutResult
Handler --> Server: {}
Server --> Client: 200\n{}
end

## List Games ##

group#red #pink List Games
Client -> Server: [GET] /game\nauthToken
Server -> Handler: authToken
Handler -> Service: listGames(authToken, ListRequest)
Service -> DataAccess: getAuth(authToken)
DataAccess -> db: Find AuthData
break authToken invalid
DataAccess --> Service: null
Service --> Server: UnauthorizedException
Server --> Client: 401\n{"message": "Error: unauthorized"}
end
DataAccess --> Service: AuthData
Service -> DataAccess: listGames()
DataAccess -> db: find games
break Game data does not exist
DataAccess --> Service: null
Service --> Server: NotFoundException
Server --> Client: 404\n{"message": "Error: not found"}
end
DataAccess --> Service: GameData
Service --> Handler: ListResult
Handler --> Server: {"games": [GameData]}
Server --> Client: 200\n {"games": [GameData]}
end

### Create Game ###

group#d790e0 #E3CCE6 Create Game
Client -> Server: [POST] /game\nauthToken\n{gameName}
Server -> Handler: {"authToken":"abc", "gameName":"My Game"}
Handler -> Service: create(CreateRequest)
Service -> DataAccess: getAuth(authToken)
DataAccess -> db: Find AuthData
break authToken invalid
DataAccess --> Service: null
Service --> Server: UnauthorizedException
Server --> Client: 401\n{"message": "Error: unauthorized"}
end
DataAccess --> Service: AuthData
Service -> DataAccess: createGame(gameNaem)
DataAccess -> db:Add GameData
DataAccess --> Service: GameData
Service --> Handler: CreateResult
Handler --> Server: {"gameName" : " ", "" : ""}
Server --> Client: 200\n{"gameName" : " ", "" : " "}

end

## Join Game ##

group#yellow #lightyellow Join Game #black
Client -> Server: [PUT] /game\nauthToken\n{playerColor, gameID}
Server -> Handler: {"authToken":"abc", "PlayerColor":"Black", "gameID":"1234",}
Handler -> Service: join(JoinRequest)
Service -> DataAccess: getAuth(authToken)
DataAccess -> db: Find AuthData
break authToken invalid
DataAccess --> Service: null
Service --> Server: UnauthorizedException
Server --> Client: 401\n{"message": "Error: unauthorized"}
end
DataAccess --> Service: AuthData
Service -> DataAccess: findGame(gameID)
DataAccess -> db: Find game by gameID
break game not foudn
DataAccess --> Service: null
Service --> Server: NotFoudnException
Server --> Client: 404\n{"message": "Error: Game not found"}
end
DataAccess --> Service: GameData
break game is taken
DataAccess --> Service: GameData
Service --> Server: AlreadyTakenException
Server --> Client: 403\n{"message": "Error: game is full"}
end
Service -> DataAccess: updateGame(GameData)
DataAccess -> db: Update GameData
DataAccess --> Service: GameData
Service --> Handler: JoinResult
Handler --> Server: {"gameID" : " ", "" : ""}
Server --> Client: 200\n{"gameID" : " ", "" : " "}
end

group#gray #lightgray Clear application
Client -> Server: [DELETE] /db
Server -> Handler:
Handler -> Service: clear()
Service -> DataAccess: clear()
DataAccess -> db: delete users
DataAccess -> db: delete games
DataAccess -> db: delete authTokens
DataAccess --> Service:
Service --> Handler: ClearResult
Handler --> Server: {}
Server --> Client: 200\n {}
end
```

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
