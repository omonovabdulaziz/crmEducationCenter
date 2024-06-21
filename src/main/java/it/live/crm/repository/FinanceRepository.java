package it.live.crm.repository;

import it.live.crm.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FinanceRepository extends JpaRepository<Finance, UUID> {
}
