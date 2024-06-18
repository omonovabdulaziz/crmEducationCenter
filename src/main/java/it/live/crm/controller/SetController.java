package it.live.crm.controller;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.SetCreateDTO;
import it.live.crm.payload.SetGetDTO;
import it.live.crm.payload.StudentSetGetDTO;
import it.live.crm.service.SetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/set")
@RequiredArgsConstructor
public class SetController {
    private final SetService setService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody SetCreateDTO setDTO) {
        return setService.create(setDTO);
    }

    @GetMapping("/get-all")
    public Map<SetGetDTO, Long> getAll() {
        return setService.getAll();
    }

    @PostMapping("/addStudentToSet")
    @Transactional
    public ResponseEntity<ApiResponse> addStudent(@RequestParam Long studentId, @RequestParam Long setId) {
        return setService.addStudent(studentId, setId);
    }

    @GetMapping("/getStudentsBySet")
    public List<StudentSetGetDTO> getStudentsBySet(@RequestParam Long setId) {
        return setService.getStudentsBySet(setId);
    }


    @DeleteMapping("/deleteStudentFromSet")
    @Transactional
    public ResponseEntity<ApiResponse> deleteStudent(@RequestParam Long studentId) {
        return setService.deleteStudent(studentId);
    }


    @DeleteMapping("/deleteSet")
    @Transactional
    public ResponseEntity<ApiResponse> deleteSet(@RequestParam Long setId) {
        return setService.deleteStudentBySet(setId);
    }
}
