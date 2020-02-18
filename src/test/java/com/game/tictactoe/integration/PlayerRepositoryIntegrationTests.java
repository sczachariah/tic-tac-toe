package com.game.tictactoe.integration;

import com.game.tictactoe.jpa.dao.PlayerRepo;
import com.game.tictactoe.jpa.dto.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * integration tests with player repository
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerRepositoryIntegrationTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlayerRepo playerRepo;

    /**
     * Method : when_add_new_player_should_return_player
     * Integration Test method to test adding a player to h2 db and fetching the player by name and compare the objects
     */
    @Test
    public void when_add_new_player_should_return_player() {
        Player testPlayer = new Player("X");
        entityManager.persist(testPlayer);
        entityManager.flush();

        Player foundPlayer = playerRepo.findByName(testPlayer.getName());
        assertThat(testPlayer).isEqualTo(foundPlayer);
    }
}
