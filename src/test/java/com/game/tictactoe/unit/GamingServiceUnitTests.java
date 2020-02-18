package com.game.tictactoe.unit;

import com.game.tictactoe.jpa.dao.GameRepo;
import com.game.tictactoe.jpa.dao.PlayerRepo;
import com.game.tictactoe.jpa.dto.Game;
import com.game.tictactoe.jpa.dto.Player;
import com.game.tictactoe.model.catalog.GameStatus;
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
        game.setStatus(GameStatus.WAITING_FOR_PLAYER);
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
        Player player1 = new Player("player1");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);

        Mockito.when(gameRepo.saveAndFlush(game)).thenReturn(game);
        Mockito.when(gameRepo.getOne(1)).thenReturn(game);
        Mockito.when(playerRepo.findByName("player1")).thenReturn(player1);

        GameResponse response = gamingService.createGame("player1");
        assertThat(response.getEntities().get(0))
                .isNotNull()
                .isEqualTo(game);
    }

    @Test
    public void testGameListing() {
        GameResponse response = gamingService.listAllGames();
        assertThat(response.getEntities()).isNotNull();
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
    public void testJoiningFinishedGame() {
        Mockito.reset(gameRepo);
        Mockito.reset(playerRepo);

        Game game = new Game();
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);
        game.setStatus(GameStatus.GAME_OVER);
        game.setGameOver(true);

        Mockito.when(gameRepo.getOne(anyInt())).thenReturn(game);
        Mockito.when(playerRepo.findByName("player1")).thenReturn(player1);
        Mockito.when(playerRepo.findByName("player2")).thenReturn(player2);
        Mockito.when(gameRepo.saveAndFlush(any())).thenReturn(game);

        GameResponse response = gamingService.joinGame(1, "player2");
        assertThat(response.getMessage()).isEqualTo("the game has ended already.");
    }

    @Test
    public void testGameState() {
        assertThat(gamingService.getGameState(1).getEntities().get(0)).isNotNull();
        assertThat(gamingService.getGameState(2).getEntities().get(0)).isNull();
    }

    @Test
    public void testGameStatus() {
        assertThat(gamingService.getGameStatus(1).getEntities().get(0)).isNotNull();
        assertThat(gamingService.getGameStatus(2).getEntities().get(0)).isNull();
    }

    @Test
    public void testInvalidMoves() {
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
    public void testMoveAlreadyMarkedPosition() {
        Mockito.reset(gameRepo);
        Mockito.reset(playerRepo);

        Game game = new Game();
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game.setPlayers(players);
        game.setCurrentPlayer(player2);
        game.setStatus(GameStatus.ACTIVE);

        Mockito.when(gameRepo.getOne(anyInt())).thenReturn(game);
        Mockito.when(playerRepo.findByName("player1")).thenReturn(player1);
        Mockito.when(playerRepo.findByName("player2")).thenReturn(player2);
        Mockito.when(gameRepo.saveAndFlush(any())).thenReturn(game);

        GameResponse response = gamingService.performGameMove(1, "player2", "a1");
        assertThat(response.getMessage()).isEqualTo("player player2 successfully made a move to position a1");

        response = gamingService.performGameMove(1, "player1", "a1");
        assertThat(response.getMessage()).isEqualTo("position a1 is already marked on the board.");
    }

    @Test
    public void testMoveRequiresTwoPlayers() {
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

        GameResponse response = gamingService.performGameMove(1, "player1", "a2");
        assertThat(response.getMessage()).isEqualTo("game requires two players. kindly wait for another player to join.");
    }

    @Test
    public void testMoveEndedGame() {
        GameResponse response = gamingService.endGame(1);
        assertThat(((Game) response.getEntities().get(0)).getStatus()).isEqualTo(GameStatus.GAME_OVER);
        assertThat(((Game) response.getEntities().get(0)).isGameOver()).isEqualTo(true);

        response = gamingService.performGameMove(1, "player1", "a2");
        assertThat(response.getMessage()).isEqualTo("cannot make a move in game that is already over.");
    }

    @Test
    public void testGameMovesAndEvaluateWinner() {
        assertThat(gamingService.performGameMove(1, "player2", "a2")).isNotNull();
        assertThat(gamingService.performGameMove(1, "player1", "b2")).isNotNull();
        assertThat(gamingService.performGameMove(1, "player2", "a3")).isNotNull();
        assertThat(gamingService.performGameMove(1, "player1", "c3")).isNotNull();

        assertThat(((Player) gamingService.getGameWinner(1).getEntities().get(0)).getName()).isEqualTo("player1");
    }

    @Test
    public void testGameEnding() {
        GameResponse response = gamingService.endGame(1);
        assertThat(((Game) response.getEntities().get(0)).getStatus()).isEqualTo(GameStatus.GAME_OVER);
        assertThat(((Game) response.getEntities().get(0)).isGameOver()).isEqualTo(true);
    }
}
