package com.yevgeniy.betbull.services;

import com.google.common.collect.ImmutableList;
import com.yevgeniy.betbull.domain.Currency;
import com.yevgeniy.betbull.domain.Team;
import com.yevgeniy.betbull.dto.DtoMapper;
import com.yevgeniy.betbull.dto.TeamDTO;
import com.yevgeniy.betbull.exceptions.TeamNotFoundException;
import com.yevgeniy.betbull.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TeamServiceTest {

    private TeamRepository mockTeamRepository() {
        return Mockito.mock(TeamRepository.class);
    }

    @Test
    void getAllTeams() {
        Team team = new Team(1L, "Lakers", Currency.GBP);
        TeamRepository teamRepository = mockTeamRepository();
        Mockito.when(teamRepository.findAll()).thenReturn(ImmutableList.of(team));
        TeamService teamService = new TeamService(teamRepository);
        ResponseEntity<List<TeamDTO>> allTeams = teamService.getAllTeams();
        assertFalse(Objects.requireNonNull(allTeams.getBody()).isEmpty());
        assertEquals(DtoMapper.toTeamDTO(team), allTeams.getBody().get(0));
        assertEquals(HttpStatus.OK, allTeams.getStatusCode());
    }

    @Test
    void getSingleTeamResponse() throws TeamNotFoundException {
        Team team = new Team(1L, "Lakers", Currency.GBP);
        TeamRepository teamRepository = mockTeamRepository();
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        TeamService teamService = new TeamService(teamRepository);
        ResponseEntity<TeamDTO> singleTeamResponse = teamService.getSingleTeamResponse(1L);
        assertEquals(HttpStatus.OK, singleTeamResponse.getStatusCode());
        assertEquals(DtoMapper.toTeamDTO(team), singleTeamResponse.getBody());
    }

    @Test
    void createNewTeam() {
        Team team = new Team(null, "Lakers", Currency.GBP);
        TeamRepository teamRepository = mockTeamRepository();
        Mockito.when(teamRepository.save(team)).thenReturn(team);
        TeamService teamService = new TeamService(teamRepository);
        ResponseEntity<TeamDTO> singleTeamResponse = teamService.createNewTeam(DtoMapper.toTeamDTO(team));
        assertEquals(HttpStatus.CREATED, singleTeamResponse.getStatusCode());
        assertEquals(DtoMapper.toTeamDTO(team), singleTeamResponse.getBody());

    }

    @Test
    void putUpdateTeam() throws TeamNotFoundException {
        Team newTeam = new Team(1L, "Stars", Currency.GBP);
        Team oldTeam = new Team(1L, "Lakers", Currency.GBP);
        TeamRepository teamRepository = mockTeamRepository();
        Mockito.when(teamRepository.save(newTeam)).thenReturn(newTeam);
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(oldTeam));
        TeamService teamService = new TeamService(teamRepository);
        ResponseEntity<TeamDTO> teamDTOResponseEntity = teamService.putUpdateTeam(1L, DtoMapper.toTeamDTO(newTeam));
        assertEquals(HttpStatus.OK, teamDTOResponseEntity.getStatusCode());
        assertEquals(DtoMapper.toTeamDTO(newTeam), teamDTOResponseEntity.getBody());
    }

    @Test
    void deleteTeam() throws TeamNotFoundException {
        Team team = new Team(1L, "Lakers", Currency.GBP);
        TeamRepository teamRepository = mockTeamRepository();
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        TeamService teamService = new TeamService(teamRepository);
        ResponseEntity<TeamDTO> singleTeamResponse = teamService.deleteTeam(1L);
        assertEquals(HttpStatus.NO_CONTENT, singleTeamResponse.getStatusCode());
    }

    @Test
    void findTeamIfExists() throws TeamNotFoundException {
        Team team = new Team(1L, "Lakers", Currency.GBP);
        TeamRepository teamRepository = mockTeamRepository();
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        TeamService teamService = new TeamService(teamRepository);
        Team teamIfExists = teamService.findTeamIfExists(1L);
        assertEquals(team, teamIfExists);
    }
}