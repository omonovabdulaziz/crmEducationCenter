package it.live.crm.controller;

import it.live.crm.payload.ApiResponse;
import it.live.crm.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/finance")
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;

    @PostMapping("/addSumToStudentBalance")
    public ResponseEntity<ApiResponse> addSumToStudent(@RequestParam Long studentId, @RequestParam Double countSum) {
       return financeService.addSumToStudent(studentId, countSum);
    }





}
