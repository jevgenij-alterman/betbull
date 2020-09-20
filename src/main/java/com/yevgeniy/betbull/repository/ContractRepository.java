package com.yevgeniy.betbull.repository;


import com.yevgeniy.betbull.domain.Contract;
import com.yevgeniy.betbull.domain.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {
    List<Contract> findAllByPlayer(Player player);
    Contract findFirstByPlayerOrderBySigningDate(Player player);
}
