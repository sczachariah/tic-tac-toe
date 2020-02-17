package com.game.tictactoe.model.catalog;

public enum GameStatus {
    NONE("NONE"),
    ACTIVE("ACTIVE"),
    DRAW("DRAW"),
    WINNER("WINNER");

    private String status;

    GameStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getStatus();
    }
}
