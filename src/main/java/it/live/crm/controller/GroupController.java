package it.live.crm.controller;

import it.live.crm.entity.Group;
import it.live.crm.entity.enums.Status;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.CreateGroupDTO;
import it.live.crm.payload.UpdateGroupDTO;
import it.live.crm.service.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/transferToGroupFromSet/{setId}")
    @Transactional
    public ResponseEntity<ApiResponse> transfer(@PathVariable Long setId, @RequestParam LocalDate date) {
        return groupService.transfer(setId, date);
    }

    @PutMapping("/setStatusGroup")
    @Transactional
    public ResponseEntity<ApiResponse> endGroup(@RequestParam Long groupId, @RequestParam Status status) {
        return groupService.changeStatusGroup(groupId, status);
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createGroup(@RequestBody CreateGroupDTO createGroupDTO) {
        return groupService.createGroup(createGroupDTO);
    }

    @PutMapping("/update/{groupId}")
    public ResponseEntity<ApiResponse> updateGroup(@RequestBody UpdateGroupDTO updateGroupDTO, @PathVariable Long groupId) {
        return groupService.updateGroup(groupId, updateGroupDTO);
    }
}
