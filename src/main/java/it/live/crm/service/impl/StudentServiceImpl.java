package it.live.crm.service.impl;

import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.entity.StudentArchiveGroup;
import it.live.crm.exception.NotFoundException;
import it.live.crm.mapper.StudentMapper;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentCreateDTO;
import it.live.crm.repository.GroupRepository;
import it.live.crm.repository.StudentArchivedGroupRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.StudentService;
import it.live.crm.util.JdbcConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final StudentArchivedGroupRepository studentArchivedGroupRepository;
    private final JdbcConnector jdbcConnector;
    private final GroupRepository groupRepository;

    @Override
    public ResponseEntity<ApiResponse> createStudent(StudentCreateDTO studentCreateDTO) {
        studentRepository.save(studentMapper.toEntity(studentCreateDTO));
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("created").build());
    }

    @Override
    public ResponseEntity<ApiResponse> activeStudent(Long studentId, Long groupId, Boolean activate) {
        jdbcConnector.updateToStudentGroup(groupId, studentId, activate);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message(activate ? "student avtivated for this group" : "student Deactivated for this group").build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteStudent(Long studentId) {
        Student updateStudent = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        if (updateStudent.getGroup() != null) {
            for (Group group : updateStudent.getGroup()) {
                studentArchivedGroupRepository.save(StudentArchiveGroup.builder().student(updateStudent).group(group).til(LocalDate.now()).from(jdbcConnector.getLocalDateFromStudentGroup(group.getId(), studentId)).build());
            }
        }
        updateStudent.setGroup(null);
        updateStudent.setIsStudent(false);
        updateStudent.setIsDeleted(false);
        studentRepository.save(updateStudent);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("deleted").build());
    }

    @Override
    public ResponseEntity<ApiResponse> addStudentGroup(Long studentId, Long newGroupId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        Group group = groupRepository.findByIdAndIsGroup(newGroupId, true).orElseThrow(() -> new NotFoundException("Group not found"));
        student.getGroup().add(group);
        studentRepository.save(student);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("added").build());
    }

    @Override
    public ResponseEntity<ApiResponse> eleminateFromGroup(Long studentId, Long oldGroupId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        for (Group group : student.getGroup()) {
            if (Objects.equals(group.getId(), oldGroupId)) {
                student.getGroup().remove(group);
                studentArchivedGroupRepository.save(StudentArchiveGroup.builder().student(student).group(group).from(jdbcConnector.getLocalDateFromStudentGroup(group.getId(), studentId)).til(LocalDate.now()).build());
                studentRepository.save(student);
            }
        }
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("elemnited").build());
    }


}
