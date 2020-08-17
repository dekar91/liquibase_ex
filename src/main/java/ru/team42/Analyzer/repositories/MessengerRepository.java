package ru.team42.analyzer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.team42.analyzer.entities.MessengerEntity;

@Repository
public interface MessengerRepository extends CrudRepository<MessengerEntity, Long> {
}
