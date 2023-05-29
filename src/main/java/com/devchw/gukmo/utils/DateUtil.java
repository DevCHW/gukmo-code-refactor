package com.devchw.gukmo.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {

    /**
     * 날짜를 몇 초전, 몇분 전, 몇시간 전, 몇일 전, 몇달 전, 몇년 전으로 나타내기
     */
    private static class TIME_MAXIMUM {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
    public static String calculateTime(LocalDateTime inputDate) {
        Instant instant = inputDate.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        long curTime = System.currentTimeMillis();
        long regTime = date.getTime();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            // sec
            msg = diffTime + "초 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            // min
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            // hour
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            // day
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            // day
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }

    /**
     * String -> LocalDateTime변환
     */
    public static LocalDateTime stringToLocalDateTimeConverter(String dateString) {
        // DateTimeFormatter를 사용하여 문자열을 LocalDateTime으로 변환
        dateString += " 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);

        return localDateTime;
    }


    /**
     * LocalDateTime -> "yyyy-mm-dd"형식의 String 변환
     */
    public static String formatLocalDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }


    /**
     * LocalDateTime - 현재날짜 (일 단위)
     */
    public static String getDaysFromNow(LocalDateTime dateTime) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = dateTime.toLocalDate();
        return String.valueOf(ChronoUnit.DAYS.between(currentDate, targetDate));
    }

    /**
     * 날짜 차이 반환
     */
    public static int calculateDateGap(LocalDateTime startDate, LocalDateTime endDate) {
        int result = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return result;
    }
}
