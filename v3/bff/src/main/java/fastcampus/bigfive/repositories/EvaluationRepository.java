package fastcampus.bigfive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fastcampus.bigfive.entities.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
