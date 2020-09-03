package ru.team42.analyzer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.team42.analyzer.entities.HitEntity;

@Repository
public interface HitRepository extends JpaRepository<HitEntity, Long> {
}
