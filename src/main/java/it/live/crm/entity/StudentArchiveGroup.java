package it.live.crm.entity;

import it.live.crm.entity.tmp.AbsLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
public class StudentArchiveGroup extends AbsLong {
    @ManyToOne
    private Student student;
    @ManyToOne
    private Group group;
    @Column(name = "boshlanishi")
    private LocalDate from;
    private LocalDate til;
}
