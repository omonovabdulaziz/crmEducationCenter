package it.live.crm.config;

import it.live.crm.entity.*;
import it.live.crm.entity.enums.Days;
import it.live.crm.exception.NotFoundException;
import it.live.crm.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DataLoaderConfig implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;
    private final WhereLinkRepository whereLinkRepository;
    private final JdbcTemplate jdbcTemplate;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final AttendanceRepository attendanceRepository;
    private final ChangeOrDenyLessonRepository changeOrDenyLessonRepository;
    @Value("${spring.sql.init.mode}")
    private String sqlInitMode;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (Objects.equals(sqlInitMode, "always")) {
            roleRepository.save(new RoleName("TEACHER"));
            RoleName ceo = roleRepository.save(new RoleName("CEO"));
            RoleName admin = roleRepository.save(new RoleName("ADMIN"));
            RoleName cashier = roleRepository.save(new RoleName("CASHIER"));
            RoleName teacher = roleRepository.findByName("TEACHER").orElseThrow(() -> new NotFoundException("Not found"));
            User teacherbek = userRepository.save(User.builder().name("Abdulaziz").surname("Omonov").isDeleted(false).phoneNumber("+998950960153").password(passwordEncoder.encode("omonov2006")).roleName(Collections.singletonList(teacher)).build());
            userRepository.save(User.builder().name("Abdulaziz").surname("Omonov").isDeleted(false).phoneNumber("+998950960154").password(passwordEncoder.encode("omonov2006")).roleName(Collections.singletonList(ceo)).build());
            userRepository.save(User.builder().name("Abdulaziz").surname("Omonov").isDeleted(false).phoneNumber("+998950960155").password(passwordEncoder.encode("omonov2006")).roleName(Collections.singletonList(admin)).build());
            userRepository.save(User.builder().name("Abdulaziz").surname("Omonov").isDeleted(false).phoneNumber("+998950960156").password(passwordEncoder.encode("omonov2006")).roleName(Collections.singletonList(cashier)).build());
            Course foundation = courseRepository.save(new Course("foundation", 6, 390000d));
            whereLinkRepository.save(new WhereLink("Telegram", "telegram"));
            Group group = groupRepository.save(Group.builder().isGroup(true).days(Arrays.asList(Days.MONDAY, Days.TUESDAY)).course(foundation).name("NOName").startDate(LocalDate.now()).startTime(LocalTime.now()).teacher(teacherbek).build());
            Student studentName = studentRepository.save(Student.builder().isStudent(true).group(List.of(group)).phoneNumber("+99895096343").fullName("Namov Name").balance(0D).build());
            attendanceRepository.save(Attendance.builder().attendanceDate(LocalDate.now()).isCome(true).student(studentName).build());
            changeOrDenyLessonRepository.save(ChangeOrDenyLesson.builder().realDate(LocalDate.now()).passedDate(LocalDate.of(2024, Month.JUNE, 12)).isCancelled(false).group(group).build());
            jdbcTemplate.execute("ALTER TABLE student_group add column joined_date DATE");
            jdbcTemplate.execute("ALTER TABLE student_group add column is_active_here BOOLEAN");
        }
    }
}
