package it.live.crm.entity;

import it.live.crm.entity.tmp.AbsUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.YearMonth;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Finance extends AbsUUID {
    private String type;
    private Float summa;
    @ManyToOne
    private Group group;
    private YearMonth month;
    @ManyToOne
    private Student student;
}
