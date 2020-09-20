package com.yevgeniy.betbull.dto;

import com.google.common.collect.ImmutableList;
import com.yevgeniy.betbull.domain.Contract;
import com.yevgeniy.betbull.domain.Currency;
import com.yevgeniy.betbull.domain.Player;
import com.yevgeniy.betbull.domain.Team;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoMapperTest {

    @Test
    void toPlayerDTO() {
        Player player = new Player(1L, "Jimmy", 19, 33);
        PlayerDTO playerDTO = DtoMapper.toPlayerDTO(player);
        assertEquals(player.getAge(), playerDTO.getAge());
        assertEquals(player.getId(), playerDTO.getId());
        assertEquals(player.getMonthOfExperience(), playerDTO.getMonthOfExperience());
        assertEquals(player.getName(), playerDTO.getName());
    }

    @Test
    void toPlayerDTOList() {
        Player player = new Player(1L, "Jimmy", 19, 33);
        List<Player> playerList = ImmutableList.of(player);
        List<PlayerDTO> playerDTOList = DtoMapper.toPlayerDTOList(playerList);
        assertEquals(playerList.get(0).getName(), playerDTOList.get(0).getName());
        assertEquals(playerList.get(0).getAge(), playerDTOList.get(0).getAge());
        assertEquals(playerList.get(0).getId(), playerDTOList.get(0).getId());
        assertEquals(playerList.get(0).getMonthOfExperience(), playerDTOList.get(0).getMonthOfExperience());
    }

    @Test
    void toTeamDTO() {
        Team team = new Team(1L, "Lakers", Currency.USD);
        TeamDTO teamDTO = DtoMapper.toTeamDTO(team);
        assertEquals(team.getId(), teamDTO.getId());
        assertEquals(team.getTeamName(), teamDTO.getTeamName());
        assertEquals(team.getCurrency(), teamDTO.getCurrency());
    }

    @Test
    void toTeamDTOList() {
        Team team = new Team(1L, "Lakers", Currency.USD);
        List<Team> teamList = ImmutableList.of(team);
        List<TeamDTO> teamDTOList = DtoMapper.toTeamDTOList(teamList);
        assertEquals(teamList.get(0).getCurrency(), teamDTOList.get(0).getCurrency());
        assertEquals(teamList.get(0).getTeamName(), teamDTOList.get(0).getTeamName());
        assertEquals(teamList.get(0).getId(), teamDTOList.get(0).getId());
    }

    @Test
    void toContractDTO() {
        Team team = new Team(1L, "Lakers", Currency.USD);
        Player player = new Player(1L, "Jimmy", 19, 33);
        Contract contract = new Contract(1L, player, team, BigDecimal.TEN, LocalDate.now());
        ContractDTO contractDTO = DtoMapper.toContractDTO(contract);
        assertEquals(DtoMapper.toPlayerDTO(contract.getPlayer()), contractDTO.getPlayer());
        assertEquals(DtoMapper.toTeamDTO(contract.getTeam()), contractDTO.getTeam());
        assertEquals(contract.getContractPrice(), contractDTO.getContractPrice());
        assertEquals(contract.getSigningDate(), contractDTO.getSigningDate());

    }

    @Test
    void toContractDTOList() {
        Team team = new Team(1L, "Lakers", Currency.USD);
        Player player = new Player(1L, "Jimmy", 19, 33);
        Contract contract = new Contract(1L, player, team, BigDecimal.TEN, LocalDate.now());
        ImmutableList<Contract> contractList = ImmutableList.of(contract);
        List<ContractDTO> contractDTOList = DtoMapper.toContractDTOList(contractList);
        assertEquals(DtoMapper.toPlayerDTO(contractList.get(0).getPlayer()), contractDTOList.get(0).getPlayer());
        assertEquals(DtoMapper.toTeamDTO(contractList.get(0).getTeam()), contractDTOList.get(0).getTeam());
        assertEquals(contractList.get(0).getContractPrice(), contractDTOList.get(0).getContractPrice());
        assertEquals(contractList.get(0).getSigningDate(), contractDTOList.get(0).getSigningDate());
    }

}