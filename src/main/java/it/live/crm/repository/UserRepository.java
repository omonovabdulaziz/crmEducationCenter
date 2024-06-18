package it.live.crm.repository;

import it.live.crm.entity.RoleName;
import it.live.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    List<User> findAllByIsDeletedAndRoleName(Boolean isDeleted, List<RoleName> roleName);
    Optional<User> findByIdAndRoleName(Long id, List<RoleName> roleName);
}
