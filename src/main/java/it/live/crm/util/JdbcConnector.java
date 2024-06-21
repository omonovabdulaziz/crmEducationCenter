package it.live.crm.util;

import it.live.crm.entity.Student;
import it.live.crm.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JdbcConnector {
    private final JdbcTemplate jdbcTemplate;
    private final StudentRepository studentRepository;

    public LocalDate getLocalDateFromStudentGroup(Long groupId, Long studentId) {
        String sql = "SELECT joined_date FROM student_group WHERE group_id = ? AND student_id = ?";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Date.class, groupId, studentId)).toLocalDate();
    }

    public void insertToStudentGroup(Long groupId, Long studentId, boolean isActiveHere) {
        String sql = "INSERT INTO student_group (group_id, student_id, joined_date , is_active_here) VALUES (?, ?, ? , ?)";
        jdbcTemplate.update(sql, groupId, studentId, LocalDate.now(), isActiveHere);
    }

    public void updateToStudentGroup(Long groupId, Long studentId, boolean isActiveHere) {
        String sql = """
                UPDATE student_group
                SET is_active_here = ?
                WHERE group_id = ? AND student_id = ?;""";
        jdbcTemplate.update(sql, isActiveHere, groupId, studentId);
    }

}
