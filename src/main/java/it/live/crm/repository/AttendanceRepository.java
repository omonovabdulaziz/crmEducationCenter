package it.live.crm.repository;

import it.live.crm.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    @Query(nativeQuery = true, value = "SELECT a.* FROM Attendance a JOIN Student s ON a.student_id = s.id JOIN student_group sg on s.id = sg.student_id AND sg.group_id =:groupId AND sg.is_active_here =:isActive AND a.attendance_date BETWEEN :from AND :til AND s.is_deleted =:isDelete AND s.is_student = true")
    List<Attendance> findAllByStudent_GroupIdAndAttendanceDateBetweenAndStudent_IsDeletedAndStudent_IsStudent(@Param("groupId") Long groupId, @Param("isActive") Boolean isActive, @Param("from") LocalDate from, @Param("til") LocalDate til, @Param("isDelete") Boolean isDelete);

    Boolean existsByStudentIdAndAttendanceDate(Long student_id, LocalDate attendanceDate);

}
