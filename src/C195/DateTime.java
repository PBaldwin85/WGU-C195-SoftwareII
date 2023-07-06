package C195;

import java.time.*;
import java.time.format.DateTimeFormatter;

/** DateTime class used to convert times from the UTC database time into the users local timezone.
 */
public class DateTime {

    public static String toLocal(String utcTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime utcDateTime = LocalDateTime.parse(utcTime, formatter);
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localTime = utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(localZoneId);
        String formattedTime = localTime.format(formatter);
        return formattedTime;
    }

    public static LocalDateTime toUtc(ZoneId timeZone, LocalDateTime selectedTime) {
        ZoneId est = ZoneId.of("UTC");

        ZonedDateTime zonedSelectedTime = ZonedDateTime.of(selectedTime, timeZone);
        ZonedDateTime convertedTime = zonedSelectedTime.withZoneSameInstant(est);


        return convertedTime.toLocalDateTime();
    }

}
