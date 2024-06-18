package it.live.crm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetChangeOrDenyDTO {
    private Boolean isCancelled;
    private LocalDate realDate;
    private LocalDate passedDate;
}
