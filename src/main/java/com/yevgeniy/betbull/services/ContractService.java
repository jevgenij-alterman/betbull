package com.yevgeniy.betbull.services;

import com.yevgeniy.betbull.domain.Contract;
import com.yevgeniy.betbull.domain.Player;
import com.yevgeniy.betbull.domain.Team;
import com.yevgeniy.betbull.dto.ContractDTO;
import com.yevgeniy.betbull.dto.DtoMapper;
import com.yevgeniy.betbull.exceptions.PlayerNotFoundException;
import com.yevgeniy.betbull.exceptions.TeamNotFoundException;
import com.yevgeniy.betbull.repository.ContractRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final PlayerService playerService;
    private final TeamService teamService;

    private static final int CONTRACT_COEFFICIENT = 100000;
    private static final BigDecimal TEAM_COMMISSION = BigDecimal.valueOf(1.1);


    public ContractService(ContractRepository contractRepository, PlayerService playerService, TeamService teamService) {
        this.contractRepository = contractRepository;
        this.playerService = playerService;
        this.teamService = teamService;
    }

    public ResponseEntity<Map<String, LocalDate>> getPlayerTeams(Long playerId) throws PlayerNotFoundException {
        Player player = playerService.findPlayerIfExists(playerId);
        List<Contract> playerContracts = contractRepository.findAllByPlayer(player);
        Map<String, LocalDate> teamList = playerContracts.stream().collect(Collectors.toMap(contract -> contract.getTeam().getTeamName(), Contract::getSigningDate));
        return new ResponseEntity<>(teamList, HttpStatus.OK);
    }

    public ResponseEntity<List<ContractDTO>> getPlayerContracts(Long playerId) throws PlayerNotFoundException {
        Player player = playerService.findPlayerIfExists(playerId);
        List<Contract> playerContracts = contractRepository.findAllByPlayer(player);
        return new ResponseEntity<>(DtoMapper.toContractDTOList(playerContracts), HttpStatus.OK);
    }

    public ResponseEntity<ContractDTO> makeTransfer(Long playerId, Long teamId) throws PlayerNotFoundException, TeamNotFoundException {
        Contract contract = new Contract();
        Player player = playerService.findPlayerIfExists(playerId);
        Team newTeam = teamService.findTeamIfExists(teamId);
        contract.setPlayer(player);
        contract.setContractPrice(calculatePlayerPrice(player));
        contract.setSigningDate(LocalDate.now());
        contract.setTeam(newTeam);
        contractRepository.save(contract);
        return new ResponseEntity<>(DtoMapper.toContractDTO(contract), HttpStatus.OK);
    }

    private BigDecimal calculatePlayerPrice(Player player) {
        return TEAM_COMMISSION.multiply(BigDecimal.valueOf(player.getMonthOfExperience() * CONTRACT_COEFFICIENT / player.getAge()));
    }
}
