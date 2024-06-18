package it.live.crm.repository;

import it.live.crm.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleName, Long> {
    Optional<RoleName> findByName(String name);
}
