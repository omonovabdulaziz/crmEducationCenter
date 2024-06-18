package it.live.crm.service.impl;

import it.live.crm.entity.User;
import it.live.crm.entity.enums.Type;
import it.live.crm.exception.MainException;
import it.live.crm.exception.NotFoundException;
import it.live.crm.mapper.TeacherMapper;
import it.live.crm.payload.ApiResponse;
import it.live.crm.payload.SetTeacherPercentageDTO;
import it.live.crm.payload.TeacherCreateDTO;
import it.live.crm.payload.TeacherGetDTO;
import it.live.crm.repository.RoleRepository;
import it.live.crm.repository.UserRepository;
import it.live.crm.service.TeacherPercentageService;
import it.live.crm.service.TeacherService;
import it.live.crm.util.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TeacherMapper teacherMapper;
    private final PasswordEncoder passwordEncoder;
    private final TeacherPercentageService teacherPercentageService;

    @Override
    public List<TeacherGetDTO> getAllTeacher(Boolean isDelete) {
        return userRepository.findAllByIsDeletedAndRoleName(isDelete, Collections.singletonList(roleRepository.findByName("TEACHER").orElseThrow(() -> new NotFoundException("Not found")))).stream().map(user -> new TeacherGetDTO(user.getName(), user.getSurname(), user.getPhoneNumber(), user.getAvatarLink(), isDelete)).toList();
    }

    @Override
    public ResponseEntity<ApiResponse> create(TeacherCreateDTO teacherGetDTO, MultipartFile multipartFile) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(teacherGetDTO.getPhoneNumber());
        if (optionalUser.isPresent())
            throw new MainException("This phone number already exist");
        String s = UUID.randomUUID() + multipartFile.getOriginalFilename();
        ImageUploader.imageUploader(s, multipartFile);
        User user = userRepository.save(teacherMapper.toEntity(teacherGetDTO, "/api/v1/file/getFile?path=" + "DOCUMENTS" + "/" + s));
        teacherPercentageService.setTeacherPercentage(SetTeacherPercentageDTO.builder().isUpdating(false).type(Type.PER_PUPIL_PERCENTAGE).teacherId(user.getId()).percentage(50d).build());
        return ResponseEntity.ok(ApiResponse.builder().status(201).message("Created").build());
    }

    @Override
    public ResponseEntity<ApiResponse> update(Long teacherId, TeacherCreateDTO teacherCreateDTO) {
        User user = userRepository.findByIdAndRoleName(teacherId, List.of(roleRepository.findByName("TEACHER").orElseThrow(() -> new NotFoundException("Not found teacher")))).orElseThrow(() -> new NotFoundException("Not found teacher"));
        user.setName(teacherCreateDTO.getName());
        if (teacherCreateDTO.getPassword() != null)
            user.setPassword(passwordEncoder.encode(teacherCreateDTO.getPassword()));
        user.setSex(teacherCreateDTO.getSex());
        user.setBirthDate(teacherCreateDTO.getBirthDate());
        user.setSurname(teacherCreateDTO.getSurname());
        user.setPhoneNumber(teacherCreateDTO.getPhoneNumber());
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Updated").build());
    }


    @Override
    public ResponseEntity<ApiResponse> delete(Long teacherId) {
        return null;


        // TEACHER DELETE LOGIC DELAYED
    }

    @Override
    public ResponseEntity<ApiResponse> updateProfileImage(Long teacherId, MultipartFile profileImage) {
        User user = userRepository.findByIdAndRoleName(teacherId, List.of(roleRepository.findByName("TEACHER").orElseThrow(() -> new NotFoundException("Not found teacher Role")))).orElseThrow(() -> new NotFoundException("Not found teacher"));
        String s = UUID.randomUUID() + profileImage.getOriginalFilename();
        ImageUploader.imageUploader(s, profileImage);
        user.setAvatarLink("/api/v1/file/getFile?path=" + "DOCUMENTS" + "/" + s);
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.builder().status(200).message("Updated").build());
    }

}
