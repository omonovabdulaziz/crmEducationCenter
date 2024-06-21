package it.live.crm.schudeletedFunctions;


import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.repository.StudentRepository;
import it.live.crm.util.JdbcConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainSchudeledFunctions {
    private final StudentRepository studentRepository;

    // Bu metod bir oyda bir marta ishlaydi  , oyning 1-kunida kechqurun 00:00 da (vazifasi : studentlarni balansidan oylik kurs pulini yeochib olish)
    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleMonthlyTask() {
        for (Student student : studentRepository.findAllByIsStudentAndGroup_IsGroupAndIsDeletedAndGroup_IsFinished(true, true, false, false)) {
            for (Group group : student.getGroup()) {
                if (studentRepository.checkGroupIdAndStudentId(group.getId(), student.getId())) {
                    student.setBalance(student.getBalance() - group.getCourse().getPrice());
                    studentRepository.save(student);
                }
            }
        }
    }
}
