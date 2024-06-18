package it.live.crm.controller;


import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.AttendanceCreateDTO;
import it.live.crm.payload.AttendanceGetDTO;
import it.live.crm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/createAttendance")
    public ResponseEntity<ApiResponse> createAttendance(@RequestBody AttendanceCreateDTO attendance) {
        return attendanceService.create(attendance);
    }

    @GetMapping("/getAttendanceByGroupAndMonth")
    public AttendanceGetDTO getAttendanceByGroupAndMonth(@RequestParam Long groupId, @RequestParam LocalDate from, @RequestParam LocalDate til, @RequestParam Boolean archiveStudent) {
        return attendanceService.getAttendanceGroupAndMonth(groupId, from, til , archiveStudent);
    }

    @DeleteMapping("/delete/{attId}")
    public ResponseEntity<ApiResponse> deleteAttendance(@PathVariable UUID attId) {
        return attendanceService.delete(attId);
    }
}
