package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andshir.model.RoundResult;
import ru.andshir.model.RoundResultId;

import java.util.List;

@Repository
public interface RoundResultsRepository extends JpaRepository<RoundResult, RoundResultId> {

    List<RoundResult> findByGameIdAndRoundNumber(long gameId, int roundNumber);

}
