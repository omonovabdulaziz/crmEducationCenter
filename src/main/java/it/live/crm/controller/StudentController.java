package it.live.crm.controller;

import it.live.crm.entity.Student;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentCreateDTO;
import it.live.crm.payload.StudentDTO;
import it.live.crm.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse> activeStudent(@RequestParam Long studentId, @RequestParam Long groupId, @RequestParam Boolean activate) {
        return studentService.activeStudent(studentId, groupId, activate);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteStudent(@RequestParam Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @PostMapping("/addStudentToGroup")
    public ResponseEntity<ApiResponse> addStudentToGroup(@RequestParam Long studentId, @RequestParam Long newGroupId) {
        return studentService.addStudentGroup(studentId, newGroupId);
    }

    @DeleteMapping("/eleminateFromGroup")
    public ResponseEntity<ApiResponse> eleminateFromGroup(@RequestParam Long studentId, @RequestParam Long groupId) {
        return studentService.eleminateFromGroup(studentId, groupId);
    }


    @GetMapping("/getAllStudentByGroupId/{groupId}")
    public List<Student> getAllStudentByGroupId(@PathVariable Long groupId, @RequestParam Boolean active) {
        return studentService.getAllStudentByGroupId(groupId , active);
    }
}
