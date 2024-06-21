package it.live.crm.entity;

import it.live.crm.entity.tmp.AbsLong;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Student extends AbsLong {
    private String fullName;
    private String phoneNumber;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Group> group;
    private Boolean isStudent;
    @ManyToOne
    private Expectations expectations;
    private LocalDate birthDate;
    private Boolean gender;
    private String parentPhoneNumber;
    private String location;
    private String telegramLink;
    private String passportId;
    private Boolean isDeleted;
    private Double balance;
}
