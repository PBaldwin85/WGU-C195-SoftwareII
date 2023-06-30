package C195;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTime {

    public static String toLocal(String utcTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime utcDateTime = LocalDateTime.parse(utcTime, formatter);
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localTime = utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(localZoneId);
        String formattedTime = localTime.format(formatter);

        return formattedTime;
    }
}
