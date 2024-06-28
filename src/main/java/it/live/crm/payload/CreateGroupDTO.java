package it.live.crm.payload;

import it.live.crm.entity.enums.Days;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupDTO {
    private String groupName;
    private Long courseId;
    private Long teacherId;
    private LocalTime startTime;
    private List<Days> daysList;
    private LocalDate startDate;
}

