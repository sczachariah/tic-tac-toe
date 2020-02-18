package com.game.tictactoe.unit;

import com.game.tictactoe.jpa.dao.GameRepo;
import com.game.tictactoe.jpa.dao.PlayerRepo;
import com.game.tictactoe.jpa.dto.Game;
import com.game.tictactoe.jpa.dto.Player;
import com.game.tictactoe.responses.GameResponse;
import com.game.tictactoe.services.GamingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

/**
 * unit tests for gaming service
 */

@RunWith(SpringRunner.class)
public class GamingServiceUnitTests {
    @TestConfiguration
    static class GamingServiceUnitTestContextConfiguration {
        @Bean
        public GamingService gamingService() {
            return new GamingService();
        }
    }

    @Autowired
    private GamingService gamingService;

    @MockBean
    private GameRepo gameRepo;
    @MockBean
    private PlayerRepo playerRepo;

    @Before
    public void setUpRepo() {
        Game game = new Game();
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        game.setCurrentPlayer(player2);
        game.setA1("player1");

        Mockito.when(gameRepo.saveAndFlush(game)).thenReturn(game);
        Mockito.when(gameRepo.getOne(1)).thenReturn(game);
        Mockito.when(playerRepo.findByName("player1")).thenReturn(player1);
        Mockito.when(playerRepo.findByName("player2")).thenReturn(player2);
    }

    @Test
    public void testGameCreation() {
        Mockito.reset(gameRepo);
        Mockito.reset(playerRepo);

        Game game = new Game();
        Player player = new Player("testPlayer");
        Player player2 = new Player("player2");
        Mockito.when(gameRepo.getOne(anyInt())).thenReturn(game);
        Mockito.when(playerRepo.findByName("testPlayer")).thenReturn(player);
        Mockito.when(playerRepo.findByName("player2")).thenReturn(player2);
        Mockito.when(gameRepo.saveAndFlush(any())).thenReturn(game);

        GameResponse response = gamingService.createGame("testPlayer");
        assertThat(response.getEntities().get(0))
                .isNotNull()
                .isEqualTo(game);
    }

    @Test
    public void testGameListing() {
        GameResponse response = gamingService.listAllGames();
        assertThat(response.getEntities().get(0))
                .isNotNull();
    }

    @Test
    public void testGameJoining() {
        Mockito.reset(gameRepo);
        Mockito.reset(playerRepo);

        Game game = new Game();
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);

        Mockito.when(gameRepo.getOne(anyInt())).thenReturn(game);
        Mockito.when(playerRepo.findByName("player1")).thenReturn(player1);
        Mockito.when(playerRepo.findByName("player2")).thenReturn(player2);
        Mockito.when(gameRepo.saveAndFlush(any())).thenReturn(game);

        GameResponse response = gamingService.joinGame(1, "player1");
        assertThat(response.getMessage()).isEqualTo("player player1 has already joined the game.");

        response = gamingService.joinGame(1, "player2");
        assertThat(response.getEntities().get(0))
                .isNotNull()
                .isEqualTo(game);
        assertThat(((Game) response.getEntities().get(0)).getPlayers()).contains(player1, player2);

        response = gamingService.joinGame(1, "player3");
        assertThat(response.getMessage()).isEqualTo("the game has two players already.");
    }

    @Test
    public void testGameState() {
        assertThat(gamingService.getGameState(1).getEntities().get(0)).isNotNull();
        assertThat(gamingService.getGameState(2).getEntities().get(0)).isNull();
    }

    @Test
    public void testIncorrectMoves() {
        String[] incorrectMoves = {"a10", "b5", "c7", "d1"};
        for (String move : incorrectMoves) {
            GameResponse response = gamingService.performGameMove(1, "player2", move);
            assertThat(response.getMessage()).isEqualTo("invalid position");
            setUpRepo();
        }
    }

    @Test
    public void testIncorrectPlayer() {
        GameResponse response = gamingService.performGameMove(1, "player1", "a2");
        assertThat(response.getMessage()).isEqualTo("player player1 is not allowed to make move.");

        response = gamingService.performGameMove(1, "testPlayerX", "a2");
        assertThat(response.getMessage()).isEqualTo("player testPlayerX is not part of the game.");
    }

    @Test
    public void testGameMovesAndEvaluateWinner() {
        assertThat(gamingService.performGameMove(1, "player2", "a2")).isNotNull();
        assertThat(gamingService.performGameMove(1, "player1", "b2")).isNotNull();
        assertThat(gamingService.performGameMove(1, "player2", "a3")).isNotNull();
        assertThat(gamingService.performGameMove(1, "player1", "c3")).isNotNull();

        assertThat(((Player) gamingService.getGameWinner(1).getEntities().get(0)).getName()).isEqualTo("player1");
    }
}
