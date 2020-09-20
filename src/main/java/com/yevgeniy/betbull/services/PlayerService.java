package com.yevgeniy.betbull.services;

import com.yevgeniy.betbull.domain.Mapper;
import com.yevgeniy.betbull.domain.Player;
import com.yevgeniy.betbull.dto.DtoMapper;
import com.yevgeniy.betbull.dto.PlayerDTO;
import com.yevgeniy.betbull.exceptions.PlayerNotFoundException;
import com.yevgeniy.betbull.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public ResponseEntity<List<PlayerDTO>> getAllPlayersResponse() {
        List<Player> allPlayers = playerRepository.findAll();
        return new ResponseEntity<>(DtoMapper.toPlayerDTOList(allPlayers), HttpStatus.OK);
    }

    public ResponseEntity<PlayerDTO> getSinglePlayerResponse(Long id) throws PlayerNotFoundException {
        Player player = findPlayerIfExists(id);
        return new ResponseEntity<>(DtoMapper.toPlayerDTO(player), HttpStatus.OK);

    }

    public ResponseEntity<PlayerDTO> createNewPlayer(PlayerDTO playerDTO) {
        Player savedPlayer = playerRepository.save(Mapper.toPlayer(playerDTO, null));
        return new ResponseEntity<>(DtoMapper.toPlayerDTO(savedPlayer), HttpStatus.CREATED);
    }

    public ResponseEntity<PlayerDTO> putUpdatePlayer(Long id, PlayerDTO player) throws PlayerNotFoundException {
        Player playerToUpdate = findPlayerIfExists(id);
        setUpdateValues(playerToUpdate, player);
        Player updatedPlayer = playerRepository.save(playerToUpdate);
        return new ResponseEntity<>(DtoMapper.toPlayerDTO(updatedPlayer), HttpStatus.OK);
    }

    private void setUpdateValues(Player playerToUpdate, PlayerDTO player) {
        if (player.getMonthOfExperience() != 0) {
            playerToUpdate.setMonthOfExperience(player.getMonthOfExperience());
        }
        if (player.getName() != null && !player.getName().isEmpty()) {
            playerToUpdate.setName(player.getName());
        }
        if (player.getMonthOfExperience() != 0) {
            playerToUpdate.setAge(player.getAge());
        }
    }

    public ResponseEntity<PlayerDTO> deletePlayer(Long id) throws PlayerNotFoundException {
        Player playerToDelete = findPlayerIfExists(id);
        playerRepository.delete(playerToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected Player findPlayerIfExists(Long id) throws PlayerNotFoundException {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isPresent())
            return player.get();
        else
            throw new PlayerNotFoundException();
    }

}
