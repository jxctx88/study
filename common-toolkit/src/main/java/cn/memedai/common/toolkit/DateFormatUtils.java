package cn.memedai.common.toolkit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chengtx on 2016/6/22.
 */
public class DateFormatUtils {

    protected static SimpleDateFormat FORMAT_YEAR2DAY = new SimpleDateFormat("yyyy-MM-dd");
    protected static SimpleDateFormat FORMAT_YEAR2SEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected static SimpleDateFormat FORMAT_HOUR2MIN = new SimpleDateFormat("HH:mm");
    protected static SimpleDateFormat FORMAT_HOUR2SEC = new SimpleDateFormat("HH:mm:ss");
    protected static SimpleDateFormat FORMAT_SHORT_YEAR2DAY_HOUR2SEC = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    protected static SimpleDateFormat FORMAT_SHORT_YEAR2DAY = new SimpleDateFormat("yyyyMMdd");
    protected static SimpleDateFormat FORMAT_SHORT_YEAR2SEC = new SimpleDateFormat("yyyyMMddHHmmss");
    protected static SimpleDateFormat FORMAT_SHORT_HOUR2MIN = new SimpleDateFormat("HHmm");
    protected static SimpleDateFormat FORMAT_SHORT_HOUR2SEC = new SimpleDateFormat("HHmmss");

    public static String formatYear2Day(Date date) {
        return FORMAT_YEAR2DAY.format(date);
    }

    public static String formatYear2Sec(Date date) {
        return FORMAT_YEAR2SEC.format(date);
    }

    public static String formatHour2Min(Date date) {
        return FORMAT_HOUR2MIN.format(date);
    }

    public static String formatHour2Sec(Date date) {
        return FORMAT_HOUR2SEC.format(date);
    }

    public static String formatShortYear2Day_Hour2Sec(Date date) {
        return FORMAT_SHORT_YEAR2DAY_HOUR2SEC.format(date);
    }

    public static String formatShortYear2Day(Date date) {
        return FORMAT_SHORT_YEAR2DAY.format(date);
    }

    public static String formatShortYear2Sec(Date date) {
        return FORMAT_SHORT_YEAR2SEC.format(date);
    }

    public static String formatShortHour2Min(Date date) {
        return FORMAT_SHORT_HOUR2MIN.format(date);
    }

    public static String formatShortHour2Sec(Date date) {
        return FORMAT_SHORT_HOUR2SEC.format(date);
    }

    public static Date parseYear2Day(String dateStr) {
        try {
            return FORMAT_YEAR2DAY.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseYear2Sec(String dateStr) {
        try {
            return FORMAT_YEAR2SEC.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseHour2Min(String dateStr) {
        try {
            return FORMAT_HOUR2MIN.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseHour2Sec(String dateStr) {
        try {
            return FORMAT_HOUR2SEC.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseHour2MinBaseDate(String dateStr, Date baseDate) {
        try {
            Date date = FORMAT_HOUR2MIN.parse(dateStr);
            Calendar calendar = toCalendar(date);
            Calendar baseCalendar = toCalendar(baseDate);
            calendar.set(Calendar.YEAR, baseCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, baseCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, baseCalendar.get(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseHour2SecBaseDate(String dateStr, Date baseDate) {
        try {
            Calendar calendar = toCalendar(FORMAT_HOUR2SEC.parse(dateStr));
            Calendar baseCalendar = toCalendar(baseDate);
            calendar.set(Calendar.YEAR, baseCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, baseCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, baseCalendar.get(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseShortYear2Day(String dateStr) {
        try {
            return FORMAT_SHORT_YEAR2DAY.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseShortYear2Sec(String dateStr) {
        try {
            return FORMAT_SHORT_YEAR2SEC.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseShortHour2Min(String dateStr) {
        try {
            return FORMAT_SHORT_HOUR2MIN.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseShortHour2Sec(String dateStr) {
        try {
            return FORMAT_SHORT_HOUR2SEC.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseShortHour2MinBaseDate(String dateStr, Date baseDate) {
        try {
            Calendar calendar = toCalendar(FORMAT_SHORT_HOUR2MIN.parse(dateStr));
            Calendar baseCalendar = toCalendar(baseDate);
            calendar.set(Calendar.YEAR, baseCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, baseCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, baseCalendar.get(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseShortHour2SecBaseDate(String dateStr, Date baseDate) {
        try {
            Calendar calendar = toCalendar(FORMAT_SHORT_HOUR2SEC.parse(dateStr));
            Calendar baseCalendar = toCalendar(baseDate);
            calendar.set(Calendar.YEAR, baseCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, baseCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, baseCalendar.get(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static boolean checkDateFormat(String date , SimpleDateFormat format){
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
