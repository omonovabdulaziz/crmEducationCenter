package it.live.crm.entity;

import it.live.crm.entity.tmp.AbsLong;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Expectations extends AbsLong {
    private String name;
}
