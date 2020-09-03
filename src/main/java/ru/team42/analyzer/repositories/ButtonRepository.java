package ru.team42.analyzer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.team42.analyzer.entities.ButtonEntity;

public interface ButtonRepository  extends JpaRepository<ButtonEntity, Long> {
}
