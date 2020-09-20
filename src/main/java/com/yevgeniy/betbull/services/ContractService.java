package com.yevgeniy.betbull.services;

import com.yevgeniy.betbull.domain.Contract;
import com.yevgeniy.betbull.domain.Currency;
import com.yevgeniy.betbull.domain.Player;
import com.yevgeniy.betbull.domain.Team;
import com.yevgeniy.betbull.dto.ContractDTO;
import com.yevgeniy.betbull.dto.ContractFeeDTO;
import com.yevgeniy.betbull.dto.DtoMapper;
import com.yevgeniy.betbull.dto.TeamHistory;
import com.yevgeniy.betbull.exceptions.PlayerNotFoundException;
import com.yevgeniy.betbull.exceptions.TeamNotFoundException;
import com.yevgeniy.betbull.repository.ContractRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    public ResponseEntity<List<TeamHistory>> getPlayerTeams(Long playerId) throws PlayerNotFoundException {
        Player player = playerService.findPlayerIfExists(playerId);
        List<Contract> playerContracts = contractRepository.findAllByPlayer(player);

        List<TeamHistory> teamList = playerContracts.stream().map(contract -> new TeamHistory(contract.getTeam().getTeamName(),
                contract.getSigningDate())).collect(Collectors.toList());
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
        Contract newContract = contractRepository.save(contract);
        return new ResponseEntity<>(DtoMapper.toContractDTO(newContract), HttpStatus.OK);
    }

    private BigDecimal calculatePlayerPrice(Player player) {
        return TEAM_COMMISSION.multiply(BigDecimal.valueOf(player.getMonthOfExperience() * CONTRACT_COEFFICIENT / player.getAge()));
    }

    public ResponseEntity<ContractFeeDTO> getContractPrice(Long playerId) throws PlayerNotFoundException {
        Player playerIfExists = playerService.findPlayerIfExists(playerId);
        BigDecimal price = calculatePlayerPrice(playerIfExists);
        Currency currency = contractRepository.findFirstByPlayerOrderBySigningDate(playerIfExists).getTeam().getCurrency();
        ContractFeeDTO contractFeeDTO = new ContractFeeDTO(price, currency);
        return new ResponseEntity<>(contractFeeDTO, HttpStatus.OK);
    }
}
