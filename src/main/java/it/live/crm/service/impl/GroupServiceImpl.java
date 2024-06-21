package it.live.crm.service.impl;

import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.entity.StudentArchiveGroup;
import it.live.crm.entity.enums.Status;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.ApiResponse;
import it.live.crm.repository.GroupRepository;
import it.live.crm.repository.StudentArchivedGroupRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.GroupService;
import it.live.crm.util.JdbcConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final StudentArchivedGroupRepository studentArchivedGroupRepository;
    private final JdbcTemplate jdbcTemplate;
    private final JdbcConnector jdbcConnector;

    @Override
    public ResponseEntity<ApiResponse> transfer(Long setId, LocalDate date) {
        Group group = groupRepository.findByIdAndIsGroup(setId, false).orElseThrow(() -> new NotFoundException("Set not found"));
        group.setIsGroup(true);
        group.setStartDate(date);
        group.setIsFinished(false);
        groupRepository.save(group);
        for (Student student : studentRepository.findAllByGroupIdAndGroup_IsGroup(group.getId(), true)) {
            student.setIsStudent(true);
            student.setBalance(0D);
            jdbcConnector.insertToStudentGroup(group.getId(), student.getId(), false);
            studentRepository.save(student);
        }
        return ResponseEntity.ok(ApiResponse.builder().message("Ok").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse> changeStatusGroup(Long groupId, Status status) {
        Group group = groupRepository.findByIdAndIsGroup(groupId, true).orElseThrow(() -> new NotFoundException("this group id have  not group"));
        group.setIsFinished(true);
        for (Student student : studentRepository.findAllByGroupIdAndGroup_IsGroup(groupId, true)) {
            studentArchivedGroupRepository.save(StudentArchiveGroup.builder().from(jdbcConnector.getLocalDateFromStudentGroup(groupId, student.getId())).group(group).til(LocalDate.now()).student(student).build());
            student.setGroup(null);
            studentRepository.save(student);

            if (status == Status.YOPILDI) {
// Return all the transactions which is delayed to students;
            }
        }
        groupRepository.save(group);
        return ResponseEntity.ok(ApiResponse.builder().message("Ok").status(200).build());
    }
}
