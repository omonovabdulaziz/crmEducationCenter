package it.live.crm.service.impl;

import it.live.crm.entity.Finance;
import it.live.crm.entity.Student;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.ApiResponse;
import it.live.crm.repository.FinanceRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {
    private final FinanceRepository financeRepository;
    private final StudentRepository studentRepository;

    @Override
    public ResponseEntity<ApiResponse> addSumToStudent(Long studentId, Float countSum) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        student.setBalance(student.getBalance() == null ? countSum : student.getBalance() + countSum);
        studentRepository.save(student);
        financeRepository.save(Finance.builder().type("KIRIM").summa(countSum).student(student).build());
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Summma added").build());
    }

}
