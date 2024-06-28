package it.live.crm.service.helper;

import it.live.crm.entity.Group;
import it.live.crm.entity.enums.Days;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HelperFunctions {

    public Double initializePerDayPrice(Group group) {
        YearMonth currentYearMonth = YearMonth.now();
        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();
        List<LocalDate> dates = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1)).toList();
        DateTimeFormatter dayNameFormatter = DateTimeFormatter.ofPattern("EEEE");

        int counter = 0;
        for (LocalDate date : dates) {
            String dayName = date.format(dayNameFormatter);
            for (Days day : group.getDays()) {
                if (dayName.equalsIgnoreCase(day.name())) {
                    counter += 1;
                }
            }
        }

        Double price = group.getCourse().getPrice();
        System.out.println(price / counter);
        return counter > 0 ? price / counter : 0.0;
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

    public LocalDate identificationByDuring(LocalDate startDate, Integer monthCount) {
        return startDate.plusDays(monthCount).with(TemporalAdjusters.lastDayOfMonth());
    }

}
