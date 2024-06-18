package it.live.crm.mapper;


import it.live.crm.entity.User;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.TeacherCreateDTO;
import it.live.crm.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TeacherMapper {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User toEntity(TeacherCreateDTO teacherGetDTO, String avatarLink) {
        return User.builder()
                .name(teacherGetDTO.getName())
                .surname(teacherGetDTO.getSurname())
                .phoneNumber(teacherGetDTO.getPhoneNumber())
                .roleName(List.of(roleRepository.findByName("TEACHER").orElseThrow(() -> new NotFoundException("Not found teacher role"))))
                .password(passwordEncoder.encode(teacherGetDTO.getPassword()))
                .isDeleted(false)
                .avatarLink(avatarLink)
                .sex(teacherGetDTO.getSex())
                .birthDate(teacherGetDTO.getBirthDate())
                .build();

    }
}
