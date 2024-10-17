package ru.andshir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andshir.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
