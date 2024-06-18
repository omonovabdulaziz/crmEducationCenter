package it.live.crm.entity;


import it.live.crm.entity.tmp.AbsUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Attendance extends AbsUUID {
    @ManyToOne
    private Student student;
    private LocalDate attendanceDate;
    private Boolean isCome;
}
