package it.live.crm.service.impl;

import it.live.crm.entity.Attendance;
import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.entity.enums.Days;
import it.live.crm.exception.MainException;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.*;
import it.live.crm.repository.AttendanceRepository;
import it.live.crm.repository.GroupRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.AttendanceService;
import it.live.crm.service.helper.LessonFinanceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final LessonFinanceHelper lessonFinanceHelper;

    @Override
    public ResponseEntity<ApiResponse> create(AttendanceCreateDTO attendance) {
        if (attendanceRepository.existsByStudentIdAndAttendanceDate(attendance.getStudentId(), attendance.getAttendanceDate()))
            throw new MainException("Attendance already exists");
        Student student = studentRepository.findById(attendance.getStudentId()).orElseThrow(() -> new NotFoundException("Not found student"));

        boolean checker = false;

        for (Days day : groupRepository.findById(attendance.getGroupId()).orElseThrow(() -> new NotFoundException("Not found group")).getDays()) {
            if (Objects.equals(String.valueOf(attendance.getAttendanceDate().getDayOfWeek()), day.name())) {
                checker = true;
                break;
            }
        }
        if (!checker) {
            throw new MainException("Attendance is not valid for this day");
        }
        attendanceRepository.save(Attendance.builder().student(student).attendanceDate(attendance.getAttendanceDate()).isCome(attendance.getIsCome()).build());
        return ResponseEntity.ok(ApiResponse.builder().build());
    }


    @Override
    public ResponseEntity<ApiResponse> delete(UUID attId) {
        attendanceRepository.deleteById(attId);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Deleted").build());
    }


    @Override
    public AttendanceGetDTO getAttendanceGroupAndMonth(Long groupId, LocalDate from, LocalDate til, Boolean archiveStudent) {
        List<Attendance> allByStudentGroupIdAndAttendanceDateBetween = attendanceRepository.findAllByStudent_GroupIdAndAttendanceDateBetweenAndStudent_IsDeletedAndStudent_IsStudent(groupId, !archiveStudent, from, til, archiveStudent);
        Group group = groupRepository.findByIdAndIsGroup(groupId, true).orElseThrow(() -> new NotFoundException("group not found or it is not group"));
        List<Days> days = group.getDays(); // Days enum includes DUSHANBA, SESHANBA, CHORSHANBA, PAYSHANBA, JUMA, SHANBA, YAKSHANBA

        Map<Long, LocalDate> daysMap = lessonFinanceHelper.getDatesByWeekName(days, from, til);

        List<Student> students = studentRepository.findAllByGroupIdAndGroup_IsGroup(groupId, true);
        List<StudentDTO> studentDTOs = students.stream().map(student -> {
            List<AttendanceDtoList> attendanceDtoLists = new ArrayList<>();
            for (Attendance attendance : allByStudentGroupIdAndAttendanceDateBetween) {
                if (attendance.getStudent().getId().equals(student.getId())) {
                    Long id = getDynamicId(attendance.getAttendanceDate(), daysMap);
                    attendanceDtoLists.add(new AttendanceDtoList(id, attendance.getIsCome()));
                }
            }

            return StudentDTO.builder()
                    .fullName(student.getFullName())
                    .phoneNumber(student.getPhoneNumber())
                    .isStudent(student.getIsStudent())
                    .isPaidThisMonth(true)
                    .attendance(attendanceDtoLists)
                    .build();
        }).collect(Collectors.toList());

        return AttendanceGetDTO.builder()
                .days(daysMap)
                .studentDTO(studentDTOs)
                .build();
    }

    private Long getDynamicId(LocalDate date, Map<Long, LocalDate> daysMap) {
        for (Map.Entry<Long, LocalDate> entry : daysMap.entrySet()) {
            if (entry.getValue().isEqual(date)) {
                return entry.getKey(); // Return the key when the date is found
            }
        }
        return null; // Handle the case where date is not found in daysMap
    }


}
