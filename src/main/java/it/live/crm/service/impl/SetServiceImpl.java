package it.live.crm.service.impl;

import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.exception.NotFoundException;
import it.live.crm.mapper.GroupMapper;
import it.live.crm.mapper.StudentMapper;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.SetCreateDTO;
import it.live.crm.payload.SetGetDTO;
import it.live.crm.payload.StudentSetGetDTO;
import it.live.crm.repository.GroupRepository;
import it.live.crm.repository.StudentRepository;
import it.live.crm.repository.UserRepository;
import it.live.crm.service.SetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class SetServiceImpl implements SetService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public ResponseEntity<ApiResponse> create(SetCreateDTO setDTO) {
        groupRepository.save(groupMapper.toEntity(setDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("created").status(201).build());
    }

    @Override
    public Map<SetGetDTO, Long> getAll() {
        Map<SetGetDTO, Long> map = new HashMap<>();
        for (Group group : groupRepository.findAllByIsGroup(false)) {
            map.put(SetGetDTO.builder().course(group.getCourse()).teacher(group.getTeacher()).days(group.getDays()).name(group.getName()).setId(group.getId()).build(), studentRepository.countByGroupId(group.getId()));
        }
        return map;
    }

    @Override
    public List<StudentSetGetDTO> getStudentsBySet(Long setId) {
        List<StudentSetGetDTO> studentSetGetDTOS = new ArrayList<>();
        for (Student student : studentRepository.findAllByGroupIdAndGroup_IsGroup(setId, false)) {
            studentSetGetDTOS.add(studentMapper.toSetGetDTO(student));
        }
        return studentSetGetDTOS;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("ok").build());
    }

    @Override
    public ResponseEntity<ApiResponse> deleteStudentBySet(Long setId) {
        studentRepository.deleteAllByGroupIdAndGroup_IsGroup(setId, false);
        groupRepository.deleteById(setId);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("ok").build());
    }

    @Override
    public ResponseEntity<ApiResponse> addStudent(Long studentId, Long setId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Group group = groupRepository.findByIdAndIsGroup(setId, false)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        student.setExpectations(null);
        student.setIsStudent(false);
        student.setBalance(0D);

        student.getGroup().add(group);

        studentRepository.save(student);

        // Return success response
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Student added to set successfully")
                .status(200)
                .build());
    }
}
