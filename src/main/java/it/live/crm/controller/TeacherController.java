package it.live.crm.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.TeacherCreateDTO;
import it.live.crm.payload.TeacherGetDTO;
import it.live.crm.service.TeacherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final ObjectMapper objectMapper;


    @GetMapping("/getAllTeacher")
    public List<TeacherGetDTO> getAllTeacher(@RequestParam Boolean isDelete) {
        return teacherService.getAllTeacher(isDelete);
    }


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<ApiResponse> create(@RequestParam String teacherCreateDTO, @RequestPart MultipartFile multipartFile) throws JsonProcessingException {
        TeacherCreateDTO dto = objectMapper.readValue(teacherCreateDTO, TeacherCreateDTO.class);
        return teacherService.create(dto, multipartFile);

    }

    @PutMapping("/update/{teacherId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long teacherId, @RequestBody TeacherCreateDTO teacherGetDTO) {
        return teacherService.update(teacherId, teacherGetDTO);
    }

    @PutMapping(value = "/updateProfileImage/{teacherId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<ApiResponse> updateProfileImage(@PathVariable Long teacherId, @RequestBody MultipartFile profileImage) {
        return teacherService.updateProfileImage(teacherId, profileImage);
    }

    @DeleteMapping("/delete/{teacherId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long teacherId) {
        return teacherService.delete(teacherId);
    }
}
