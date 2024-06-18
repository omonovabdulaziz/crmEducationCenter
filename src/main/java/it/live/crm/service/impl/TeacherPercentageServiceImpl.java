package it.live.crm.service.impl;

import it.live.crm.entity.TeacherPercentage;
import it.live.crm.entity.User;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.SetTeacherPercentageDTO;
import it.live.crm.repository.RoleRepository;
import it.live.crm.repository.TeacherPercentageRepository;
import it.live.crm.repository.UserRepository;
import it.live.crm.service.TeacherPercentageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherPercentageServiceImpl implements TeacherPercentageService {
    private final TeacherPercentageRepository teacherPercentageRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<ApiResponse> setTeacherPercentage(SetTeacherPercentageDTO setTeacherPercentageDTO) {
        User teacher = userRepository.findByIdAndRoleName(setTeacherPercentageDTO.getTeacherId(), List.of(roleRepository.findByName("TEACHER").orElseThrow(() -> new NotFoundException("Not found teacher")))).orElseThrow(() -> new NotFoundException("Not found teacher"));
        if (!setTeacherPercentageDTO.getIsUpdating()) {
            teacherPercentageRepository.save(TeacherPercentage.builder()
                    .count(setTeacherPercentageDTO.getPercentage())
                    .teacher(teacher)
                    .type(setTeacherPercentageDTO.getType())
                    .build());
        } else {
            TeacherPercentage updatingTeacherPercentage = teacherPercentageRepository.findByTeacherId(setTeacherPercentageDTO.getTeacherId()).orElseThrow(() -> new NotFoundException("Not found teacher"));
            updatingTeacherPercentage.setCount(setTeacherPercentageDTO.getPercentage());
            updatingTeacherPercentage.setType(setTeacherPercentageDTO.getType());
            teacherPercentageRepository.save(updatingTeacherPercentage);
        }

        return ResponseEntity.ok(ApiResponse.builder().status(200).message("ok").build());
    }

    @Override
    public List<TeacherPercentage> getAllTeacherPercentage() {
        return teacherPercentageRepository.findAllBy();
    }
}
