package com.game.tictactoe.jpa.dao;

import com.game.tictactoe.jpa.dto.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * repository interface for players
 */

public interface PlayerRepo extends JpaRepository<Player, Integer> {
}
