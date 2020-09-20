package com.yevgeniy.betbull.controllers;

import com.yevgeniy.betbull.dto.PlayerDTO;
import com.yevgeniy.betbull.dto.TeamHistory;
import com.yevgeniy.betbull.exceptions.PlayerNotFoundException;
import com.yevgeniy.betbull.services.ContractService;
import com.yevgeniy.betbull.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class PlayerController {

    private final PlayerService playerService;
    private final ContractService contractService;

    public PlayerController(PlayerService playerService, ContractService contractService) {
        this.playerService = playerService;
        this.contractService = contractService;
    }

    @GetMapping(value = "player")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        return playerService.getAllPlayersResponse();
    }

    @GetMapping(value = "player/{id}")
    public ResponseEntity<PlayerDTO> getSinglePlayer(@PathVariable Long id) throws PlayerNotFoundException {
        return playerService.getSinglePlayerResponse(id);
    }

    @PostMapping(value = "player")
    public ResponseEntity<PlayerDTO> createNewPlayer(@RequestBody PlayerDTO player) {
        return playerService.createNewPlayer(player);
    }

    @PutMapping(value = "player/{id}")
    public ResponseEntity<PlayerDTO> putUpdatePlayer(@PathVariable Long id, @RequestBody PlayerDTO player) throws PlayerNotFoundException {
        return playerService.putUpdatePlayer(id, player);
    }

    @DeleteMapping(value = "player/{id}")
    public ResponseEntity<PlayerDTO> deletePlayer(@PathVariable Long id) throws PlayerNotFoundException {
        return playerService.deletePlayer(id);
    }

    @GetMapping(value = "playerTeamsHistory/{playerId}")
    public ResponseEntity<List<TeamHistory>> getPlayerTeamsHistory(@PathVariable Long playerId) throws PlayerNotFoundException {
        return contractService.getPlayerTeams(playerId);
    }
}
