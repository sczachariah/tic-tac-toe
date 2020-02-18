package com.game.tictactoe.responses;

import com.game.tictactoe.jpa.dto.Game;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

public class GameResponse {
    @ApiModelProperty(notes = "The high level message if any", readOnly = true, hidden = true)
    private String message;

    @ApiModelProperty(notes = "The game entities associated with the response if any", readOnly = true, hidden = true)
    private List<Game> games;

    public GameResponse() {
        message = "";
        games = new LinkedList<>();
    }

    public String getMessage() {
        return message;
    }

    public GameResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Game> getGames() {
        return games;
    }

    public GameResponse setGames(List<Game> games) {
        this.games = games;
        return this;
    }
}
