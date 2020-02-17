package com.game.tictactoe.integration;

import com.game.tictactoe.jpa.dao.GameRepo;
import com.game.tictactoe.jpa.dto.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * integration tests with game repository
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameRepositoryIntegrationTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GameRepo gameRepo;

    /**
     * test to add a game to h2 db and fetch the game by id and compare the objects
     */
    @Test
    public void when_add_new_game_should_return_game() {
        Game testGame = new Game();
        entityManager.persist(testGame);
        entityManager.flush();

        Game foundGame = gameRepo.getOne(testGame.getId());

        assertThat(testGame).isEqualTo(foundGame);
    }
}
