package it.live.crm.mapper;

import it.live.crm.entity.Student;
import it.live.crm.entity.StudentLead;
import it.live.crm.exception.NotFoundException;
import it.live.crm.payload.StudentLeadAddDTO;
import it.live.crm.repository.WhereLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentLeadMapper {
    private final WhereLinkRepository whereLinkRepository;
    public StudentLead toEntityFromLead(StudentLeadAddDTO studentLeadAddDTO, Student student) {
        return StudentLead.builder()
                .student(student)
                .whereLink(studentLeadAddDTO.getWhereLinkId() != null ? whereLinkRepository.findById(studentLeadAddDTO.getWhereLinkId()).orElseThrow(() -> new NotFoundException("link topilmadi")) : null)
                .comment(studentLeadAddDTO.getComment())
                .build();
    }
}
