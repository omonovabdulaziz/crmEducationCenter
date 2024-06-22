package it.live.crm.entity;

import it.live.crm.entity.tmp.AbsUUID;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"teacher_id", "student_id", "group_id"})}
)
public class TeacherFinance extends AbsUUID {
    @ManyToOne
    private User teacher;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Group group;
    private Double commonSum;
}
