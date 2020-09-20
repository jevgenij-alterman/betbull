package com.yevgeniy.betbull.controllers;


import com.yevgeniy.betbull.dto.ContractDTO;
import com.yevgeniy.betbull.dto.ContractFeeDTO;
import com.yevgeniy.betbull.dto.TransferDTO;
import com.yevgeniy.betbull.exceptions.PlayerNotFoundException;
import com.yevgeniy.betbull.exceptions.TeamNotFoundException;
import com.yevgeniy.betbull.services.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping(value = "contract/{playerId}")
    public ResponseEntity<List<ContractDTO>> getPlayerContracts(@PathVariable Long playerId) throws PlayerNotFoundException {
        return contractService.getPlayerContracts(playerId);
    }

    @GetMapping(value = "contractPrice/{playerId}")
    public ResponseEntity<ContractFeeDTO> getContractPrice(@PathVariable Long playerId) throws PlayerNotFoundException {
        return contractService.getContractPrice(playerId);
    }

    @PostMapping(value = "transfer")
    public ResponseEntity<ContractDTO> transfer(@RequestBody TransferDTO transferDTO) throws PlayerNotFoundException,
            TeamNotFoundException {
        return contractService.makeTransfer(transferDTO.getPlayerId(), transferDTO.getTeamId());
    }
}
