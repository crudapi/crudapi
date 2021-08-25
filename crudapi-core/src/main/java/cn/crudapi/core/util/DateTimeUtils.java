package cn.crudapi.core.util;

import java.sql.Date;
import java.sql.Timestamp;

import org.joda.time.DateTime;

public final class DateTimeUtils {
    public static DateTime now() {
        return DateTime.now();
    }
    
    public static Date sqlDate() {
        return new Date(DateTime.now().getMillis());
    }
    
    public static Date sqlDatePlusDays(int days) {
        return new Date(DateTime.now().plusDays(days).getMillis());
    }
   
    public static Timestamp sqlTimestamp() {
        return new Timestamp(DateTime.now().getMillis());
    }
    
    public static Timestamp sqlTimestamp(DateTime dt) {
        return new Timestamp(dt.getMillis());
    }
    
    public static DateTime toDateTime(Timestamp timestamp) {
        return new DateTime(timestamp);
    }
    
    public static long toMillis(Timestamp timestamp) {
        return (new DateTime(timestamp)).getMillis();
    }
}
