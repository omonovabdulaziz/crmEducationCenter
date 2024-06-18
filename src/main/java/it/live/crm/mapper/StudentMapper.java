package it.live.crm.mapper;


import it.live.crm.entity.Student;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.StudentCreateDTO;
import it.live.crm.payload.StudentLeadAddDTO;
import it.live.crm.payload.StudentSetGetDTO;
import it.live.crm.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentMapper {
    private final GroupRepository groupRepository;

    public Student toEntity(StudentLeadAddDTO studentLeadAddDTO) {
        return Student.builder()
                .isStudent(false)
                .phoneNumber(studentLeadAddDTO.getPhoneNumber())
                .group(null)
                .fullName(studentLeadAddDTO.getFullName())
                .expectations(null)
                .build();
    }

    public Student toEntity(StudentCreateDTO studentCreateDTO) {
        return Student.builder()
                .phoneNumber(studentCreateDTO.getPhoneNumber())
                .isStudent(studentCreateDTO.getGroupId() != null)
                .group(studentCreateDTO.getGroupId() != null ? List.of(groupRepository.findById(studentCreateDTO.getGroupId()).orElseThrow(() -> new NotFoundException("not found group"))) : null)
                .fullName(studentCreateDTO.getFullName())
                .location(studentCreateDTO.getLocation())
                .telegramLink(studentCreateDTO.getTelegramLink())
                .birthDate(studentCreateDTO.getBirthDate())
                .gender(studentCreateDTO.getGender())
                .parentPhoneNumber(studentCreateDTO.getParentPhoneNumber())
                .passportId(studentCreateDTO.getPassportId())
                .build();


    }

    public StudentSetGetDTO toSetGetDTO(Student student) {
        return StudentSetGetDTO.builder()
                .fullName(student.getFullName())
                .phoneNumber(student.getPhoneNumber())
                .build();

    }
}
