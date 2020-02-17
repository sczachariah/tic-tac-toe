package com.game.tictactoe.jpa.dao;

import com.game.tictactoe.jpa.dto.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository interface for games
 */

@Repository
public interface GameRepo extends JpaRepository<Game, Integer> {
}
