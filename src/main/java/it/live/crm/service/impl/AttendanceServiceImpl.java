package it.live.crm.service.impl;

import it.live.crm.entity.*;
import it.live.crm.entity.enums.Days;
import it.live.crm.entity.enums.Type;
import it.live.crm.exception.MainException;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.*;
import it.live.crm.repository.*;
import it.live.crm.service.AttendanceService;
import it.live.crm.service.helper.LessonFinanceHelper;
import it.live.crm.service.helper.RedisHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final LessonFinanceHelper lessonFinanceHelper;
    private final RedisHelper redisHelper;
    private final TeacherPercentageRepository teacherPercentageRepository;
    private final TeacherFinanceRepository teacherFinanceRepository;

    @Override
    public ResponseEntity<ApiResponse> create(AttendanceCreateDTO attendance) {
        if (attendanceRepository.existsByStudentIdAndAttendanceDate(attendance.getStudentId(), attendance.getAttendanceDate()))
            throw new MainException("Attendance already exists");
        Student student = studentRepository.findById(attendance.getStudentId()).orElseThrow(() -> new NotFoundException("Not found student"));
        boolean checker = false;
        Group group = groupRepository.findById(attendance.getGroupId()).orElseThrow(() -> new NotFoundException("Not found group"));
        for (Days day : group.getDays()) {
            System.out.println(attendance.getAttendanceDate().getDayOfWeek());
            if (Objects.equals(String.valueOf(attendance.getAttendanceDate().getDayOfWeek()), day.name())) {
                checker = true;
                break;
            }
        }
        if (!checker) {
            throw new MainException("Attendance is not valid for this day");
        }

        Long courseId = group.getCourse().getId();
        Object exactPriceObject = redisHelper.find("course_" + courseId);
        System.out.println(exactPriceObject);
        Double exactPrice = exactPriceObject != null ? Double.parseDouble(exactPriceObject.toString()) : null;
        User teacher = group.getTeacher();
        TeacherPercentage teacherPercentage = teacherPercentageRepository.findByTeacherId(teacher.getId()).orElseThrow(() -> new NotFoundException("First of all Teacher should be provided with mutual income"));
        Optional<TeacherFinance> optional = teacherFinanceRepository.findByTeacherIdAndGroupIdAndStudentId(teacher.getId(), group.getId(), student.getId());
        if (teacherPercentage.getType() == Type.PER_PUPIL_SUM) {
            if (optional.isEmpty()) {
                teacherFinanceRepository.save(TeacherFinance.builder().teacher(teacher).student(student).commonSum(teacherPercentage.getCount()).group(group).build());
            } else {
                TeacherFinance teacherFinance = optional.get();
                teacherFinance.setCommonSum(teacherFinance.getCommonSum() + teacherPercentage.getCount());
                teacherFinanceRepository.save(teacherFinance);
            }
        } else {
            if (exactPrice == null) {
                Double priceForOneDay = lessonFinanceHelper.initializePerDayPrice(group);
                Double percentage = teacherPercentage.getCount() / 100.0;
                Double realPrice = priceForOneDay * percentage;
                if (optional.isEmpty()) {
                    teacherFinanceRepository.save(TeacherFinance.builder().teacher(teacher).student(student).commonSum(realPrice).group(group).build());
                } else {
                    TeacherFinance teacherFinance = optional.get();
                    teacherFinance.setCommonSum(teacherFinance.getCommonSum() + realPrice);
                    teacherFinanceRepository.save(teacherFinance);
                }
                redisHelper.save("course_" + courseId, realPrice);
            } else {
                if (optional.isEmpty()) {
                    teacherFinanceRepository.save(TeacherFinance.builder()
                            .teacher(teacher)
                            .student(student)
                            .commonSum(exactPrice)
                            .group(group)
                            .build());
                } else {
                    TeacherFinance teacherFinance = optional.get();
                    teacherFinance.setCommonSum(teacherFinance.getCommonSum() + exactPrice);
                    teacherFinanceRepository.save(teacherFinance);
                }
            }
        }

        attendanceRepository.save(Attendance.builder().student(student).attendanceDate(attendance.getAttendanceDate()).isCome(attendance.getIsCome()).build());
        return ResponseEntity.ok(ApiResponse.builder().message("ok").status(200).build());
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
