package it.live.crm.entity;

import it.live.crm.entity.enums.Days;
import it.live.crm.entity.tmp.AbsLong;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "guruh")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Group extends AbsLong {
    private String name;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User teacher;
    private Boolean isGroup;
    private LocalTime startTime;
    @ElementCollection(targetClass = Days.class)
    @Enumerated(EnumType.STRING)
    private List<Days> days;
    private LocalDate startDate;
    private Boolean isFinished;
}
