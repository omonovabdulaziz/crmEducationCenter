package it.live.crm.entity;

import it.live.crm.entity.enums.Type;
import it.live.crm.entity.tmp.AbsLong;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TeacherPercentage extends AbsLong {
    @OneToOne
    private User teacher;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Double count;
}
