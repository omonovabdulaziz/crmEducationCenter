package it.live.crm.repository;

import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.entity.TeacherFinance;
import it.live.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeacherFinanceRepository extends JpaRepository<TeacherFinance , UUID> {
    Optional<TeacherFinance> findByTeacherIdAndGroupIdAndStudentId(Long teacher_id, Long group_id, Long student_id);
}
