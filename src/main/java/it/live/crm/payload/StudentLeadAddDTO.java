package it.live.crm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentLeadAddDTO {
    private String fullName;
    private Long whereLinkId;
    private String comment;
    private String phoneNumber;

}
