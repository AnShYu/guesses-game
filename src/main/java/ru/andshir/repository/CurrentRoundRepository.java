package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andshir.model.CurrentRound;

@Repository
public interface CurrentRoundRepository extends JpaRepository<CurrentRound, Long> {

}
