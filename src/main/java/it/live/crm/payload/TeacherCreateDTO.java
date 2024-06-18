package it.live.crm.payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherCreateDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private String password;
    private Boolean sex;
    private LocalDate birthDate;
}
