package it.live.crm.payload;

import it.live.crm.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeOrDenyLessonDTO {
    private Long groupId;
    private LocalDate realDate;
    private LocalDate passedDate;
}
