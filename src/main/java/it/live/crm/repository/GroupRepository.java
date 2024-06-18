package it.live.crm.repository;

import it.live.crm.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group , Long> {
    List<Group> findAllByIsGroup(Boolean isGroup);
    Optional<Group> findByIdAndIsGroup(Long id, Boolean isGroup);
}
