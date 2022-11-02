package mouli;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Calendar implements ICalendar {

    private final LocalDate givenYear;
    private final StringBuilder sb;

    public Calendar(LocalDate givenYear) {
        this.givenYear = givenYear;
        this.sb = new StringBuilder();

    }


    public String getCalendar() {
        // get calendar for 12 months

        IntStream.range(0, MONTHS_IN_YEAR)
                .mapToObj(givenYear::plusMonths)
                .forEach(month -> {

                    // append month names
                    sb.append(getMonthsName(month))
                            .append(LINE).append("|") // separator
                            .append(getDaysName()) // day names
                            .append("\n").append(LINE) // separator
                            .append(datesInMonth(month)) // dates
                            .append("\n")
                            .append(LINE).append("\n\n"); // separator

                });

        return sb.toString();
    }


    // month names, e.g. JANUARY, FEBRUARY etc.
    private String getMonthsName(LocalDate month) {

        return "%18s %s%n".formatted(
                month.getMonth(), givenYear.getYear());
    }


    // Day names e.g. SUN, MON etc.
    private String getDaysName() {
        return
                IntStream.rangeClosed(1, DAYS_IN_WEEK)
                        .mapToObj(DayOfWeek::of)
                        .map(Enum::toString)
                        .map(s -> "%3s |"
                                .formatted(s.substring(0, 3))
                        )
                        .collect(Collectors.joining());
    }


    // generates the dates
    private String datesInMonth(LocalDate currentMonth) {
        // stores all the dates
        var dates = new StringBuilder();

        // to track 7 days in a column
        var counter = new AtomicInteger(1);

        // get the start day value of current month
        int dayValue = getDayValue(currentMonth);

        // adjust spaces to start
        // the month on it's respective day
        if (dayValue != DAYS_IN_WEEK) {

            IntStream.range(0, dayValue)
                    .forEach(i -> {

                        dates.append(String.format("%5s", ""));
                        counter.getAndIncrement();

                    });
        } // end of adjusting space

        dates.append("|");


        // loop from 1 to end of current month
        // to print all the dates
        IntStream.rangeClosed
                (1, currentMonth.lengthOfMonth())

                .forEach(date -> {

                    // append dates
                    dates.append(String.format(
                            isToday(currentMonth, date)
                                    ? "*%02d |" : " %02d |", date
                    ));

                    // if 7 days in a row
                    // and it's not end of month
                    // then add a new line and
                    // continue the table pattern
                    if ((counter.get() % DAYS_IN_WEEK) == 0 &&
                        date != currentMonth.lengthOfMonth()) {

                        dates.append("\n")
                                .append(LINE).append("|"); // separator
                    }

                    counter.getAndIncrement();

                }); // end of printing dates

        return dates.toString();

    }


    // day value [monday is 0 and sunday is 6]
    private int getDayValue(LocalDate currentMonth) {

        return
                currentMonth.with(
                        TemporalAdjusters.firstDayOfMonth()
                ) // first date of the month
                        .getDayOfWeek() // get day
                        .getValue() - 1; // day value
    }


    // checks if current date is today
    private boolean isToday(LocalDate currentMonth, int date) {

        return LocalDate.now()
                .equals(
                        LocalDate.of(
                                givenYear.getYear(),
                                currentMonth.getMonth().getValue(),
                                date)
                );
    }

}
