package it.live.crm.service;

import it.live.crm.entity.TeacherPercentage;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.SetTeacherPercentageDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeacherPercentageService {
    ResponseEntity<ApiResponse> setTeacherPercentage(SetTeacherPercentageDTO setTeacherPercentageDTO);

    List<TeacherPercentage> getAllTeacherPercentage();

}
