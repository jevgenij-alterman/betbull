package com.yevgeniy.betbull.repository;

import com.yevgeniy.betbull.domain.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {
    List<Team> findAll();
}
