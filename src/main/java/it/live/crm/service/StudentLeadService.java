package it.live.crm.service;

import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentGetLeadDTO;
import it.live.crm.payload.StudentLeadAddDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentLeadService {
    ResponseEntity<ApiResponse> addLeadStudent(StudentLeadAddDTO studentLeadAddDTO);

    List<StudentGetLeadDTO> getFromLeadByWhere(Long leadId);

    ResponseEntity<ApiResponse> deleteFromLead(Long id);
}
