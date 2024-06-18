package it.live.crm.controller;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.ExpectationStudentDTO;
import it.live.crm.payload.ExpectationsGetDTO;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.ExpectationsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/expectations")
@RequiredArgsConstructor
public class ExpectationsController {
    private final ExpectationsService expectationsService;
    private final StudentRepository studentRepository;

    @GetMapping("/get-all")
    public Map<ExpectationsGetDTO, Long> getExpectations() {
        return expectationsService.getExpectations();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addExpectations(@RequestParam String name) {
        return expectationsService.add(name);
    }


    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<ApiResponse> deleteExpectations(@RequestParam Long id) {
        return expectationsService.delete(id);
    }

    @PostMapping("/addStudentToExpectation")
    @Transactional
    public ResponseEntity<ApiResponse> addStudentToExpectation(@RequestParam Long studentLeadId, @RequestParam Long expectationId) {
        return expectationsService.addStudent(studentLeadId, expectationId);
    }

    @DeleteMapping("/deleteStudentFromExpectation")
    public ResponseEntity<ApiResponse> deleteStudentFromExpectation(@RequestParam Long studentId) {
        studentRepository.deleteById(studentId);
        return ResponseEntity.ok(ApiResponse.builder().message("Deleted").status(200).build());
    }

    @GetMapping("/getStudentByExpectations/{expectationId}")
    public List<ExpectationStudentDTO> getStudentByExpectation(@PathVariable Long expectationId) {
        return expectationsService.getStudentByExpectation(expectationId);
    }
}
