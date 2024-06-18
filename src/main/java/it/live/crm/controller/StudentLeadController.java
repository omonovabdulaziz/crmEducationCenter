package it.live.crm.controller;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentGetLeadDTO;
import it.live.crm.payload.StudentLeadAddDTO;
import it.live.crm.service.StudentLeadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-lead")
@RequiredArgsConstructor
public class StudentLeadController {
    private final StudentLeadService studentLeadService;


    @PostMapping("/add")
    @Transactional
    public ResponseEntity<ApiResponse> addStudent(@RequestBody StudentLeadAddDTO studentLeadAddDTO) {
        return studentLeadService.addLeadStudent(studentLeadAddDTO);
    }

    @GetMapping("/getFromLeadByWhere")
    public List<StudentGetLeadDTO> getFromLeadByWhere(@RequestParam("whereLinkId") Long leadId) {
        return studentLeadService.getFromLeadByWhere(leadId);
    }

    @DeleteMapping("/deleteStudentFromLead")
    @Transactional
    public ResponseEntity<ApiResponse> deleteStudentFromLead(@RequestParam("studentId") Long id, @RequestParam("whereLinkId") Long whereLinkId) {
        return studentLeadService.deleteFromLead(id);
    }
}
