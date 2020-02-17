# tic-tac-toe
Tic Tac Toe Game with Spring Boot

Tic Tac Toe - API

<!-- TOC depthFrom:1 depthTo:6 withLinks:1 updateOnSave:1 orderedList:0 -->

- [How to run](#how-to-run)
- [Endpoints](#endpoints)
	- [1. POST /api/tictactoe/game/create](#1-post-apitictactoegamecreate)
	- [2. GET /api/tictactoe/game/listAll](#2-get-apitictactoegamelistall)
	- [3. POST /api/tictactoe/game/{id}/join](#3-post-apitictactoegameidjoin)
	- [4. POST /api/tictactoe/game/{id}/move](#4-post-apitictactoegameidmove)
	- [5. GET /api/tictactoe/game/{id}/state](#5-get-apitictactoegameidstate)
	- [6. GET /api/tictactoe/game/{id}/status](#6-get-apitictactoegameidstatus)
	- [7. GET /api/tictactoe/game/{id}/getwinner](#7-get-apitictactoegameidgetwinner)
	- [8. POST /api/tictactoe/game/{id}/endgame](#8-post-apitictactoegameidendgame)

<!-- /TOC -->

This Tic Tac Toe game has a grid of 3x3. At the moment, it doesn't validate the player moves, i.e. any player can make more than one move at a time.

This API exposes following endpoints:

 1. POST /api/tictactoe/game/create
 2. GET /api/tictactoe/game/listAll
 3. POST /api/tictactoe/game/{id}/join
 4. POST /api/tictactoe/game/{id}/move
 5. GET /api/tictactoe/game/{id}/state
 6. GET /api/tictactoe/game/{id}/status
 7. GET /api/tictactoe/game/{id}/getwinner
 8. POST /api/tictactoe/game/{id}/endgame
 
# How to run
To compile, run tests and build, use the mvn wrapper command below. The java command will start the application.

```bash
$ ./mvnw clean install && java -jar target/tic-tac-toe-1.0-SNAPSHOT.jar
```

# Endpoints

## 1. POST /api/tictactoe/game/create
## 2. GET /api/tictactoe/game/listAll
## 3. POST /api/tictactoe/game/{id}/join
## 4. POST /api/tictactoe/game/{id}/move
## 5. GET /api/tictactoe/game/{id}/state
## 6. GET /api/tictactoe/game/{id}/status
## 7. GET /api/tictactoe/game/{id}/getwinner
## 8. POST /api/tictactoe/game/{id}/endgame




