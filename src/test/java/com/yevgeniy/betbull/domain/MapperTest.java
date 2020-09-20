package com.yevgeniy.betbull.domain;

import com.yevgeniy.betbull.dto.PlayerDTO;
import com.yevgeniy.betbull.dto.TeamDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperTest {

    @Test
    void toPlayer() {
        PlayerDTO playerDTO = new PlayerDTO(1L, "Jimmy", 33, 13);
        Player player = Mapper.toPlayer(playerDTO, 2L);
        assertEquals(playerDTO.getAge(), player.getAge());
        assertEquals(2L, player.getId());
        assertEquals(playerDTO.getMonthOfExperience(), player.getMonthOfExperience());
        assertEquals(playerDTO.getName(), player.getName());
    }

    @Test
    void toTeam() {
        TeamDTO rockets = new TeamDTO(1L, "Rockets", Currency.EUR);
        Team team = Mapper.toTeam(rockets, 3L);
        assertEquals(rockets.getCurrency(), team.getCurrency());
        assertEquals(rockets.getTeamName(), team.getTeamName());
        assertEquals(3L, team.getId());
    }
}