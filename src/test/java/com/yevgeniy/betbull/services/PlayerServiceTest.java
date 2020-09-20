package com.yevgeniy.betbull.services;

import com.google.common.collect.ImmutableList;
import com.yevgeniy.betbull.domain.Player;
import com.yevgeniy.betbull.dto.DtoMapper;
import com.yevgeniy.betbull.dto.PlayerDTO;
import com.yevgeniy.betbull.exceptions.PlayerNotFoundException;
import com.yevgeniy.betbull.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlayerServiceTest {

    private PlayerRepository mockPlayerRepository() {
        return Mockito.mock(PlayerRepository.class);
    }

    @Test
    void getAllPlayersResponse() {
        Player player = new Player(1L, "John", 19, 33);
        PlayerRepository playerRepository = mockPlayerRepository();
        Mockito.when(playerRepository.findAll()).thenReturn(ImmutableList.of(player));
        PlayerService playerService = new PlayerService(playerRepository);
        ResponseEntity<List<PlayerDTO>> allPlayersResponse = playerService.getAllPlayersResponse();
        assertFalse(Objects.requireNonNull(allPlayersResponse.getBody()).isEmpty());
        assertEquals(DtoMapper.toPlayerDTO(player), allPlayersResponse.getBody().get(0));
        assertEquals(HttpStatus.OK, allPlayersResponse.getStatusCode());
    }

    @Test
    void getSinglePlayerResponse() throws PlayerNotFoundException {
        Player player = new Player(1L, "John", 19, 33);
        PlayerRepository playerRepository = mockPlayerRepository();
        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        PlayerService playerService = new PlayerService(playerRepository);
        ResponseEntity<PlayerDTO> singlePlayerResponse = playerService.getSinglePlayerResponse(1L);
        assertEquals(HttpStatus.OK, singlePlayerResponse.getStatusCode());
        assertEquals(DtoMapper.toPlayerDTO(player), singlePlayerResponse.getBody());
    }

    @Test
    void createNewPlayer() {
        Player player = new Player(null, "John", 19, 33);
        PlayerRepository playerRepository = mockPlayerRepository();
        Mockito.when(playerRepository.save(player)).thenReturn(player);
        PlayerService playerService = new PlayerService(playerRepository);
        ResponseEntity<PlayerDTO> singlePlayerResponse = playerService.createNewPlayer(DtoMapper.toPlayerDTO(player));
        assertEquals(HttpStatus.CREATED, singlePlayerResponse.getStatusCode());
        assertEquals(DtoMapper.toPlayerDTO(player), singlePlayerResponse.getBody());
    }

    @Test
    void putUpdatePlayer() throws PlayerNotFoundException {
        Player player = new Player(null, "John", 19, 33);
        PlayerRepository playerRepository = mockPlayerRepository();
        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        Mockito.when(playerRepository.save(player)).thenReturn(player);
        PlayerService playerService = new PlayerService(playerRepository);
        ResponseEntity<PlayerDTO> singlePlayerResponse = playerService.putUpdatePlayer(1L,
                DtoMapper.toPlayerDTO(player));
        assertEquals(HttpStatus.OK, singlePlayerResponse.getStatusCode());
        assertEquals(DtoMapper.toPlayerDTO(player), singlePlayerResponse.getBody());

    }

    @Test
    void deletePlayer() throws PlayerNotFoundException {
        Player player = new Player(null, "John", 19, 33);
        PlayerRepository playerRepository = mockPlayerRepository();
        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        PlayerService playerService = new PlayerService(playerRepository);
        ResponseEntity<PlayerDTO> singlePlayerResponse = playerService.deletePlayer(1L);
        assertEquals(HttpStatus.NO_CONTENT, singlePlayerResponse.getStatusCode());
    }

    @Test
    void findPlayerIfExists() throws PlayerNotFoundException {
        Player player = new Player(null, "John", 19, 33);
        PlayerRepository playerRepository = mockPlayerRepository();
        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        PlayerService playerService = new PlayerService(playerRepository);
        Player playerIfExists = playerService.findPlayerIfExists(1L);
        assertEquals(player, playerIfExists);
    }
}