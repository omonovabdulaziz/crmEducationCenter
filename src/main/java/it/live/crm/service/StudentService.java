package it.live.crm.service;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentCreateDTO;
import org.springframework.http.ResponseEntity;

public interface StudentService {
    ResponseEntity<ApiResponse> createStudent(StudentCreateDTO studentCreateDTO);

    ResponseEntity<ApiResponse> activeStudent(Long studentId, Long groupId);

    ResponseEntity<ApiResponse> deleteStudent(Long studentId);

    ResponseEntity<ApiResponse> changeGroup(Long studentId, Long groupId);

    ResponseEntity<ApiResponse> addStudentGroup(Long studentId, Long newGroupId);

    ResponseEntity<ApiResponse> eleminateFromGroup(Long studentId, Long groupId);
}
