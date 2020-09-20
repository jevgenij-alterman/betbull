package com.yevgeniy.betbull.services;

import com.yevgeniy.betbull.domain.Mapper;
import com.yevgeniy.betbull.domain.Team;
import com.yevgeniy.betbull.dto.DtoMapper;
import com.yevgeniy.betbull.dto.TeamDTO;
import com.yevgeniy.betbull.exceptions.TeamNotFoundException;
import com.yevgeniy.betbull.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return new ResponseEntity<>(DtoMapper.toTeamDTOList(teamRepository.findAll()), HttpStatus.OK);
    }

    public ResponseEntity<TeamDTO> getSingleTeamResponse(Long id) throws TeamNotFoundException {
        Team team = findTeamIfExists(id);
        return new ResponseEntity<>(DtoMapper.toTeamDTO(team), HttpStatus.OK);
    }

    public ResponseEntity<TeamDTO> createNewTeam(TeamDTO team) {
        Team createdTeam = teamRepository.save(Mapper.toTeam(team, null));
        return new ResponseEntity<>(DtoMapper.toTeamDTO(createdTeam), HttpStatus.CREATED);
    }

    public ResponseEntity<TeamDTO> putUpdateTeam(Long id, TeamDTO team) throws TeamNotFoundException {
        Team teamToUpdate = findTeamIfExists(id);
        teamToUpdate.setTeamName(team.getTeamName());
        Team updatedTeam = teamRepository.save(teamToUpdate);
        return new ResponseEntity<>(DtoMapper.toTeamDTO(updatedTeam), HttpStatus.OK);
    }

    public ResponseEntity<TeamDTO> deleteTeam(Long id) throws TeamNotFoundException {
        Team teamToDelete = findTeamIfExists(id);
        teamRepository.delete(teamToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Team findTeamIfExists(Long id) throws TeamNotFoundException {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent())
            return team.get();
        else
            throw new TeamNotFoundException();
    }

}

