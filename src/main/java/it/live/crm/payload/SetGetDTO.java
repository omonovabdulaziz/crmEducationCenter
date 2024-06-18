package it.live.crm.payload;


import it.live.crm.entity.Course;
import it.live.crm.entity.User;
import it.live.crm.entity.enums.Days;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetGetDTO {
    private Long setId;
    private String name;
    private Course course;
    private User teacher;
    private List<Days> days;
}
