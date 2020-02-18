package com.game.tictactoe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.tictactoe.model.catalog.GameStatus;
import com.game.tictactoe.responses.GameResponse;
import com.game.tictactoe.services.GamingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller for gaming rest api
 */

@RestController
@RequestMapping("/api/tictactoe")
@Api(value = "Gaming", description = "API for tic-tac-toe game operations", tags = {"Gaming API"})
public class GamingRestController {
    @Autowired
    private GamingService gamingService;
    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation(value = "create a new tic-tac-toe game")
    @RequestMapping(method = RequestMethod.POST, value = "/game/create")
    public GameResponse createGame(@RequestParam(value = "player") String playerName) {
        GameResponse response = gamingService.createGame(playerName);
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value = "list all tic-tac-toe games")
    @RequestMapping(method = RequestMethod.GET, value = "/game/listAll")
    public GameResponse listAllGames() {
        GameResponse response = gamingService.listAllGames();
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value = "join an active tic-tac-toe game")
    @RequestMapping(method = RequestMethod.POST, value = "/game/{id}/join")
    public GameResponse joinGame(@PathVariable(value = "id") int id, @RequestParam(value = "player") String playerName) {
        GameResponse response = gamingService.joinGame(id, playerName);
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value = "perform a move in an active tic-tac-toe game")
    @RequestMapping(method = RequestMethod.POST, value = "/game/{id}/move")
    public GameResponse performGameMove(@PathVariable(value = "id") int id, @RequestParam(value = "player") String playerName, @RequestParam(value = "move") String move) {
        GameResponse response = gamingService.performGameMove(id, playerName, move);
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value = "get the current state of a tic-tac-toe game")
    @RequestMapping(method = RequestMethod.GET, value = "/game/{id}/state")
    public GameResponse getGameState(@PathVariable(value = "id") int id) {
        GameResponse response = gamingService.getGameState(id);
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value = "get the status of a tic-tac-toe game")
    @RequestMapping(method = RequestMethod.GET, value = "/game/{id}/status")
    public Object getGameStatus(@PathVariable(value = "id") int id) {
        Object response = gamingService.getGameStatus(id);
        if (response != null) {
            try {
                System.out.println(objectMapper.writeValueAsString(response));
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            return ResponseEntity.status(HttpStatus.OK).body("successfully ended game with id: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to end game with id:" + id);
        }
    }
}
