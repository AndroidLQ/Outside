package com.yunwang.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/9.
 */
public class StringUtils {

    /**
     * unicode转换成中文
     *
     * @param theString 需要转换的字符串
     * @return 转码之后的字符串
     */
    public static String decodeUnicode(String theString) {

        char aChar;

        int len = theString.length();

        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len; ) {

            aChar = theString.charAt(x++);

            if (aChar == '\\') {

                aChar = theString.charAt(x++);

                if (aChar == 'u') {

                    // Read the xxxx

                    int value = 0;

                    for (int i = 0; i < 4; i++) {

                        aChar = theString.charAt(x++);

                        switch (aChar) {

                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';

                    else if (aChar == 'n')

                        aChar = '\n';

                    else if (aChar == 'f')

                        aChar = '\f';

                    outBuffer.append(aChar);

                }

            } else

                outBuffer.append(aChar);

        }

        return outBuffer.toString();

    }

    /**
     * 判断字符串中是否还有中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    public static String getTodayCalendarView() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 获取格式化的时间时长(将毫秒换算成时分秒)
     *
     * @param time
     * @return 返回式化的时长
     */

    public static String formatTime(Long time) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = time / dd;
        Long hour = (time - day * dd) / hh;
        Long minute = (time - day * dd - hour * hh) / mi;
        Long second = (time - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = time - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "毫秒");
        }
        return sb.toString();
    }

    /**
     * 将毫秒转化为 时：分：秒 格式 ，例如  1时5分23秒
     *
     * @param milliSecondTime 毫秒数
     * @return 格式化之后的时间
     */
    public static String calculatTime(long milliSecondTime) {

        int hour = (int) (milliSecondTime / (60 * 60 * 1000));
        int minute = (int) ((milliSecondTime - hour * 60 * 60 * 1000) / (60 * 1000));
        int seconds = (int) ((milliSecondTime - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000);

        if (seconds >= 60) {
            seconds = seconds % 60;
            minute += seconds / 60;
        }
        if (minute >= 60) {
            minute = minute % 60;
            hour += minute / 60;
        }

        String sh = "";
        String sm = "";
        String ss = "";
        if (hour < 10) {
            sh = String.valueOf(hour);
        } else {
            sh = String.valueOf(hour);
        }
        if (minute < 10) {
            sm = String.valueOf(minute);
        } else {
            sm = String.valueOf(minute);
        }
        if (seconds < 10) {
            ss = String.valueOf(seconds);
        } else {
            ss = String.valueOf(seconds);
        }
        if ("0".equals(sh) && !"0".equals(sm)) {
            return sm + "分" + ss + "秒";
        }
        if ("0".equals(sh) && "0".equals(sm)) {
            return ss + "秒";
        }
        return sh + "时" + sm + "分" + ss + "秒";
    }

    /**
     * 格式化时间
     *
     * @param time 需要格式的时间
     * @return 格式化之后的时间
     */
    public static String formatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:MM:ss");
        String resultTime = format.format(time);
        return resultTime;
    }

    /**
     * 获取当前的年份和月份
     *
     * @return 当前的年份和月份  形式为2016年9月
     **/
    public static String getTodayYearAndMonthFormat() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        StringBuffer buffer = new StringBuffer();
        buffer.append(year).append("年").append(month).append("月");
        return buffer.toString();
    }

    /**
     * 获取当前的年份、月份和日期
     *
     * @return 当前的年份和月份 返回形式为数组
     */
    public static String[] getTodayYearAndMonthAndDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String resultMonth = "";
        if (month + 1 <= 10) {
            resultMonth = "0" + month;
        } else {
            resultMonth = String.valueOf(month);
        }
        String resultDay = "";
        if (day + 1 <= 10) {
            resultDay = "0" + day;
        } else {
            resultDay = String.valueOf(day);
        }
        String[] result = new String[]{String.valueOf(year), resultMonth, resultDay};
        return result;
    }

    /**
     * 获取当前的年份和月份
     *
     * @return 当前的年份和月份 返回形式为数组
     */
    public static String[] getTodayYearAndMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String resultMonth = "";
        if (month + 1 <= 10) {
            resultMonth = "0" + month;
        } else {
            resultMonth = String.valueOf(month);
        }
        String[] result = new String[]{String.valueOf(year), resultMonth};
        return result;
    }

    /**
     * 获取当前的年份和月份
     *
     * @return 当前的年份和月份 返回形式为数组
     */
    public static String getYearAndMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String resultMonth = "";
        if (month + 1 <= 10) {
            resultMonth = "0" + month;
        } else {
            resultMonth = String.valueOf(month);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(year).append("-").append(resultMonth);
        return buffer.toString();
    }

    /**
     * 获取当前的月份
     *
     * @return 当前的月份
     */
    public static String getTodayMonth() {
        String resultMonth = "";
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month + 1 <= 10) {
            resultMonth = "0" + month;
        } else {
            resultMonth = String.valueOf(month);
        }
        return resultMonth;
    }

    /**
     * 获取当前的月份和日期
     *
     * @return 当前的月份和日期
     */
    public static String getTodayMonthAndDay() {
        StringBuffer buffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        buffer.append(month).append("月").append(day).append("日");
        return buffer.toString();
    }

    /**
     * 获取当前的日
     *
     * @return 当前的日
     */
    public static String getTodayDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day);
    }

    /**
     * 时间比较
     *
     * @param endTime
     * @return
     */
    public static long compareTime(String endTime) {
        if (TextUtils.isEmpty(endTime)) {
            return 0;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        String currentMillis = formatter.format(date);

        long endMillionSeconds = 0, resultCurrentTimeMillis = 0;
        try {
            endMillionSeconds = formatter.parse(endTime).getTime();//毫秒
            resultCurrentTimeMillis = formatter.parse(currentMillis).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endMillionSeconds - resultCurrentTimeMillis;
    }

    /**
     * 时间比较
     * 只是用来比较时和分
     *
     * @param originalTime 开始时间
     * @param targetTime   结束时间
     * @return 毫秒数
     * <p/>
     * 得到的结果是目标时间减去原始的时间
     */
    public static long compareTime(String originalTime, String targetTime) {
        if (TextUtils.isEmpty(targetTime) || TextUtils.isEmpty(originalTime)) {
            return 0;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

//        long currentTimeMillis = System.currentTimeMillis();
//        Date date = new Date(currentTimeMillis);
//        String currentMillis = formatter.format(date);

        long startTempTime = 0, endTempTime = 0;
        try {
            //开始时间
            startTempTime = formatter.parse(originalTime).getTime();//毫秒
            //结束时间
            endTempTime = formatter.parse(targetTime).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //结束时间减去开始时间
        return endTempTime - startTempTime;
    }

    /**
     * 时间比较
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static long compareTimeOnYearAndMonth(String startTime, String endTime) {
        if (TextUtils.isEmpty(endTime) || TextUtils.isEmpty(startTime)) {
            return 0;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

//        long currentTimeMillis = System.currentTimeMillis();
//        Date date = new Date(currentTimeMillis);
//        String currentMillis = formatter.format(date);

        long startTempTime = 0, endTempTime = 0;
        try {
            //开始时间
            startTempTime = formatter.parse(startTime).getTime();//毫秒
            //结束时间
            endTempTime = formatter.parse(endTime).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTempTime - endTempTime;
    }

    /**
     * 获取当前时间，已经格式化之后的时间
     *
     * @return 需要秒数
     */
    public static String getCurrentFormatTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTimeMillis = System.currentTimeMillis();
        return formatter.format(currentTimeMillis);
    }

    /**
     * 获取当前时间，已经格式化之后的时间
     *
     * @return 不需要秒数
     */
    public static String getCurrentFormatTimeNoMills() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long currentTimeMillis = System.currentTimeMillis();
        return formatter.format(currentTimeMillis);
    }

    /**
     * 获取当前时间，已经格式化之后的时间
     *
     * @return 不需要秒数
     */
    public static String getCurrentFormatTimeNoHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long currentTimeMillis = System.currentTimeMillis();
        return formatter.format(currentTimeMillis);
    }

    /**
     * 获取当前时间，已经格式化之后的时间
     *
     * @return 不需要秒数
     */
    public static String getCurrentFormatTime2() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTimeMillis = System.currentTimeMillis();
        return formatter.format(currentTimeMillis);
    }

    /**
     * 分割时间
     *
     * @param time
     * @return
     */
    public static String getsplitTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        String[] times = time.split("-");
        if (times == null || times.length < 3) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(times[0]).append("年").append(times[1]).append("月").append(times[2]).append("日");
        return buffer.toString();
    }

    /**
     * 获取当前的年份
     *
     * @return
     */
    public static int getToadyYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * 判断是周几
     *
     * @param date 需要判断的日期
     * @return
     */
    public static int getDayofweek(String date) {
        Calendar cal = Calendar.getInstance();
//   cal.setTime(new Date(System.currentTimeMillis()));
        if (date.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            cal.setTime(new Date(date));
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 格式化小时和分钟
     *
     * @param time 需要格式化的时间
     * @return
     */
    public static String getFormatHourAndMinute(String time) {
        StringBuffer buffer = new StringBuffer();
        if (time.contains(" ")) {
            String[] times = time.split(" ");
            if (null != times && times.length >= 2) {
                //添加年份
//                buffer.append(times[0]).append(" ");
                if (times[1].contains(":")) {
                    String[] tempTimes = times[1].split(":");
                    if (null != tempTimes && tempTimes.length >= 2) {
                        if (tempTimes[0].length() < 2) {
                            //添加小时
                            buffer.append("0").append(tempTimes[0]);
                        } else {
                            //添加小时
                            buffer.append(tempTimes[0]);
                        }
                        buffer.append(":");
                        if (tempTimes[1].length() < 2) {
                            //添加分钟
                            buffer.append("0").append(tempTimes[1]);
                        } else {
                            //添加分钟
                            buffer.append(tempTimes[1]);
                        }
                    }
                }
            }
        }
        return buffer.toString();
    }


    /**
     * 格式化小时和分钟
     *
     * @param time 需要格式化的时间
     * @return
     */
    public static String getFormatYearAndMonthWithDay(String time) {
        StringBuffer buffer = new StringBuffer();
        String month = "";
        String day = "";
        if (time.contains(" ")) {
            String[] times = time.split(" ");
            if (null != times && times.length >= 2) {
                if (times[0].contains("-")) {
                    String[] tempTimes = times[0].split("-");
                    if (null != tempTimes && tempTimes.length >= 3) {
                        //添加年份
                        buffer.append(tempTimes[0]).append("-");
                        if (Integer.parseInt(tempTimes[1]) < 10) {
                            month = "0" + tempTimes[1];
                        } else {
                            month = tempTimes[1];
                        }
                        buffer.append(month).append("-");
                        if (Integer.parseInt(tempTimes[2]) < 10) {
                            day = "0" + tempTimes[2];
                        } else {
                            day = tempTimes[2];
                        }
                        //添加日期
                        buffer.append(day);
                    }
                }
            }
        }
        return buffer.toString();
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str 需要判断的字符串
     * @return true表示是数字  false表示不是数字
     **/
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
