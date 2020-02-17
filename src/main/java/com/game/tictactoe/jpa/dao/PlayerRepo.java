package com.game.tictactoe.jpa.dao;

import com.game.tictactoe.jpa.dto.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * repository interface for players
 */

@Repository
public interface PlayerRepo extends JpaRepository<Player, Integer> {
    Player findByName(String name);
}
