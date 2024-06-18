package it.live.crm.service;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.TeacherCreateDTO;
import it.live.crm.payload.TeacherGetDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherService {
    List<TeacherGetDTO> getAllTeacher( Boolean isDelete);

    ResponseEntity<ApiResponse> create(TeacherCreateDTO teacherGetDTO , MultipartFile multipartFile);

    ResponseEntity<ApiResponse> update(Long teacherId, TeacherCreateDTO teacherGetDTO);

    ResponseEntity<ApiResponse> delete(Long teacherId);

    ResponseEntity<ApiResponse> updateProfileImage(Long teacherId, MultipartFile profileImage);
}
