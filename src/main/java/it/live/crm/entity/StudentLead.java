package it.live.crm.entity;

import it.live.crm.entity.tmp.AbsLong;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentLead extends AbsLong {
    @ManyToOne
    private WhereLink whereLink;
    @ManyToOne
    private Student student;
    private String comment;

}
