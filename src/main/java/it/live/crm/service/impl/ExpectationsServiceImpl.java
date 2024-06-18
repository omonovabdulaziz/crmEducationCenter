package it.live.crm.service.impl;

import it.live.crm.entity.Expectations;
import it.live.crm.entity.Student;
import it.live.crm.entity.StudentLead;
import it.live.crm.exception.MainException;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.ExpectationStudentDTO;
import it.live.crm.payload.ExpectationsGetDTO;
import it.live.crm.repository.ExpectationsRepository;
import it.live.crm.repository.StudentLeadRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.ExpectationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpectationsServiceImpl implements ExpectationsService {
    private final ExpectationsRepository expectationsRepository;
    private final StudentRepository studentRepository;
    private final StudentLeadRepository studentLeadRepository;

    @Override
    public Map<ExpectationsGetDTO, Long> getExpectations() {
        Map<ExpectationsGetDTO, Long> map = new HashMap<>();
        for (Expectations expectations : expectationsRepository.findAll()) {
            map.put(new ExpectationsGetDTO(expectations.getName(), expectations.getId()), studentRepository.countByExpectationsId(expectations.getId()));
        }
        return map;
    }

    @Override
    public ResponseEntity<ApiResponse> add(String name) {
        if (name == null || name.isEmpty()) {
            throw new MainException("Error: name is null or empty");
        }
        expectationsRepository.save(new Expectations(name));
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Created").build());
    }

    @Override
    public ResponseEntity<ApiResponse> delete(Long id) {
        studentRepository.deleteAllByExpectationsId(id);
        expectationsRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Deleted").build());
    }

    @Override
    public ResponseEntity<ApiResponse> addStudent(Long studentLeadId, Long expectationId) {
        StudentLead studentLead = studentLeadRepository.findById(studentLeadId).orElseThrow(() -> new NotFoundException("error"));
        Student student = studentLead.getStudent();
        student.setExpectations(expectationsRepository.findById(expectationId).orElseThrow(() -> new NotFoundException("Error not Found")));
        studentRepository.save(student);
        studentLeadRepository.deleteByStudentId(student.getId());
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Added").build());
    }

    @Override
    public List<ExpectationStudentDTO> getStudentByExpectation(Long expectationId) {
        return studentLeadRepository.getStudentByExpectation(expectationId);
    }
}
