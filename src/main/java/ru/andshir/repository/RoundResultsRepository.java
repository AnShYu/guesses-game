package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.andshir.model.RoundResult;
import ru.andshir.model.RoundResultId;

import java.util.List;

@Repository
public interface RoundResultsRepository extends JpaRepository<RoundResult, RoundResultId> {

    @Query("SELECT rr FROM RoundResult rr WHERE rr.id.gameId = :gameId")
    List<RoundResult> findAllByGameId(@Param("gameId") long gameId);


//    List<RoundResult> findByIdGameId(long gameId);

}
