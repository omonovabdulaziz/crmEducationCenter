package it.live.crm.entity;

import it.live.crm.entity.tmp.AbsLong;
import lombok.*;

import java.time.Month;
import java.time.Year;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherMonthly extends AbsLong {
    private User teacher;
    private Month month;
    private Year year;
    private Double moneyCount;
}
