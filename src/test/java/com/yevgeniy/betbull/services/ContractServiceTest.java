package com.yevgeniy.betbull.services;

import com.google.common.collect.ImmutableList;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContractServiceTest {


    private ContractRepository mockContractRepository() {
        return Mockito.mock(ContractRepository.class);
    }

    private PlayerService mockPlayerService() {
        return Mockito.mock(PlayerService.class);
    }

    private TeamService mockTeamService() {
        return Mockito.mock(TeamService.class);
    }

    @Test
    void getPlayerTeams() throws PlayerNotFoundException {

        Player player = new Player(1L, "John", 19, 33);
        Team team1 = new Team(1L, "Rockets", Currency.GBP);
        Team team2 = new Team(2L, "Stars", Currency.EUR);
        Team team3 = new Team(3L, "Chicago Bulls", Currency.USD);
        Contract contract1 = new Contract(1L, player, team1, BigDecimal.valueOf(1000), LocalDate.now());
        Contract contract2 = new Contract(2L, player, team2, BigDecimal.valueOf(2000), LocalDate.now().plusMonths(1));
        Contract contract3 = new Contract(3L, player, team3, BigDecimal.valueOf(3000), LocalDate.now().plusMonths(2));

        PlayerService playerService = mockPlayerService();
        Mockito.when(playerService.findPlayerIfExists(1L)).thenReturn(player);
        ContractRepository contractRepository = mockContractRepository();
        Mockito.when(contractRepository.findAllByPlayer(player)).thenReturn(ImmutableList.of(contract1, contract2,
                contract3));

        ContractService contractService = new ContractService(contractRepository, playerService, null);
        ResponseEntity<List<TeamHistory>> playerTeams = contractService.getPlayerTeams(1L);

        assertEquals(3, Objects.requireNonNull(playerTeams.getBody()).size());
        assertTrue(playerTeams.getBody().contains(new TeamHistory("Rockets", LocalDate.now())));
        assertEquals(HttpStatus.OK, playerTeams.getStatusCode());

    }

    @Test
    void getPlayerContracts() throws PlayerNotFoundException {
        Player player = new Player(1L, "John", 19, 33);
        Team team1 = new Team(1L, "Rockets", Currency.TRY);
        Contract contract1 = new Contract(1L, player, team1, BigDecimal.valueOf(1000), LocalDate.now());
        PlayerService playerService = mockPlayerService();
        Mockito.when(playerService.findPlayerIfExists(1L)).thenReturn(player);
        ContractRepository contractRepository = mockContractRepository();
        Mockito.when(contractRepository.findAllByPlayer(player)).thenReturn(ImmutableList.of(contract1));
        ContractService contractService = new ContractService(contractRepository, playerService, null);
        ResponseEntity<List<ContractDTO>> playerContracts = contractService.getPlayerContracts(1L);
        assertEquals(HttpStatus.OK, playerContracts.getStatusCode());
        assertTrue(Objects.requireNonNull(playerContracts.getBody()).contains(DtoMapper.toContractDTO(contract1)));
    }

    @Test
    void makeTransfer() throws PlayerNotFoundException, TeamNotFoundException {
        Player player = new Player(1L, "John", 19, 33);
        Team team1 = new Team(1L, "Rockets", Currency.TRY);
        Contract contract1 = new Contract(1L, player, team1, BigDecimal.valueOf(1000), LocalDate.now());
        PlayerService playerService = mockPlayerService();
        Mockito.when(playerService.findPlayerIfExists(1L)).thenReturn(player);
        TeamService teamService = mockTeamService();
        Mockito.when(teamService.findTeamIfExists(1L)).thenReturn(team1);
        ContractRepository contractRepository = mockContractRepository();
        Mockito.when(contractRepository.save(Mockito.any())).thenReturn(contract1);
        ContractService contractService = new ContractService(contractRepository, playerService, teamService);
        ResponseEntity<ContractDTO> response = contractService.makeTransfer(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(DtoMapper.toContractDTO(contract1), response.getBody());

    }

    @Test
    void getContractPrice() throws PlayerNotFoundException {
        Player player = new Player(1L, "John", 19, 33);
        Team team1 = new Team(1L, "Rockets", Currency.TRY);
        Contract contract1 = new Contract(1L, player, team1, BigDecimal.valueOf(1000), LocalDate.now());

        PlayerService playerService = mockPlayerService();
        Mockito.when(playerService.findPlayerIfExists(1L)).thenReturn(player);
        ContractRepository contractRepository = mockContractRepository();
        Mockito.when(contractRepository.findFirstByPlayerOrderBySigningDate(player)).thenReturn(contract1);

        ContractService contractService = new ContractService(contractRepository, playerService, null);
        ResponseEntity<ContractFeeDTO> contractPrice = contractService.getContractPrice(1L);

        assertEquals(HttpStatus.OK, contractPrice.getStatusCode());
        assertEquals(Currency.TRY, Objects.requireNonNull(contractPrice.getBody()).getCurrency());
        assertEquals(BigDecimal.valueOf(191052.4), contractPrice.getBody().getPrice());

    }
}