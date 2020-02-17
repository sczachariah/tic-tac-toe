package com.game.tictactoe.controllers;

import com.game.tictactoe.jpa.dto.Game;
import com.game.tictactoe.model.catalog.GameStatus;
import com.game.tictactoe.services.GamingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * controller for gaming rest api
 */

@RestController
@RequestMapping("/api/tictactoe")
@Api(value = "Gaming", description = "API for game operations", tags = {"Gaming API"})
public class GamingRestController {
    @Autowired
    private GamingService gamingService;

    @ApiOperation(value = "create a new tic-tac-toe game")
    @RequestMapping(method = RequestMethod.POST, value = "/game/create")
    public Object createGame(@RequestParam(value = "player") String playerName) {
        Game response = gamingService.createGame(playerName);
        if (response != null) {
            return response;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to create a new game");
        }
    }

    @ApiOperation(value = "list all tic-tac-toe games")
    @RequestMapping(method = RequestMethod.GET, value = "/game/listAll")
    public Object listAllGames() {
        List<Game> response = gamingService.listAllGames();
        if (response != null) {
            return response;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to list all games");
        }
    }

    @ApiOperation(value = "join an active tic-tac-toe game")
    @RequestMapping(method = RequestMethod.POST, value = "/game/{id}/join")
    public Object joinGame(@PathVariable(value = "id") int id, @RequestParam(value = "player") String playerName) {
        Game response = gamingService.joinGame(id, playerName);
        if (response != null) {
            return response;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to join game with id: " + id);
        }
    }

    @ApiOperation(value = "perform a move in an active tic-tac-toe game")
    @RequestMapping(method = RequestMethod.POST, value = "/game/{id}/move")
    public ResponseEntity<String> performGameMove(@PathVariable(value = "id") int id, @RequestParam(value = "player") String playerName, @RequestParam(value = "move") String move) {
        if (gamingService.performGameMove(id, playerName, move)) {
            return ResponseEntity.status(HttpStatus.OK).body("null");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to make move \"" + move + "\" by player \"" + playerName + "\"");
        }
    }

    @ApiOperation(value = "get the current state of a tic-tac-toe game")
    @RequestMapping(method = RequestMethod.GET, value = "/game/{id}/state")
    public Object getGameState(@PathVariable(value = "id") int id) {
        Object response = gamingService.getGameState(id);
        if (response != null) {
            return response;
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to obtain state of game with id: " + id);
    }

    @ApiOperation(value = "get the status of a tic-tac-toe game")
    @RequestMapping(method = RequestMethod.GET, value = "/game/{id}/status")
    public Object getGameStatus(@PathVariable(value = "id") int id) {
        Object response = gamingService.getGameStatus(id);
        if (response != null) {
            return response;
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to get status of game with id: " + id);
    }

    @ApiOperation(value = "get the winner of a tic-tac-toe game")
    @RequestMapping(method = RequestMethod.GET, value = "/game/{id}/getwinner")
    public Object getGameWinner(@PathVariable(value = "id") int id) {
        if (gamingService.getGameStatus(id) != null && gamingService.getGameStatus(id).equals(GameStatus.WINNER)) {
            return gamingService.getGameWinner(id);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("game with id \"" + id + "\" do not have a winner.");
    }

    @ApiOperation(value = "end an active tic-tac-toe game")
    @RequestMapping(method = RequestMethod.POST, value = "/game/{id}/endgame")
    public ResponseEntity<String> endGame(@PathVariable(value = "id") int id) {
        if (gamingService.endGame(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("null");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to end game with id:" + id);
        }
    }
}
