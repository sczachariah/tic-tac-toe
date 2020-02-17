package com.game.tictactoe.jpa.dao;

import com.game.tictactoe.jpa.dto.Game;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository interface for games
 */

public interface GameRepo extends JpaRepository<Game, Integer> {
}
