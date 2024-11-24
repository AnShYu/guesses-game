package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.andshir.model.CurrentRound;

@Repository
public interface CurrentRoundsRepository extends JpaRepository<CurrentRound, Long> {


    @Modifying
    @Query(value = "UPDATE current_rounds SET current_round_number = :new_round_number WHERE game_id = :game_id", nativeQuery = true)
    void updateRoundNumber(@Param("game_id") Long id, @Param("new_round_number") int new_round_number);

    @Modifying
    @Query(value = "UPDATE current_rounds SET results_saved = :saving_status WHERE game_id = :game_id", nativeQuery = true)
    void updateResultsSaved(@Param("game_id") Long id, @Param("saving_status") boolean results_saved);

}
