package it.live.crm.service;

import it.live.crm.entity.enums.Status;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.CreateGroupDTO;
import it.live.crm.payload.UpdateGroupDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;

public interface GroupService {
    ResponseEntity<ApiResponse> transfer(Long setId , LocalDate time);

    ResponseEntity<ApiResponse> changeStatusGroup(Long groupId , Status status);

    ResponseEntity<ApiResponse> createGroup(CreateGroupDTO createGroupDTO);

    ResponseEntity<ApiResponse> updateGroup(Long groupId, UpdateGroupDTO updateGroupDTO);
}
