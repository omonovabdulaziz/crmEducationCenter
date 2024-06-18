package it.live.crm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherGetDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private String avatarLink;
    private Boolean isDeleted;
}
