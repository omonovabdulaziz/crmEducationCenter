package it.live.crm.service;

import it.live.crm.entity.Student;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentCreateDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    ResponseEntity<ApiResponse> createStudent(StudentCreateDTO studentCreateDTO);

    ResponseEntity<ApiResponse> activeStudent(Long studentId, Long groupId , Boolean activate);

    ResponseEntity<ApiResponse> deleteStudent(Long studentId);


    ResponseEntity<ApiResponse> addStudentGroup(Long studentId, Long newGroupId);

    ResponseEntity<ApiResponse> eleminateFromGroup(Long studentId, Long groupId);

    List<Student> getAllStudentByGroupId(Long groupId, Boolean active);
}
