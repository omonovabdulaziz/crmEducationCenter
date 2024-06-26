package it.live.crm.service.impl;

import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.entity.StudentArchiveGroup;
import it.live.crm.entity.enums.Days;
import it.live.crm.exception.NotFoundException;
import it.live.crm.mapper.StudentMapper;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.StudentCreateDTO;
import it.live.crm.repository.GroupRepository;
import it.live.crm.repository.StudentArchivedGroupRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.service.StudentService;
import it.live.crm.service.helper.LessonFinanceHelper;
import it.live.crm.util.JdbcConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final StudentArchivedGroupRepository studentArchivedGroupRepository;
    private final JdbcConnector jdbcConnector;
    private final GroupRepository groupRepository;
    private final LessonFinanceHelper lessonFinanceHelper;


    @Override
    public ResponseEntity<ApiResponse> createStudent(StudentCreateDTO studentCreateDTO) {
        studentRepository.save(studentMapper.toEntity(studentCreateDTO));
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("created").build());
    }

    @Override
    public ResponseEntity<ApiResponse> activeStudent(Long studentId, Long groupId, Boolean activate) {
        if (activate) {
            LocalDate currentDate = LocalDate.now();
            YearMonth currentYearMonth = YearMonth.from(currentDate);
            LocalDate atEndOfMonth = currentYearMonth.atEndOfMonth();
            LocalDate startOfThisMonth = currentDate.withDayOfMonth(1);
            Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Not found group"));
            List<Days> daysList = group.getDays();
            Map<Long, LocalDate> remainDates = lessonFinanceHelper.getDatesByWeekName(daysList, currentDate, atEndOfMonth);
            Map<Long, LocalDate> allDates = lessonFinanceHelper.getDatesByWeekName(daysList, startOfThisMonth, atEndOfMonth);
            int remainDateSize = remainDates.size();
            int allDateSize = allDates.size();
            double eliminateSum = group.getCourse().getPrice() * remainDateSize / allDateSize;
            Student student = studentRepository.findByIdAndGroupId(studentId, groupId).orElseThrow(() -> new NotFoundException("Not found student with this group"));
            student.setBalance(student.getBalance() - eliminateSum);
            studentRepository.save(student);
        }
        jdbcConnector.updateToStudentGroup(groupId, studentId, activate);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message(activate ? "student activated and balance reduced for this group" : "student Deactivated for this group").build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteStudent(Long studentId) {
        Student updateStudent = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        if (updateStudent.getGroup() != null) {
            for (Group group : updateStudent.getGroup()) {
                studentArchivedGroupRepository.save(StudentArchiveGroup.builder().student(updateStudent).group(group).til(LocalDate.now()).from(jdbcConnector.getLocalDateFromStudentGroup(group.getId(), studentId)).build());
            }
        }
        updateStudent.setGroup(null);
        updateStudent.setIsStudent(false);
        updateStudent.setIsDeleted(false);
        studentRepository.save(updateStudent);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("deleted").build());
    }

    @Override
    public ResponseEntity<ApiResponse> addStudentGroup(Long studentId, Long newGroupId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        Group group = groupRepository.findByIdAndIsGroup(newGroupId, true).orElseThrow(() -> new NotFoundException("Group not found"));
        student.getGroup().add(group);
        studentRepository.save(student);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("added").build());
    }

    @Override
    public ResponseEntity<ApiResponse> eleminateFromGroup(Long studentId, Long oldGroupId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        for (Group group : student.getGroup()) {
            if (Objects.equals(group.getId(), oldGroupId)) {
                student.getGroup().remove(group);
                studentArchivedGroupRepository.save(StudentArchiveGroup.builder().student(student).group(group).from(jdbcConnector.getLocalDateFromStudentGroup(group.getId(), studentId)).til(LocalDate.now()).build());
                studentRepository.save(student);
            }
        }
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("elemnited").build());
    }

    @Override
    public List<Student> getAllStudentByGroupId(Long groupId, Boolean active) {
        List<Student> students = new ArrayList<>();
        for (Long l : jdbcConnector.getStudentsByGroupId(groupId, active)) {
            students.add(studentRepository.findByIdAndGroupId(l, groupId).orElseThrow(() -> new NotFoundException("Student not found or group mismatched")));
        }
        return students;
    }


}
