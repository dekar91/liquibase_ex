package ru.team42.analyzer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team42.analyzer.entities.IntegrationEntity;

public interface IntegrationRepository extends JpaRepository<IntegrationEntity, Long> {
}
