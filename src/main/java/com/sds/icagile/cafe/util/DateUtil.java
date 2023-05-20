package com.sds.icagile.cafe.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    public static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

    public static int weekday(String date) {
        try {
            int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
            Calendar cal = Calendar.getInstance();
            cal.setTime(YYYYMMDD.parse(date));
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
            return weekDays[w];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static String dateToStringWithFormat(String formatStr, int type, int minute) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Calendar nowDate = Calendar.getInstance();
        nowDate.add(type, minute);
        return format.format(nowDate.getTime());
    }

    public static String dateToString(int type, int minute) {
        Calendar nowDate = Calendar.getInstance();
        nowDate.add(type, minute);
        return YYYYMMDD.format(nowDate.getTime());
    }

    public static String addDay(String date, int dayNum) {
        Calendar nowDate = Calendar.getInstance();
        try {
            nowDate.setTime(YYYYMMDD.parse(date));
            nowDate.add(Calendar.DAY_OF_YEAR, dayNum);
            return YYYYMMDD.format(nowDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
