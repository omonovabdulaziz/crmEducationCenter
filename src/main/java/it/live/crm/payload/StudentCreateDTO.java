package it.live.crm.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreateDTO {
    private String fullName;
    private String phoneNumber;
    private Long groupId;
    private LocalDate birthDate;
    private Boolean gender;
    private String parentPhoneNumber;
    private String location;
    private String telegramLink;
    private String passportId;
}
