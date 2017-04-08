package com.yodpook.android.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by apple on 12/20/16.
 */

public class TimeOperationHelper {

    private static DateFormat fullDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private static DateFormat hourMinuteDateFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static DateFormat pushDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static DateFormat displayDateFormat = new SimpleDateFormat("EEE, d MMM, yyyy", Locale.getDefault());

    public static String getDisplayTimeFromStartAndDuration(String startTimeText, String durationText) {
        int startTime = hourMinToMinute(startTimeText);
        int durationTime = Integer.valueOf(durationText);
        int endTime = startTime + durationTime;

        return startTimeText + " - " + minuteToHourString(endTime);
    }

    public static boolean isTimeBeforeCurrent(String stringDate) {
        Date currentDate = new Date();
        Date date = null;
        try {
            date = fullDateFormatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate.before(date);
    }

    public static String[] getRemovedTimeTextBeforeCurrent(String[] times) {
        ArrayList<String> timeArrayList = new ArrayList<>(Arrays.asList(times));
        String currentTime = hourMinuteDateFormatter.format(new Date());

        for (int i = timeArrayList.size() - 1; i >= 0; i--) {
            if (hourMinToMinute(timeArrayList.get(i)) < hourMinToMinute(currentTime)) {
                timeArrayList.remove(i);
            }
        }

        return timeArrayList.toArray(new String[timeArrayList.size()]);
    }

    public static int getClosestTimeFromArrayList(ArrayList<String> closeTimes, String time) {
        int min = Integer.MAX_VALUE;
        int closest = hourMinToMinute(time);

        for (String closeTime : closeTimes) {
            int diff = hourMinToMinute(closeTime) - hourMinToMinute(time);
            if (diff < min && diff > 0) {
                min = diff;
                closest = hourMinToMinute(closeTime);
            }
        }

        return closest;
    }

    public static ArrayList<String> getListOfCloseTime(ArrayList<String> startTimes, ArrayList<String> endTimes) {
        ArrayList<String> closeTimes = new ArrayList<>();
        for (String endTime : endTimes) {
            if (!startTimes.contains(endTime))
                closeTimes.add(endTime);
        }
        return closeTimes;
    }

    public static String[] getArrayOfTimeByStartAndEndTime(String startTime, String endTime) {
        int startMinute = hourMinToMinute(startTime);
        int endMinute = hourMinToMinute(endTime);
        ArrayList<String> listOfTime = new ArrayList<>();

        while (startMinute < endMinute) {
            listOfTime.add(minuteToHourString(startMinute));
            startMinute += 30;
        }
        return listOfTime.toArray(new String[listOfTime.size()]);
    }

    public static boolean isTimeAvailableCompareToAnother(String bookingTime1, String bookingDuration1,
                                                          String bookingTime2, String bookingDuration2) {

        int startTime1 = hourMinToMinute(bookingTime1);
        int endTime1 = startTime1 + Integer.valueOf(bookingDuration1);
        int startTime2 = hourMinToMinute(bookingTime2);
        int endTime2 = startTime2 + Integer.valueOf(bookingDuration2);

        return !((startTime2 < startTime1 && startTime1 < endTime2) ||
                (startTime2 < endTime1 && endTime1 < endTime2) ||
                (startTime1 < startTime2 && endTime1 > endTime2) ||
                startTime1 == startTime2 ||
                endTime1 == endTime2);
    }

    public static String getStartTimeFromDisplayTime(String displayTime) {
        return displayTime.split("-")[0];
    }

    public static String getDurationFromDisplayTime(String displayTime) {
        String[] splitTime = displayTime.split("-");
        int duration = hourMinToMinute(splitTime[1]) - hourMinToMinute(splitTime[0]);
        return String.valueOf(duration);
    }

    /*******************************************
     * Time
     *******************************************/

    public static int getCurrentTimeMilliSeconds() {
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    /*******************************************
     * Formatting
     *******************************************/

    public static String getPushStringFromDate(Date date) {
        return pushDateFormat.format(date);
    }

    public static String getPushDateFromDisplayDate(String displayDate) {
        try {
            Date date = displayDateFormat.parse(displayDate);
            return pushDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromPushString(String dateStr) {
        try {
            return pushDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDisplayDate(Date date) throws ParseException, NullPointerException {

        return displayDateFormat.format(date);
    }

    public static int hourMinToMinute(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    public static String minuteToHourString(int min) {
        long hours = TimeUnit.MINUTES.toHours(min);
        long remainMinute = min - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d", hours) + ":"
                + String.format("%02d", remainMinute);
    }
}

