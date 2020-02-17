package com.game.tictactoe.services.impl;

import com.game.tictactoe.jpa.dao.GameRepo;
import com.game.tictactoe.jpa.dao.PlayerRepo;
import com.game.tictactoe.services.GamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * service implementation for gaming rest api
 */

@Service("gamingService")
public abstract class GamingServiceImpl implements GamingService {
    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private PlayerRepo playerRepo;
}
