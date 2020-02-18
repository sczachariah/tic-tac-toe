package com.game.tictactoe.jpa.dto;

import com.game.tictactoe.model.catalog.GameStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * model of an instance of a game
 */

@Entity
@Table(name = "games")
public class Game {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "A1")
    private String a1;

    @Column(name = "A2")
    private String a2;

    @Column(name = "A3")
    private String a3;

    @Column(name = "B1")
    private String b1;

    @Column(name = "B2")
    private String b2;

    @Column(name = "B3")
    private String b3;

    @Column(name = "C1")
    private String c1;

    @Column(name = "C2")
    private String c2;

    @Column(name = "C3")
    private String c3;

    @Column(name = "IS_GAME_OVER")
    private boolean isGameOver;

    @Column(name = "STATUS")
    private GameStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player currentPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player winner;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAYER_ID", referencedColumnName = "ID")
    private List<Player> players;

    public Game() {
        setStatus(GameStatus.WAITING_FOR_PLAYER);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getB2() {
        return b2;
    }

    public void setB2(String b2) {
        this.b2 = b2;
    }

    public String getB3() {
        return b3;
    }

    public void setB3(String b3) {
        this.b3 = b3;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getPlayerAtPosition(String position) {
        switch (position) {
            case "a1":
                return getA1();
            case "a2":
                return getA2();
            case "a3":
                return getA3();
            case "c1":
                return getC1();
            case "c2":
                return getC2();
            case "c3":
                return getC3();
            case "b1":
                return getB1();
            case "b2":
                return getB2();
            case "b3":
                return getB3();
            default:
                return null;
        }
    }

    public boolean setPlayerToPosition(String position, Player player) {
        boolean result = false;
        switch (position) {
            case "a1": {
                setA1(player.getName());
                result = true;
                break;
            }
            case "a2": {
                setA2(player.getName());
                result = true;
                break;
            }
            case "a3": {
                setA3(player.getName());
                result = true;
                break;
            }
            case "c1": {
                setC1(player.getName());
                result = true;
                break;
            }
            case "c2": {
                setC2(player.getName());
                result = true;
                break;
            }
            case "c3": {
                setC3(player.getName());
                result = true;
                break;
            }
            case "b1": {
                setB1(player.getName());
                result = true;
                break;
            }
            case "b2": {
                setB2(player.getName());
                result = true;
                break;
            }
            case "b3": {
                setB3(player.getName());
                result = true;
                break;
            }
        }
        return result;
    }
}
