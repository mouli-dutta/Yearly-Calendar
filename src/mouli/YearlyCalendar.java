package mouli;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

/**
 * {@code YearlyCalendar} prints the 12 month Calendar of given year.
 *
 * Input: The year you want calendar of e.g. 2022
 *
 * @author Mouli
 */

public class YearlyCalendar {
    public static void main(String[] args) {

        try (var sc = new Scanner(System.in)) {

            int year = sc.nextInt(); // input year

            // boundary checks for correct input year
            // Thanks for the suggestion @bell !

            if (year <= 1752 || year > 9999)
                throw new IllegalArgumentException(
                        "Year must be greater than 1752"
                        + " and less than 9999.\n");


            // print calendar if everything is OK
            print(LocalDate.of(year, 1, 1));

            System.exit(0); // terminate the program

        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());

        } catch (Exception e) {

            System.out.println(
                    "Bad input.\nEnter year (e.g. "
                    + LocalDate.now().getYear()
                    + " )\n");
        }

        System.out.println(
                "Printing current calendar.\n\n");

        // print the current calendar
        // if something goes wrong
        print(LocalDate.now().with(
                TemporalAdjusters.firstDayOfYear())
        );
    }


    private static void print(LocalDate date) {

        var c = new Calendar(date);
        System.out.print(c.getCalendar());
    }
}


