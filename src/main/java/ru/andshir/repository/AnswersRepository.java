package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andshir.model.Answer;
import ru.andshir.model.AnswerId;

import java.util.List;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, AnswerId> {

    int countByGameIdAndRoundNumber(long gameId, int roundNumber);

    List<Answer> findByGameIdAndRoundNumber(long gameId, int roundNumber);
}
