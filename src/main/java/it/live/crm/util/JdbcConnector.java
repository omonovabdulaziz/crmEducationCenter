package it.live.crm.util;

import it.live.crm.entity.Student;
import it.live.crm.exception.MainException;
import it.live.crm.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<Long> getStudentsByGroupId(Long groupId, Boolean isActiveHere) {
        String sql = """
            SELECT student_id FROM student_group WHERE group_id = ? AND is_active_here = ?
            """;
        List<Long> studentIds = new ArrayList<>();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, groupId, isActiveHere);
            for (Map<String, Object> row : rows) {
                studentIds.add((Long) row.get("student_id"));
            }
        } catch (Exception e) {
             throw new MainException("Get Student by group exception");
        }
        return studentIds;
    }

}
