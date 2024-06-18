package it.live.crm.entity;


import it.live.crm.entity.tmp.AbsUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TeacherAttendance extends AbsUUID {
    @ManyToOne
    private User teacher;
    @ManyToOne
    private Group group;
    private LocalDate date;
    private Double moneyCountForDate;
}
