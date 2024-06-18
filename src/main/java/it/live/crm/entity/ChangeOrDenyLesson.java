package it.live.crm.entity;

import it.live.crm.entity.tmp.AbsLong;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ChangeOrDenyLesson extends AbsLong {
    @ManyToOne
    private Group group;
    private LocalDate realDate;
    private LocalDate passedDate;
    private Boolean isCancelled;
}
