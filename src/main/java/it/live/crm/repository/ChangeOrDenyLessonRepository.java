package it.live.crm.repository;

import it.live.crm.entity.ChangeOrDenyLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChangeOrDenyLessonRepository extends JpaRepository<ChangeOrDenyLesson , Long> {
    List<ChangeOrDenyLesson> findAllByGroupIdAndRealDateBetween(Long group_id, LocalDate realDate, LocalDate realDate2);
}
