package it.live.crm.service.impl;

import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.entity.StudentArchiveGroup;
import it.live.crm.exception.NotFoundException;
import it.live.crm.mapper.StudentMapper;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentCreateDTO;
import it.live.crm.repository.StudentArchivedGroupRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.StudentService;
import it.live.crm.util.JdbcConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final StudentArchivedGroupRepository studentArchivedGroupRepository;
    private final JdbcConnector jdbcConnector;

    @Override
    public ResponseEntity<ApiResponse> createStudent(StudentCreateDTO studentCreateDTO) {
        studentRepository.save(studentMapper.toEntity(studentCreateDTO));
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("created").build());
    }

    @Override
    public ResponseEntity<ApiResponse> activeStudent(Long studentId, Long groupId) {
        return null;


        // Here is the logic of active Student
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
    public ResponseEntity<ApiResponse> changeGroup(Long studentId, Long groupId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> addStudentGroup(Long studentId, Long newGroupId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> eleminateFromGroup(Long studentId, Long groupId) {
        return null;
    }


}
