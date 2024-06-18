package it.live.crm.service;

import it.live.crm.entity.enums.Status;
import it.live.crm.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;

public interface GroupService {
    ResponseEntity<ApiResponse> transfer(Long setId , LocalDate time);

    ResponseEntity<ApiResponse> changeStatusGroup(Long groupId , Status status);

}
