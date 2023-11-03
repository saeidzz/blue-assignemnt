package com.blubank.doctorappointment.util;

import com.blubank.doctorappointment.enums.DateFormat;
import com.blubank.doctorappointment.enums.DateUnit;
import com.blubank.doctorappointment.exception.InvalidDateFormatException;
import com.blubank.doctorappointment.exception.InvalidInputDateException;
import com.ibm.icu.util.ULocale;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CalendarUtil {

    private static final ZoneId zoneId = ZoneId.of("UTC");


    public static LocalDateTime toLocalDateTime(long ts, String timezone) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(ts), ZoneId.of(timezone));
    }


    public static LocalDateTime getCurrentDateTime() {
        LocalDate currentDate = getCurrentDate();
        return currentDate.atTime(LocalTime.now(zoneId));
    }


    public static LocalDate getCurrentDate() {
        LocalDate currentDate = Clock.system(zoneId).instant().atZone(zoneId).toLocalDate();
        return currentDate;
    }


    public static Instant toInstant(LocalDateTime time) {
        return time.atZone(zoneId).toInstant();
    }


    public static LocalDate add(DateUnit dateUnit, int amount) {
        return add(getCurrentDate(), dateUnit, amount);
    }


    public static LocalDate add(LocalDate originDate, DateUnit dateUnit, int amount) {
        if (originDate == null) {
            return null;
        }
        ULocale locale = new ULocale("UTC");
        com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance(locale);
        calendar.setTime(Date.from(originDate.atStartOfDay().atZone(zoneId).toInstant()));

        switch (dateUnit) {
            case DAY:
                calendar.add(com.ibm.icu.util.Calendar.DAY_OF_MONTH, amount);
                break;
            case WEEk:
                calendar.add(com.ibm.icu.util.Calendar.DAY_OF_MONTH, amount * 7);
                break;
            case MONTH:
                calendar.add(com.ibm.icu.util.Calendar.MONTH, amount);
                break;
            case YEAR:
                calendar.add(com.ibm.icu.util.Calendar.YEAR, amount);
                break;
            default:
                return null;
        }
        return calendar.getTime().toInstant().atZone(zoneId).toLocalDate();
    }


    public static LocalDateTime add(LocalDateTime originDate, DateUnit dateUnit, int amount) {
        if (originDate == null) {
            return null;
        }
        ULocale locale = new ULocale("UTC");
        com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance(locale);
        calendar.setTime(Date.from(originDate.atZone(zoneId).toInstant()));

        switch (dateUnit) {
            case HOUR:
                calendar.add(com.ibm.icu.util.Calendar.HOUR_OF_DAY, amount);
                break;
            case DAY:
                calendar.add(com.ibm.icu.util.Calendar.DAY_OF_MONTH, amount);
                break;
            case WEEk:
                calendar.add(com.ibm.icu.util.Calendar.DAY_OF_MONTH, amount * 7);
                break;
            case MONTH:
                calendar.add(com.ibm.icu.util.Calendar.MONTH, amount);
                break;
            case YEAR:
                calendar.add(com.ibm.icu.util.Calendar.YEAR, amount);
                break;
            default:
                return null;
        }
        return calendar.getTime().toInstant().atZone(zoneId).toLocalDateTime();
    }


    public static int compare(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        int result = 0;

        if (date1.isAfter(date2)) {
            result = 1;
        } else if (date1.isBefore(date2)) {
            result = -1;
        }

        return result;
    }


    public static int diffDays(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            return 0;
        }
        int days = (int) ChronoUnit.DAYS.between(fromDate, toDate);
        if (days < 0) {
            throw new InvalidInputDateException();
        }
        return days;
    }


    public static int diffMinutes(LocalDateTime fromDate, LocalDateTime toDate) {
        if (fromDate == null || toDate == null) {
            return 0;
        }
        int minutes = (int) ChronoUnit.MINUTES.between(fromDate, toDate);
        if (minutes < 0) {
            throw new InvalidInputDateException();
        }
        return minutes;
    }


    public static String format(LocalDate localDate, DateFormat format) {
        if (localDate != null) {
            ULocale locale = new ULocale("UTC");
            com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance(locale);
            calendar.clear();
            calendar.setTime(Date.from(localDate.atStartOfDay().atZone(zoneId).toInstant()));

            if (format == DateFormat.BASIC_DATE) {
                return zeroExtend(calendar.get(com.ibm.icu.util.Calendar.YEAR), 4) + "/" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.MONTH) + 1, 2) + "/" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.DAY_OF_MONTH), 2);
            }
            throw new InvalidDateFormatException();
        } else {
            return "yyyy/MM/dd";
        }
    }


    public static String format(LocalDateTime localDateTime, DateFormat format) {
        if (localDateTime != null) {
            ULocale locale = new ULocale("UTC");
            com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance(locale);
            calendar.clear();
            calendar.setTime(Date.from(localDateTime.atZone(zoneId).toInstant()));

            return switch (format) {
                case BASIC_DATE_TIME -> zeroExtend(calendar.get(com.ibm.icu.util.Calendar.YEAR), 4) + "/" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.MONTH) + 1, 2) + "/" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.DAY_OF_MONTH), 2) + " " + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.HOUR_OF_DAY), 2) + ":" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.MINUTE), 2) + ":" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.SECOND), 2);
                case BASIC_DATE_TIME_DASH ->
                        zeroExtend(calendar.get(com.ibm.icu.util.Calendar.YEAR), 4) + "-" + zeroExtend(
                                calendar.get(com.ibm.icu.util.Calendar.MONTH) + 1, 2) + "-" + zeroExtend(
                                calendar.get(com.ibm.icu.util.Calendar.DAY_OF_MONTH), 2) + " " + zeroExtend(
                                calendar.get(com.ibm.icu.util.Calendar.HOUR_OF_DAY), 2) + ":" + zeroExtend(
                                calendar.get(com.ibm.icu.util.Calendar.MINUTE), 2) + ":" + zeroExtend(
                                calendar.get(com.ibm.icu.util.Calendar.SECOND), 2);
                case TIME -> zeroExtend(calendar.get(com.ibm.icu.util.Calendar.HOUR_OF_DAY), 2) + ":" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.MINUTE), 2) + ":" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.SECOND), 2);
                case BASIC_DATE_TIME_GREGORIAN -> calendar.get(com.ibm.icu.util.Calendar.YEAR) + "-" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.MONTH) + 1, 2) + "-" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.DAY_OF_MONTH), 2) + " " + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.HOUR_OF_DAY), 2) + ":" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.MINUTE), 2) + ":" + zeroExtend(
                        calendar.get(com.ibm.icu.util.Calendar.SECOND), 2);
                default -> throw new InvalidDateFormatException();
            };
        } else {
            return "yyyy/MM/dd hh:mm:ss";
        }
    }

    private static String zeroExtend(int number, int length) {
        return String.format("%0" + length + "d", number);
    }


    public static LocalDate formatAsLocalDate(String date, DateFormat format) {
        if (date != null && !date.isEmpty()) {
            ULocale locale = new ULocale("UTC");
            com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance(locale);
            calendar.clear();

            date = StringUtils.trim(date);
            String[] dateArr = null;
            switch (format) {
                case BASIC_DATE -> {
                    dateArr = StringUtils.split(date, "/");
                    calendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1,
                            Integer.parseInt(dateArr[2]));
                }
                case BASIC_DATE_DASH -> {
                    dateArr = StringUtils.split(date, "-");
                    calendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1,
                            Integer.parseInt(dateArr[2]));
                }
                default -> throw new InvalidDateFormatException();
            }

            return calendar.getTime().toInstant().atZone(zoneId).toLocalDate();
        }
        return null;
    }


    public static LocalDateTime formatAsLocalDateTime(String dateTime, DateFormat format) {
        if (dateTime != null) {
            ULocale locale = new ULocale("UTC");
            com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance(locale);
            calendar.clear();

            String[] dateTimeArr;
            String date;
            String time;
            String[] dateArr;
            String[] timeArr;

            dateTime = StringUtils.trim(dateTime);
            switch (format) {
                case BASIC_DATE_TIME -> {
                    dateTimeArr = StringUtils.split(dateTime, " ");
                    date = dateTimeArr[0];
                    time = dateTimeArr[1];
                    dateArr = StringUtils.split(date, "/");
                    timeArr = StringUtils.split(time, ":");
                    calendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1,
                            Integer.parseInt(dateArr[2]), Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]),
                            Integer.parseInt(timeArr[2]));
                }
                case BASIC_DATE_TIME_DASH, BASIC_DATE_TIME_GREGORIAN -> {
                    dateTimeArr = StringUtils.split(dateTime, " ");
                    date = dateTimeArr[0];
                    time = dateTimeArr[1];
                    dateArr = StringUtils.split(date, "-");
                    timeArr = StringUtils.split(time, ":");
                    calendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1,
                            Integer.parseInt(dateArr[2]), Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]),
                            Integer.parseInt(timeArr[2]));
                }
                default -> throw new InvalidDateFormatException();
            }
            return calendar.getTime().toInstant().atZone(zoneId).toLocalDateTime();
        } else {
            return null;
        }
    }
}
