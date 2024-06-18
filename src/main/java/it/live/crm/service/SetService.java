package it.live.crm.service;

import it.live.crm.entity.Student;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.SetCreateDTO;
import it.live.crm.payload.SetGetDTO;
import it.live.crm.payload.StudentSetGetDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SetService {
    ResponseEntity<ApiResponse> create(SetCreateDTO setDTO);

    Map<SetGetDTO, Long> getAll();


    List<StudentSetGetDTO> getStudentsBySet(Long setId);

    ResponseEntity<ApiResponse> deleteStudent(Long studentId);

    ResponseEntity<ApiResponse> deleteStudentBySet(Long setId);

    ResponseEntity<ApiResponse> addStudent(Long studentId, Long setId);
}
