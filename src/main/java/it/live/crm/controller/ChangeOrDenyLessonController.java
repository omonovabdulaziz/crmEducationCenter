package it.live.crm.controller;

import it.live.crm.entity.ChangeOrDenyLesson;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.AttendanceGetDTO;
import it.live.crm.payload.ChangeOrDenyLessonDTO;
import it.live.crm.payload.GetChangeOrDenyDTO;
import it.live.crm.repository.ChangeOrDenyLessonRepository;
import it.live.crm.repository.GroupRepository;
import it.live.crm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/change-or-deny-lesson")
@RequiredArgsConstructor
public class ChangeOrDenyLessonController {
    public final ChangeOrDenyLessonRepository changeOrDenyLessonRepository;
    private final GroupRepository groupRepository;
    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<ApiResponse> changeOrDenyLesson(@RequestParam String type, @RequestParam LocalDate from, @RequestParam LocalDate til, @RequestBody ChangeOrDenyLessonDTO changeOrDenyLesson) {
        AttendanceGetDTO attendanceGroupAndMonth = attendanceService.getAttendanceGroupAndMonth(changeOrDenyLesson.getGroupId(), from, til , false);
        Map<Long, LocalDate> days = attendanceGroupAndMonth.getDays();
        boolean checker = days.values().stream().anyMatch(date -> date.isEqual(changeOrDenyLesson.getRealDate()));
        if (!checker)
            throw new NotFoundException("Not found day for this group");
        ChangeOrDenyLesson changeOrDenyLessonEntity = new ChangeOrDenyLesson();
        changeOrDenyLessonEntity.setGroup(groupRepository.findById(changeOrDenyLesson.getGroupId()).orElseThrow(() -> new NotFoundException("Not found")));
        changeOrDenyLessonEntity.setRealDate(changeOrDenyLesson.getRealDate());
        if (type.equalsIgnoreCase("deny")) {
            changeOrDenyLessonEntity.setIsCancelled(true);
        } else {
            changeOrDenyLessonEntity.setIsCancelled(false);
            changeOrDenyLessonEntity.setPassedDate(changeOrDenyLesson.getPassedDate());
        }
        changeOrDenyLessonRepository.save(changeOrDenyLessonEntity);
        return ResponseEntity.ok(ApiResponse.builder().message("Ok").status(201).build());
    }


    @GetMapping("/getChangeOrDenyList")
    public List<GetChangeOrDenyDTO> getChangeOrDenyDTO(@RequestParam LocalDate from, @RequestParam LocalDate til, @RequestParam Long groupId) {
        List<GetChangeOrDenyDTO> getChangeOrDenyDTOS = new ArrayList<>();
        for (ChangeOrDenyLesson changeOrDenyLesson : changeOrDenyLessonRepository.findAllByGroupIdAndRealDateBetween(groupId, from, til)) {
            getChangeOrDenyDTOS.add(GetChangeOrDenyDTO.builder().isCancelled(changeOrDenyLesson.getIsCancelled()).passedDate(changeOrDenyLesson.getPassedDate()).realDate(changeOrDenyLesson.getRealDate()).build());
        }
        return getChangeOrDenyDTOS;
    }
}
