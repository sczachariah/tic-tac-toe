package com.game.tictactoe.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.tictactoe.responses.GameResponse;
import com.game.tictactoe.services.GamingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public GameResponse getGameStatus(@PathVariable(value = "id") int id) {
        GameResponse response = gamingService.getGameStatus(id);
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value = "get the winner of a tic-tac-toe game")
    @RequestMapping(method = RequestMethod.GET, value = "/game/{id}/getwinner")
    public GameResponse getGameWinner(@PathVariable(value = "id") int id) {
        GameResponse response = gamingService.getGameWinner(id);
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value = "end an active tic-tac-toe game")
    @RequestMapping(method = RequestMethod.POST, value = "/game/{id}/endgame")
    public GameResponse endGame(@PathVariable(value = "id") int id) {
        GameResponse response = gamingService.endGame(id);
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
