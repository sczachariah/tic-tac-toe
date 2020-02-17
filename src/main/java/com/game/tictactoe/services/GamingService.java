package com.game.tictactoe.services;

import com.game.tictactoe.jpa.dto.Game;
import com.game.tictactoe.jpa.dto.Player;
import com.game.tictactoe.model.catalog.GameStatus;

import java.util.List;

/**
 * service interface for gaming rest api
 */

public interface GamingService {
    Game createGame(String playerName);

    List<Game> listAllGames();

    Game joinGame(int id, String playerName);

    boolean performGameMove(int id, String playerName, String move);

    Object getGameState(int id);

    GameStatus getGameStatus(int id);

    Player getGameWinner(int id);

    boolean endGame(int id);
}
