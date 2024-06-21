package it.live.crm.service.helper;

import it.live.crm.entity.Group;
import it.live.crm.entity.enums.Days;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonFinanceHelper {

    public Double initializeLessonPriceForPerDay(Group group, LocalDate from, LocalDate til) {
        Map<Long, LocalDate> dates = getDatesByWeekName(group.getDays(), from, til);
        int counter = 0;
        for (LocalDate value : dates.values()) {
            counter += 1;
        }

        Double price = group.getCourse().getPrice();
        return price / counter;
    }

    public Double initializeAllLessonPrice(Group group) {
        LocalDate today = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(today);
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();
        List<LocalDate> dates = today.datesUntil(lastDayOfMonth.plusDays(1)).toList();
        DateTimeFormatter dayNameFormatter = DateTimeFormatter.ofPattern("EEEE");

        int counter = 0;
        for (LocalDate date : dates) {
            String dayName = date.format(dayNameFormatter);
            for (Days day : group.getDays()) {
                if (dayName.equals(String.valueOf(day))) {
                    counter += 1;
                }
            }
        }
        Double price = group.getCourse().getPrice();
        return price / counter;
    }


    public Map<Long, LocalDate> getDatesByWeekName(List<Days> days, LocalDate from, LocalDate til) {
        Map<Long, LocalDate> daysMap = new HashMap<>();
        long dynamicId = 1L;

        LocalDate date = from;
        while (!date.isAfter(til)) {
            Days currentDay = convertDayOfWeekToDays(date.getDayOfWeek());
            if (days.contains(currentDay)) {
                daysMap.put(dynamicId, date);
                dynamicId++;
            }
            date = date.plusDays(1);
        }
        return daysMap;
    }

    private Days convertDayOfWeekToDays(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> Days.MONDAY;
            case TUESDAY -> Days.TUESDAY;
            case WEDNESDAY -> Days.WEDNESDAY;
            case THURSDAY -> Days.THURSDAY;
            case FRIDAY -> Days.FRIDAY;
            case SATURDAY -> Days.SATURDAY;
            case SUNDAY -> Days.SUNDAY;
            default -> throw new IllegalArgumentException("Unexpected value: " + dayOfWeek);
        };

    }

}
