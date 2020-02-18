package com.game.tictactoe.services;

import com.game.tictactoe.jpa.dao.GameRepo;
import com.game.tictactoe.jpa.dao.PlayerRepo;
import com.game.tictactoe.jpa.dto.Game;
import com.game.tictactoe.jpa.dto.Player;
import com.game.tictactoe.model.catalog.GameStatus;
import com.game.tictactoe.responses.GameResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * service implementation for gaming rest api
 */

@Service("gamingService")
public class GamingService {
    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private PlayerRepo playerRepo;

    /**
     * Method : createGame
     * Service method to create a new game
     *
     * @param playerName name of the player
     * @return - game response with message and game entity of newly created game.
     */
    public GameResponse createGame(String playerName) {
        GameResponse createGameResponse = new GameResponse();
        try {
            // create a game and add the player to game
            Game game = new Game();
            Player player = addPlayerToDB(playerName);
            List<Player> players = new ArrayList<>();
            players.add(player);
            game.setPlayers(players);
            createGameResponse.setMessage("successfully created game.")
                    .getEntities().add(gameRepo.saveAndFlush(game));
        } catch (Exception ex) {
            createGameResponse.setMessage(ExceptionUtils.getMessage(ex));
            ex.printStackTrace();
        }
        return createGameResponse;
    }

    /**
     * Method : listAllGames
     * Service method to list all games
     *
     * @return - game response with message and game entities of all games.
     */
    public GameResponse listAllGames() {
        GameResponse listAllGamesResponse = new GameResponse();
        try {
            listAllGamesResponse.setMessage("successfully retrieved all games.")
                    .getEntities().addAll(gameRepo.findAll());
        } catch (Exception ex) {
            listAllGamesResponse.setMessage(ExceptionUtils.getMessage(ex));
            ex.printStackTrace();
        }
        return listAllGamesResponse;
    }

    /**
     * Method : joinGame
     * Service method to join an available game
     *
     * @param id         id of the game
     * @param playerName name of the player
     * @return - game response with message and game entity of active game.
     */
    public GameResponse joinGame(int id, String playerName) {
        GameResponse joinGameResponse = new GameResponse();
        try {
            Game game = gameRepo.getOne(id);
            // cannot join a game which is over
            if (game.isGameOver()) {
                joinGameResponse.setMessage("the game has ended already.");
            } else {
                Player player = addPlayerToDB(playerName);
                List<Player> players = game.getPlayers();
                // if there is only one player
                if (players.size() == 1) {
                    players.add(player);
                    // set a random player as currentplayer
                    Random random = new Random();
                    Player randomCurrentPlayer = players.get(random.nextInt(2));
                    game.setCurrentPlayer(randomCurrentPlayer);
                    game.setPlayers(players);
                    game.setStatus(GameStatus.ACTIVE);
                    joinGameResponse.setMessage("player " + player.getName() + " successfully joined game.")
                            .getEntities().add(gameRepo.saveAndFlush(game));
                }
                // a new player cannot join if the game already has two players
                else {
                    joinGameResponse.setMessage("the game has two players already.");
                }
            }
        } catch (Exception ex) {
            joinGameResponse.setMessage(ExceptionUtils.getMessage(ex));
            ex.printStackTrace();
        }
        return joinGameResponse;
    }

    /**
     * Method : performGameMove
     * Service method to perform a game move by a player
     *
     * @param id         id of the game
     * @param playerName name of the player
     * @param move       board position the player want to make a move to
     * @return - game response with message and game entity of game.
     */
    public GameResponse performGameMove(int id, String playerName, String move) {
        GameResponse performGameMoveResponse = new GameResponse();
        try {
            Game game = gameRepo.getOne(id);
            Player currentPlayer = game.getCurrentPlayer();
            // if the game is active
            if (!game.isGameOver()) {
                // if the game has two players
                if (game.getPlayers().size() == 2) {
                    // if the player can make a move next
                    if (currentPlayer.getName().equals(playerName)) {
                        // if the position on board is available
                        if (game.getPlayerAtPosition(move) == null) {
                            // mark the player to position
                            if (game.setPlayerToPosition(move, currentPlayer)) {
                                Player winner = evaluateGame(game);
                                // set the other player as next player
                                for (Player item : game.getPlayers()) {
                                    if (!currentPlayer.equals(item)) {
                                        game.setCurrentPlayer(item);
                                        break;
                                    }
                                }
                                // if a winner is determined
                                if (winner != null) {
                                    game.setGameOver(true);
                                    game.setWinner(winner);
                                    game.setStatus(GameStatus.WINNER);
                                }
                                // if the game is a draw
                                if (game.getStatus().equals(GameStatus.DRAW)) {
                                    game.setGameOver(true);
                                }
                                performGameMoveResponse.setMessage("player " + playerName + " successfully made a move to position " + move)
                                        .getEntities().add(gameRepo.saveAndFlush(game));
                            }
                        } else
                            performGameMoveResponse.setMessage("position " + move + " is already marked on the board.");
                    } else
                        performGameMoveResponse.setMessage("player " + playerName + " is not allowed to move as it is not his turn.");
                } else
                    performGameMoveResponse.setMessage("game requires two players. kindly wait for another player to join.");
            } else
                performGameMoveResponse.setMessage("cannot make a move in game that is already over.");

        } catch (Exception ex) {
            performGameMoveResponse.setMessage(ExceptionUtils.getMessage(ex));
            ex.printStackTrace();
        }
        return performGameMoveResponse;
    }

    /**
     * Method : getGameState
     * Service method to get the current state of game board
     *
     * @param id id of the game
     * @return - game response with message and game entity of game.
     */
    public GameResponse getGameState(int id) {
        GameResponse gameStateResponse = new GameResponse();
        try {
            Game game = gameRepo.getOne(id);
            gameStateResponse.setMessage("successfully retrieved game state")
                    .getEntities().add(game);
        } catch (Exception ex) {
            gameStateResponse.setMessage(ExceptionUtils.getMessage(ex));
            ex.printStackTrace();
        }
        return gameStateResponse;
    }

    /**
     * Method : getGameStatus
     * Service method to get the current status of a game
     *
     * @param id id of the game
     * @return - game response with message and game entity of game.
     */
    public GameResponse getGameStatus(int id) {
        GameResponse gameStatusResponse = new GameResponse();
        try {
            Game game = gameRepo.getOne(id);
            gameStatusResponse.setMessage("successfully retrieved game status")
                    .getEntities().add(game.getStatus());
        } catch (Exception ex) {
            gameStatusResponse.setMessage(ExceptionUtils.getMessage(ex));
            ex.printStackTrace();
        }
        return gameStatusResponse;
    }

    /**
     * Method : getGameWinner
     * Service method to get winner of a game, if any
     *
     * @param id id of the game
     * @return - game response with message and winner, if any
     */
    public GameResponse getGameWinner(int id) {
        GameResponse getWinnerResponse = new GameResponse();
        try {
            Game game = gameRepo.getOne(id);
            switch (game.getStatus()) {
                case WAITING_FOR_PLAYER:
                    getWinnerResponse.setMessage("game has not started yet.");
                    break;
                case ACTIVE:
                    getWinnerResponse.setMessage("game is still active. winner not yet determined.");
                    break;
                case GAME_OVER:
                    getWinnerResponse.setMessage("game ended before a winner could be determined.");
                    break;
                case DRAW:
                    getWinnerResponse.setMessage("game is a draw.");
                    break;
                case WINNER:
                    getWinnerResponse.setMessage("successfully evaluated winner.")
                            .getEntities().add(game.getWinner());
                    break;
            }
        } catch (Exception ex) {
            getWinnerResponse.setMessage(ExceptionUtils.getMessage(ex));
            ex.printStackTrace();
        }
        return getWinnerResponse;
    }

    /**
     * Method : endGame
     * Service method to end a game
     *
     * @param id id of the game
     * @return - game response with message and game entity of game.
     */
    public GameResponse endGame(int id) {
        GameResponse endGameResponse = new GameResponse();
        try {
            Game game = gameRepo.getOne(id);
            game.setGameOver(true);
            game.setStatus(GameStatus.GAME_OVER);
            endGameResponse.setMessage("successfully ended game.")
                    .getEntities().add(gameRepo.saveAndFlush(game));
        } catch (Exception ex) {
            endGameResponse.setMessage(ExceptionUtils.getMessage(ex));
            ex.printStackTrace();
        }
        return endGameResponse;
    }

    /**
     * Method : addPlayerToDB
     * Helper method to add a player to database
     *
     * @param playerName name of the player
     * @return - player entity.
     */
    private Player addPlayerToDB(String playerName) {
        Player player = playerRepo.findByName(playerName);
        if (player == null) {
            player = new Player(playerName);
            return playerRepo.saveAndFlush(player);
        }
        return player;
    }

    /**
     * Method : evaluateGame
     * Helper method to evaluate game winner
     *
     * @param game game entity to be evaluated
     * @return - player entity, if there is a winner.
     */
    private Player evaluateGame(Game game) {
        String a1 = game.getA1();
        String a2 = game.getA2();
        String a3 = game.getA3();
        String b1 = game.getB1();
        String b2 = game.getB2();
        String b3 = game.getB3();
        String c1 = game.getC1();
        String c2 = game.getC2();
        String c3 = game.getC3();

        if ((a1 != null && a2 != null && a3 != null) && (a1.equals(a2) & a1.equals(a3))) {
            return playerRepo.findByName(game.getA1());
        }
        if ((b1 != null && b2 != null && b3 != null) && (b1.equals(b2) & b1.equals(b3))) {
            return playerRepo.findByName(game.getB1());
        }
        if ((c1 != null && c2 != null && c3 != null) && (c1.equals(c2) & c1.equals(c3))) {
            return playerRepo.findByName(game.getC1());
        }
        if ((a1 != null && b1 != null && c1 != null) && (a1.equals(b1) & a1.equals(c1))) {
            return playerRepo.findByName(game.getA1());
        }
        if ((a2 != null && b2 != null && c2 != null) && (a2.equals(b2) & a2.equals(c2))) {
            return playerRepo.findByName(game.getA2());
        }
        if ((a3 != null && b3 != null && c3 != null) && (a3.equals(b3) & a3.equals(c3))) {
            return playerRepo.findByName(game.getA3());
        }
        if ((a1 != null && b2 != null && c3 != null) && (a1.equals(b2) & a1.equals(c3))) {
            return playerRepo.findByName(game.getA1());
        }
        if ((c1 != null && b2 != null && a3 != null) && (c1.equals(b2) & c1.equals(a3))) {
            return playerRepo.findByName(game.getC1());
        }
        if (a1 != null && a2 != null && a3 != null &&
                b1 != null && b2 != null && b3 != null &&
                c1 != null && c2 != null && c3 != null) {
            game.setStatus(GameStatus.DRAW);
        }
        return null;
    }
}
