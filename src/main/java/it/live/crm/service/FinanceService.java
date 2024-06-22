package it.live.crm.service;

import it.live.crm.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface FinanceService {

    ResponseEntity<ApiResponse> addSumToStudent(Long studentId, Double countSum);
}
