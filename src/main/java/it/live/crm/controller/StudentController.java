package it.live.crm.controller;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentCreateDTO;
import it.live.crm.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createStudent(@RequestBody StudentCreateDTO studentCreateDTO) {
        return studentService.createStudent(studentCreateDTO);
    }

    @PutMapping("/activeStudent")
    public ResponseEntity<ApiResponse> activeStudent(@RequestParam Long studentId, @RequestParam Long groupId) {
        return studentService.activeStudent(studentId, groupId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteStudent(@RequestParam Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @PutMapping("/changeGroup")
    public ResponseEntity<ApiResponse> changeGroup(@RequestParam Long studentId, @RequestParam Long groupId) {
        return studentService.changeGroup(studentId, groupId);
    }

    @PostMapping("/addStudentToGroup")
    public ResponseEntity<ApiResponse> addStudentToGroup(@RequestParam Long studentId, @RequestParam Long newGroupId) {
        return studentService.addStudentGroup(studentId, newGroupId);
    }

    @DeleteMapping("/eleminateFromGroup")
    public ResponseEntity<ApiResponse> eleminateFromGroup(@RequestParam Long studentId, @RequestParam Long groupId) {
        return studentService.eleminateFromGroup(studentId , groupId);
    }


}
