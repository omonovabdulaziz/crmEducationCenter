package it.live.crm.repository;

import it.live.crm.entity.TeacherPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherPercentageRepository extends JpaRepository<TeacherPercentage , Long> {
    Optional<TeacherPercentage> findByTeacherId(Long teacher_id);

    @Query(value = "SELECT * FROM teacher_percentage" , nativeQuery = true)
    List<TeacherPercentage> findAllBy();
}
