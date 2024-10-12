package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andshir.model.Round;
import ru.andshir.model.RoundId;

@Repository
public interface RoundsRepository extends JpaRepository<Round, RoundId> {

}
