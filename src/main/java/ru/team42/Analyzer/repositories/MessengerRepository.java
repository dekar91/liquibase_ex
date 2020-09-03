package ru.team42.analyzer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.team42.analyzer.entities.MessengerEntity;

@Repository
public interface MessengerRepository extends JpaRepository<MessengerEntity, Long> {
}
