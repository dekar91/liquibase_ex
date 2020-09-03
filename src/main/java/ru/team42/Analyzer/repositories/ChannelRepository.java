package ru.team42.analyzer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.team42.analyzer.entities.ChannelEntity;

@Repository
@Transactional
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

}
