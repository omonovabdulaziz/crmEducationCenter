package it.live.crm.payload;

import it.live.crm.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {
    private String fullName;
    private String phoneNumber;
    private Boolean isStudent;
    private Boolean isPaidThisMonth;
    private List<AttendanceDtoList> attendance;
}
