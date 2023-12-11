package tech.xixing.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class TimeUtil {

    public static Long getCurrentHour() {
        return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
    }

    public static Long getCurrentMinute() {
        return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
    }

    /**
     * 获取当前的时间戳按十秒来计算，即17：21：22秒变成172120
     * @param timestamp 时间戳
     * @return 时间以十秒为单位
     */
    public static Long getTimestamp10Second(Long timestamp) {
        long time = Long.parseLong(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        long second = time%10;
        return time-second;
    }

    public static Long getDayBeforeTimestamp(Integer day) {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(day);
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();

    }

    public static Long getTimestampByStr(String str) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static String getTimeStrByTimestamp(Long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8")).format(DateTimeFormatter.ofPattern("HH时mm分ss秒"));
    }

    public static void main(String[] args) {
        System.out.println(getTimestamp10Second(System.currentTimeMillis()));
    }
}
