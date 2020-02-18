package com.game.tictactoe.model.catalog;

public enum GameStatus {
    WAITING_FOR_PLAYER("WAITING_FOR_PLAYER"),
    ACTIVE("ACTIVE"),
    GAME_OVER("GAME_OVER"),
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
