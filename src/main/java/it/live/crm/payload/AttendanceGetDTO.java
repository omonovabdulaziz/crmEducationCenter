    package it.live.crm.payload;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDate;
    import java.util.Date;
    import java.util.List;
    import java.util.Map;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class AttendanceGetDTO {
        private Map<Long, LocalDate> days;
        private List<StudentDTO> studentDTO;
    }
