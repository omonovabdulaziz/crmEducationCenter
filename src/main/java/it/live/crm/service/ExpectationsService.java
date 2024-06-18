package it.live.crm.service;


import it.live.crm.entity.Expectations;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.ExpectationStudentDTO;
import it.live.crm.payload.ExpectationsGetDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ExpectationsService  {
    Map<ExpectationsGetDTO, Long> getExpectations();


    ResponseEntity<ApiResponse> add(String name);

    ResponseEntity<ApiResponse> delete(Long id);

    ResponseEntity<ApiResponse> addStudent(Long studentLeadId, Long expectationId);

    List<ExpectationStudentDTO> getStudentByExpectation(Long expectationId);

}
