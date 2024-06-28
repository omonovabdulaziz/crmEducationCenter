package it.live.crm.service.impl;

import it.live.crm.entity.Course;
import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.entity.StudentArchiveGroup;
import it.live.crm.entity.enums.Status;
import it.live.crm.exception.NotFoundException;
import it.live.crm.mapper.GroupMapper;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.CreateGroupDTO;
import it.live.crm.payload.UpdateGroupDTO;
import it.live.crm.repository.*;
import it.live.crm.service.GroupService;
import it.live.crm.service.helper.HelperFunctions;
import it.live.crm.util.JdbcConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final StudentArchivedGroupRepository studentArchivedGroupRepository;
    private final JdbcConnector jdbcConnector;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HelperFunctions helperFunctions;
    private final GroupMapper groupMapper;

    @Override
    public ResponseEntity<ApiResponse> transfer(Long setId, LocalDate date) {
        Group group = groupRepository.findByIdAndIsGroup(setId, false).orElseThrow(() -> new NotFoundException("Set not found"));
        group.setIsGroup(true);
        group.setStartDate(date);
        group.setEndDate(helperFunctions.identificationByDuring(date, group.getCourse().getMonthCount()));
        group.setIsFinished(false);
        groupRepository.save(group);
        for (Student student : studentRepository.findAllByGroupIdAndGroup_IsGroup(group.getId(), true)) {
            student.setIsStudent(true);
            student.setBalance(0D);
            jdbcConnector.insertToStudentGroup(group.getId(), student.getId(), false);
            studentRepository.save(student);
        }
        return ResponseEntity.ok(ApiResponse.builder().message("Ok").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse> changeStatusGroup(Long groupId, Status status) {
        Group group = groupRepository.findByIdAndIsGroup(groupId, true).orElseThrow(() -> new NotFoundException("this group id have  not group"));
        group.setIsFinished(true);
        for (Student student : studentRepository.findAllByGroupIdAndGroup_IsGroup(groupId, true)) {
            studentArchivedGroupRepository.save(StudentArchiveGroup.builder().from(jdbcConnector.getLocalDateFromStudentGroup(groupId, student.getId())).group(group).til(LocalDate.now()).student(student).build());
            student.setGroup(null);
            studentRepository.save(student);

            if (status == Status.YOPILDI) {
// Return all the transactions which is delayed to students;
            }
        }
        groupRepository.save(group);
        return ResponseEntity.ok(ApiResponse.builder().message("Ok").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse> createGroup(CreateGroupDTO createGroupDTO) {
        Course course = courseRepository.findById(createGroupDTO.getCourseId()).orElseThrow(() -> new NotFoundException("Not found course"));
        groupRepository.save(Group.builder()
                .isGroup(true)
                .name(createGroupDTO.getGroupName())
                .endDate(helperFunctions.identificationByDuring(createGroupDTO.getStartDate(), course.getMonthCount()))
                .days(createGroupDTO.getDaysList())
                .course(course)
                .startTime(createGroupDTO.getStartTime())
                .startDate(createGroupDTO.getStartDate())
                .isFinished(false)
                .teacher(userRepository.findByIdAndRoleName(createGroupDTO.getTeacherId(), List.of(roleRepository.findByName("TEACHER").orElseThrow(() -> new NotFoundException("Not found teacher role")))).orElseThrow(() -> new NotFoundException("Not found teacher")))
                .build());
        return ResponseEntity.ok(ApiResponse.builder().message("Created").status(201).build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateGroup(Long groupId, UpdateGroupDTO updateGroupDTO) {
        Group group = groupRepository.findByIdAndIsGroup(groupId, true).orElseThrow(() -> new NotFoundException("Not found group"));
        groupRepository.save(groupMapper.toUpdate(group, updateGroupDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("Updated").status(200).build());
    }
}
