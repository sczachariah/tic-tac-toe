package com.game.tictactoe.responses;

import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

public class GameResponse {
    @ApiModelProperty(notes = "The high level message if any", readOnly = true, hidden = true)
    private String message;

    @ApiModelProperty(notes = "The game entities associated with the response if any", readOnly = true, hidden = true)
    private List<Object> entity;

    public GameResponse() {
        message = "";
        entity = new LinkedList<>();
    }

    public String getMessage() {
        return message;
    }

    public GameResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<Object> getEntities() {
        return entity;
    }

    public GameResponse setEntities(List<Object> entity) {
        this.entity = entity;
        return this;
    }
}
