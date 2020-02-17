package com.game.tictactoe.services;

import com.game.tictactoe.jpa.dao.GameRepo;
import com.game.tictactoe.jpa.dao.PlayerRepo;
import com.game.tictactoe.jpa.dto.Game;
import com.game.tictactoe.jpa.dto.Player;
import com.game.tictactoe.model.catalog.GameStatus;
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
     * @param playerName
     * @return - game entity of the newly created game.
     */
    public Game createGame(String playerName) {
        try {
            Game game = new Game();
            Player player = addPlayerToDB(playerName);
            List<Player> players = new ArrayList<>();
            players.add(player);
            game.setPlayers(players);
            return gameRepo.saveAndFlush(game);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Game> listAllGames() {
        try {
            return gameRepo.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Game joinGame(int id, String playerName) {
        try {
            Game game = gameRepo.getOne(id);
            Player player = addPlayerToDB(playerName);
            List<Player> players = game.getPlayers();
            if (!players.isEmpty() & players.size() < 2) {
                players.add(player);
                Random random = new Random();
                Player randomCurrentPlayer = players.get(random.nextInt(2));
                game.setCurrentPlayer(randomCurrentPlayer);
                game.setPlayers(players);
                game.setStatus(GameStatus.ACTIVE);
                gameRepo.saveAndFlush(game);
                return game;
            } else return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean performGameMove(int id, String playerName, String move) {
        Game game = gameRepo.getOne(id);
        Player currentPlayer = game.getCurrentPlayer();
        try {
            if (currentPlayer.getName().equals(playerName) & game.getPlayers().size() == 2 & !game.isGameOver()) {
                if (game.getPlayerAtPosition(move) == null) {
                    if (game.setPlayerToPosition(move, currentPlayer)) {
                        Player winner = evaluateGame(game);
                        for (Player item : game.getPlayers()) {
                            if (!currentPlayer.equals(item)) {
                                game.setCurrentPlayer(item);
                                break;
                            }
                        }
                        if (winner != null) {
                            game.setGameOver(true);
                            game.setWinner(winner);
                            game.setStatus(GameStatus.WINNER);
                        }
                        if (game.getStatus().equals(GameStatus.DRAW)) {
                            game.setGameOver(true);
                        }
                        gameRepo.saveAndFlush(game);
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Game getGameState(int id) {
        try {
            Game game = gameRepo.getOne(id);
            return game;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public GameStatus getGameStatus(int id) {
        try {
            Game game = gameRepo.getOne(id);
            return game.getStatus();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Player getGameWinner(int id) {
        try {
            Game game = gameRepo.getOne(id);
            return game.getWinner();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean endGame(int id) {
        Game game = gameRepo.getOne(id);
        if (game != null) {
            try {
                game.setGameOver(true);
                game.setStatus(GameStatus.GAME_OVER);
                gameRepo.saveAndFlush(game);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else return false;
    }

    private Player addPlayerToDB(String playerName) {
        Player player = playerRepo.findByName(playerName);
        if (player == null) {
            player = new Player(playerName);
            return playerRepo.saveAndFlush(player);
        }
        return player;
    }

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
