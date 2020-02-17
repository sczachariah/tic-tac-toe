package com.game.tictactoe.services;

import com.game.tictactoe.jpa.dao.GameRepo;
import com.game.tictactoe.jpa.dao.PlayerRepo;
import com.game.tictactoe.jpa.dto.Game;
import com.game.tictactoe.jpa.dto.Player;
import com.game.tictactoe.model.catalog.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service implementation for gaming rest api
 */

@Service("gamingService")
public class GamingService {
    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private PlayerRepo playerRepo;

    public Game createGame(String playerName) {
        return null;
    }

    public List<Game> listAllGames() {
        return null;
    }

    public Game joinGame(int id, String playerName) {
        return null;
    }

    public boolean performGameMove(int id, String playerName, String move) {
        return false;
    }

    public Object getGameState(int id) {
        return null;
    }

    public GameStatus getGameStatus(int id) {
        return null;
    }

    public Player getGameWinner(int id) {
        return null;
    }

    public boolean endGame(int id) {
        return false;
    }
}
