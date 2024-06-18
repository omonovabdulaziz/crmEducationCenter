package it.live.crm.controller;


import it.live.crm.entity.TeacherPercentage;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.SetTeacherPercentageDTO;
import it.live.crm.service.TeacherPercentageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/percentage-teacher")
@RequiredArgsConstructor
public class TeacherPercentageController {
    private final TeacherPercentageService teacherPercentageService;


    @PostMapping("/set")
    public ResponseEntity<ApiResponse> setTeacherPercentage(@RequestBody SetTeacherPercentageDTO setTeacherPercentageDTO) {
        return teacherPercentageService.setTeacherPercentage(setTeacherPercentageDTO);
    }

    @GetMapping("/getAllTeacherPercentage")
    public List<TeacherPercentage> getAllTeacherPercentage() {
        return teacherPercentageService.getAllTeacherPercentage();
    }
}
