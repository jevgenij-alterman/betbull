package com.yevgeniy.betbull.controllers;

import com.yevgeniy.betbull.dto.TeamDTO;
import com.yevgeniy.betbull.exceptions.TeamNotFoundException;
import com.yevgeniy.betbull.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping(value = "team")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping(value = "team/{id}")
    public ResponseEntity<TeamDTO> getSingleTeam(@PathVariable Long id) throws TeamNotFoundException {
        return teamService.getSingleTeamResponse(id);
    }

    @PostMapping(value = "team")
    public ResponseEntity<TeamDTO> createNewTeam(@RequestBody TeamDTO team) {
        return teamService.createNewTeam(team);
    }

    @PutMapping(value = "team/{id}")
    public ResponseEntity<TeamDTO> putUpdateTeam(@PathVariable Long id, @RequestBody TeamDTO team) throws TeamNotFoundException {
        return teamService.putUpdateTeam(id, team);
    }

    @DeleteMapping(value = "team/{id}")
    public ResponseEntity<TeamDTO> deleteTeam(@PathVariable Long id) throws TeamNotFoundException {
        return teamService.deleteTeam(id);
    }
}
