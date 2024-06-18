package it.live.crm.mapper;

import it.live.crm.entity.Group;
import it.live.crm.entity.RoleName;
import it.live.crm.entity.enums.Days;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.SetCreateDTO;
import it.live.crm.repository.CourseRepository;
import it.live.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class GroupMapper {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Group toEntity(SetCreateDTO setDTO) {
        List<Days> days = new ArrayList<>();
        setDTO.getDays().forEach(day -> days.add(Days.valueOf(day)));
        var checker = false;
        for (RoleName roleName : userRepository.findById(setDTO.getTeacherId()).orElseThrow(() -> new NotFoundException("Error not found")).getRoleName()) {
            if (Objects.equals(roleName.getName(), "TEACHER")) {
                checker = true;
                break;
            }
        }
        if (!checker)
            throw new NotFoundException("Error not found");

        return Group.builder()
                .isGroup(false)
                .name(setDTO.getName())
                .days(days)
                .course(courseRepository.findById(setDTO.getCourseId()).orElseThrow(() -> new NotFoundException("Error not found")))
                .teacher(userRepository.findById(setDTO.getTeacherId()).orElseThrow(() -> new NotFoundException("Error not found")))
                .startTime(setDTO.getStartTime())
                .startDate(null)
                .build();
    }
}
