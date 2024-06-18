package it.live.crm.service;


import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.AttendanceCreateDTO;
import it.live.crm.payload.AttendanceGetDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    ResponseEntity<ApiResponse> create(AttendanceCreateDTO attendance);


    AttendanceGetDTO getAttendanceGroupAndMonth(Long groupId, LocalDate from, LocalDate til , Boolean archiveStudent);

    ResponseEntity<ApiResponse> delete(UUID attId);

}
