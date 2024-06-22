package it.live.crm.schudeletedFunctions;


import it.live.crm.entity.Finance;
import it.live.crm.entity.Group;
import it.live.crm.entity.Student;
import it.live.crm.repository.FinanceRepository;
import it.live.crm.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
@RequiredArgsConstructor
public class MainSchudeledFunctions {
    private final StudentRepository studentRepository;
    private final FinanceRepository financeRepository;

    // Bu metod bir oyda bir marta ishlaydi  , oyning 1-kunida kechqurun 00:00 da (vazifasi : studentlarni balansidan oylik kurs pulini yeochib olish)
    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleMonthlyTask() {
        for (Student student : studentRepository.findAllByIsStudentAndGroup_IsGroupAndIsDeletedAndGroup_IsFinished(true, true, false, false)) {
            for (Group group : student.getGroup()) {
                Double price = group.getCourse().getPrice();
                if (studentRepository.checkGroupIdAndStudentId(group.getId(), student.getId())) {
                    student.setBalance(student.getBalance() - price);
                    financeRepository.save(Finance.builder().student(student).summa(price).type("CHIQIM").group(group).month(YearMonth.now()).build());
                    studentRepository.save(student);
                }
            }
        }
    }
}
