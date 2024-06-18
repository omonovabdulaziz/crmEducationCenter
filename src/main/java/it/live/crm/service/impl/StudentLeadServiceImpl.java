package it.live.crm.service.impl;

import it.live.crm.exception.MainException;
import it.live.crm.mapper.StudentLeadMapper;
import it.live.crm.mapper.StudentMapper;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentGetLeadDTO;
import it.live.crm.payload.StudentLeadAddDTO;
import it.live.crm.repository.StudentLeadRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.StudentLeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentLeadServiceImpl implements StudentLeadService {
    private final StudentLeadRepository studentLeadRepository;
    private final StudentRepository studentRepository;
    private final StudentLeadMapper studentLeadMapper;
    private final StudentMapper studentMapper;


    @Override
    public ResponseEntity<ApiResponse> addLeadStudent(StudentLeadAddDTO studentLeadAddDTO) {
        studentLeadRepository.save(studentLeadMapper.toEntityFromLead(studentLeadAddDTO, studentRepository.save(studentMapper.toEntity(studentLeadAddDTO))));
        return ResponseEntity.ok(ApiResponse.builder().message("Student added").status(201).build());
    }

    @Override
    public List<StudentGetLeadDTO> getFromLeadByWhere(Long leadId) {
        return studentLeadRepository.findAllByWhereLinkId(leadId);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteFromLead(Long id) {
            studentLeadRepository.deleteByStudentId(id);
        return ResponseEntity.ok(ApiResponse.builder().message("Student deleted").status(200).build());
    }
}
