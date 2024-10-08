package ru.andshir.repository;

import ru.andshir.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long> {

}
