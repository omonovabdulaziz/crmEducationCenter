package it.live.crm.payload;

import it.live.crm.entity.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetTeacherPercentageDTO {
    private Long teacherId;
    private Double percentage;
    private Type type;
    private Boolean isUpdating;
}
