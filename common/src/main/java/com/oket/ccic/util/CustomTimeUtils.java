package com.oket.ccic.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class CustomTimeUtils {
    private static final Logger logger = LoggerFactory.getLogger(CustomTimeUtils.class);
    public static final long MILLI_SECOND_PER_SECOND = 1000;

    public static final long SECOND_PER_MINUTE = 60;

    public static final long MINUTE_PER_HOUR = 60;

    public static final long HOUR_PER_DAY = 24;

    public static final long MILLI_SECOND_PER_DAY = HOUR_PER_DAY
            * MINUTE_PER_HOUR * SECOND_PER_MINUTE * MILLI_SECOND_PER_SECOND;

    private static DateFormat dateFormat = DateFormat.getDateTimeInstance();


    /*
     * @Author ld
     * @Description //把时间类型转化为MM/dd/yyyy HH:mm:ss
     * @Date 8:36 2018/8/15
     * @Param [date]
     * @return java.util.Date
     **/
    public static String getSecondTime(String secondString) throws ParseException {
        String secondEnString = secondString.substring(5, 7) + "/"
                + secondString.substring(8, 10) + "/" + secondString.substring(0, 4) + " "
                + secondString.substring(11, 13) + ":"
                + secondString.substring(14, 16) + ":"
                + secondString.substring(17, 19);
        return secondEnString;
    }

    /**
     * 四舍五入毫秒数
     *
     * @param time
     */
    public static void roundSecond(Date time) {
        long timeStamp = time.getTime();
        timeStamp = (timeStamp + 500) / 1000 * 1000;
        time.setTime(timeStamp);
    }

    /**
     * @return Stringf
     * @desc get Current Time
     * @format yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTimeByFormatSecond() {
        SimpleDateFormat secondFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return secondFormat.format(new Date());
    }

    /**
     * @return Date
     * @desc get Current Time
     */
    public static Date getCurrentTime() {
        return new Date();
    }

    /**
     * 返回无间隔符的时间格式
     *
     * @param string
     * @return
     * @throws ParseException
     */
    public static String onlyNum(String string) throws ParseException {
        return string.replace("-", "")
                .replace(":", "")
                .replace(" ", "");
    }

    private static SimpleDateFormat Format = new SimpleDateFormat(
            "HHmmssddMMyy");

    private static String dateTimeFormatText = "yyyyMMddHHmmss";

    public static Date millsecondFormatParseDate(String dateString)
            throws ParseException {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS").parse(dateString);
    }

    /**
     * @param time
     * @return Date
     * @desc Date TO String
     * @format yyyy-MM-dd HH:mm:ss
     */
    public static String formatToSecond(Date time) {
        SimpleDateFormat secondFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return secondFormat.format(time);
    }

    /**
     * @param minuendTime
     * @param subtrahendTime
     * @return Long
     * @desc get Time Defference
     */
    public static Long getTimeDifference(Date minuendTime, Date subtrahendTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minuendTime);
        Long minuend = calendar.getTimeInMillis();
        calendar.setTime(subtrahendTime);
        Long subtrahend = calendar.getTimeInMillis();

        Long millisecond = minuend - subtrahend;
        return millisecond;
    }

    /**
     * 将'yyyy-MM-DD HH:MM'格式的日期字符串转换为日期类型
     *
     * @param dateString String
     * @return Date
     */
    public static Date parseDate(String dateString, String addStr)
            throws ParseException {
        Date ret = null;
        if (dateString != null) {
            dateString += addStr;
            ret = dateFormat.parse(dateString);
        }
        return ret;
    }

    public static Date parseDate(String dateString) throws ParseException {
        return CustomTimeUtils.parseDate(dateString, ":00");
    }


    public static Date parseEropDate(String dateString) throws ParseException {
        return new SimpleDateFormat(
                "dd.MM.yyyy HH:mm:ss").parse(dateString);
    }

    public static Date parseEropsDate(String dateString) throws ParseException {
        return new SimpleDateFormat(
                "dd.MM.yy HH:mm").parse(dateString);
    }

    /**
     * 将'yyyy-MM-DD HH:MM'格式的日期字符串转换为格林威治日期类型
     *
     * @param dateString String
     * @return GregorianCalendar
     * @throws ParseException
     */
    public static GregorianCalendar parseGregorianCalendar(String dateString,
                                                           String addStr) throws ParseException {
        Date date = parseDate(dateString, addStr);
        GregorianCalendar ret = null;
        if (date != null) {
            ret = new GregorianCalendar();
            ret.setTime(date);
        }
        return ret;
    }

    /**
     * 获得两个时间中较早的一个时间
     *
     * @param dateOne Date
     * @param dateTwo Date
     * @return Date
     */
    public static Date getBeforeDate(Date dateOne, Date dateTwo) {
        if (dateOne.before(dateTwo)) {
            return dateOne;
        }
        return dateTwo;
    }

    /**
     * 获得两个时间中较晚的一个时间
     *
     * @param dateOne Date
     * @param dateTwo Date
     * @return Date
     */
    public static Date getAfterDate(Date dateOne, Date dateTwo) {
        if (dateOne.after(dateTwo)) {
            return dateOne;
        }
        return dateTwo;
    }

    public static Date parseToHourMinute(String timeStr) throws ParseException {
        return new SimpleDateFormat(
                "HH:mm").parse(timeStr);
    }

    public static String formateToHourMinute(Date date) {
        return new SimpleDateFormat(
                "HH:mm").format(date);
    }

    public static Date parseToMinute(String timeStr) throws ParseException {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm").parse(timeStr);
    }

    public static String formateToMinute(Date date) {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm").format(date);
    }

    public static String formateToSecond(Date date) {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formateToTime(Date date) {
        return new SimpleDateFormat(
                "HH:mm:ss").format(date);
    }

    public static Date formateToDate(String date) throws ParseException {
        return new SimpleDateFormat(
                "HH:mm:ss").parse(date);
    }

    public static String formatToSecondID(Date date) {
        return new SimpleDateFormat(
                "yyyyMMddHHmmss").format(date);
    }

    public static Date parseToYearNumberDate(String timeStr)
            throws ParseException {
        return new SimpleDateFormat(
                "yyyyMMddHHmmss").parse(timeStr);
    }

    public static String formateToShortYearNumberDate(Date date) {
        return new SimpleDateFormat(
                "yyMMddHHmmss").format(date);
    }

    public static String formateDate(Date date) {
        return Format.format(date);
    }

    public static Date parseToShortYearNumberDate(String timeStr)
            throws ParseException {
        return new SimpleDateFormat(
                "yyMMddHHmmss").parse(timeStr);
    }

    public static Date parseToSecond(String timeStr) throws ParseException {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").parse(timeStr);
    }

    public static String toSecondeString(Date date) {
        String secondString = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(date);
        return secondString.substring(0, 4) + secondString.substring(5, 7)
                + secondString.substring(8, 10)
                + secondString.substring(11, 13)
                + secondString.substring(14, 16)
                + secondString.substring(17, 19);
    }

    public static String toSecondeString1(Date date) {
        String secondString = new SimpleDateFormat(
                "yyyy-MM-ddHH:mm:ss").format(date);
        return secondString.substring(0, 4) + secondString.substring(5, 7)
                + secondString.substring(8, 10)
                + secondString.substring(11, 13)
                + secondString.substring(14, 16)
                + secondString.substring(17, 19);
    }

    public static String splitDateString(String date) {
        String split[] = date.substring(0, 10).split("-");
        String retString = "";
        for (int i = 0; i < split.length; i++) {
            retString += split[i];
        }
        return retString;
    }

    public static Date parseToPrintMinute(String timeStr) throws ParseException {
        return new SimpleDateFormat(
                "dd/HHmm").parse(timeStr);
    }

    public static String formateToPrintMinute(Date date) {
        return new SimpleDateFormat(
                "dd/HHmm").format(date);
    }

    public static String formatToDateTime(Date date) {
        return new SimpleDateFormat(dateTimeFormatText).format(date);
    }

    public static Date parseToDay(String timeStr) throws ParseException {
        return new SimpleDateFormat(
                "yyyy-MM-dd").parse(timeStr);
    }

    public static String formateToDay(Date date) {
        return new SimpleDateFormat(
                "yyyy-MM-dd").format(date);
    }

    public static Date parseToMonth(String timeStr) throws ParseException {
        return new SimpleDateFormat(
                "yyyy-MM").parse(timeStr);
    }

    public static String formatToMonth(Date date) {
        return new SimpleDateFormat(
                "yyyy-MM").format(date);
    }

    /**
     * 带下划线的格式化时间
     *
     * @param date
     * @return
     */
    public static String formatToMonthWithUnderline(Date date) {
        return new SimpleDateFormat(
                "yyyy_MM").format(date);
    }

    /**
     * 根据日期返回日期最后的时间点
     *
     * @param date
     * @return String
     */
    public static String getEndTimeOfDate(String date) {
        return date + " 23:59:59.999";
    }

    public static Date getEndTimeOfDate(Date date) throws ParseException {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat(
                "yyyy-MM-dd").format(date) + " 23:59:59.999");
    }

    /**
     * 根据日期返回日期最初的时间点
     *
     * @param date
     * @return String
     */
    public static String getStartTimeOfDate(String date) {
        return date + " 00:00:00.001";
    }

    public static long getTime(String timeStr) {
        java.sql.Date date = java.sql.Date.valueOf(timeStr);
        return date.getTime();
    }

    /**
     * 获得月份字符串 ，如2008-09-09 ————>0809
     *
     * @param date
     * @return
     */
    public static String getMonthStr(Date date) {
        return toSecondeString(date).substring(2, 6);
    }

    /**
     * 获得月份字符串 ，如2008-09-09 ————>0909
     *
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        return toSecondeString(date).substring(4, 8);
    }

    /**
     * 格式时间 yyyyMM
     *
     * @param date
     * @return
     */
    public static String formatToyyyyMM(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        return dateFormat.format(date);
    }

    /**
     * Description:获取制定月份的上一个月的最后一天 输入的格式 如2008-10
     *
     * @param timeStr
     * @return
     * @throws ParseException Time: Nov 4, 20084:44:11 PM
     * @author liuruiping
     */
    public static String getPrevMonthEnd(String timeStr) throws ParseException {
        Date date = CustomTimeUtils.parseToMonth(timeStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formateToDay(calendar.getTime());
    }

    /**
     * Description:获取制定月份的上上一个月的最后一天 输入的格式 如2008-10
     *
     * @param timeStr
     * @return
     * @throws ParseException Time: Nov 4, 20084:47:56 PM
     * @author liuruiping
     */
    public static String getPrevPrevMonthEnd(String timeStr)
            throws ParseException {
        Date date = CustomTimeUtils.parseToMonth(timeStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formateToDay(calendar.getTime());
    }

    public static String getFormatedDateString(float timeZoneOffset) {
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }

        int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    /**
     * Description:获得上一个与的年月 输入的格式 如2008-10
     *
     * @param timeStr
     * @return
     * @throws ParseException Time: Nov 5, 200811:17:22 AM
     * @author liuruiping
     */
    public static String getPreMonth(String timeStr) throws ParseException {
        Date date = CustomTimeUtils.parseToMonth(timeStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatToMonth(calendar.getTime());
    }

    /**
     * Description:获得当前日期的下几天
     *
     * @param nextDayNum
     * @return Time: Dec 19, 20085:06:55 PM
     * @author liuruiping
     */
    public static String getPreDay(int nextDayNum) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -nextDayNum * 24);
        String preDay = formateToDay(calendar.getTime());
        return preDay;
    }

    /**
     * 将String类型的时间转换为Date类型 String类型格式“yyyy-MM-dd HH:mm”
     *
     * @param timeStr
     * @return Date
     * @throws ParseException
     */
    public static Date parseToHourMinutes(String timeStr) throws ParseException {
        SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return minuteFormat.parse(timeStr);
    }

    public static Date parseToHHmm(String timeStr) throws ParseException {
        SimpleDateFormat minuteFormat = new SimpleDateFormat("HH:mm");
        return minuteFormat.parse(timeStr);
    }

    /**
     * 将String类型的时间转换为Date类型 String类型格式“HH:mm”
     *
     * @param timeStr
     * @return Date
     * @throws ParseException
     */
    public static Date parseToMinutes(String timeStr) throws ParseException {
        SimpleDateFormat minuteFormat = new SimpleDateFormat("HH:mm");
        return minuteFormat.parse(timeStr);
    }

    /**
     * 获取当前时间，以String类型返回 格式："yyyy-MM-dd HH:mm"
     *
     * @return
     */
    public static String formatToMinute() {
        SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return minuteFormat.format(new Date());
    }

    public static String formatToDay(Date calcDate) {
        SimpleDateFormat dayormat = new SimpleDateFormat("yyyy-MM-dd");
        return dayormat.format(calcDate);
    }

    /**
     * 获取当前日期 "yyyy-MM-dd"
     *
     * @return
     */
    public static String getCurrentDay() {
        SimpleDateFormat dayormat = new SimpleDateFormat("yyyy-MM-dd");
        return dayormat.format(new Date());
    }

    /**
     * 获取当前月份 "yyyy-MM"
     *
     * @return
     */
    public static String getCurrentMonth() {
        SimpleDateFormat dayormat = new SimpleDateFormat("yyyy-MM");
        return dayormat.format(new Date());
    }

    /**
     * 获取当前时间，以String类型返回 参数为自定义格式
     *
     * @param type
     * @return
     */
    public static String getCurrentTimeByFormat(String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(new Date());
    }

    /**
     * Description:比较时间大小 时间格式必须为："HH:mm"<br>
     * dataTime(大)在systemTime(小)之前返回true，否则返回false<br>
     */
    public static boolean compareTime(String dataTime, String systemTime)
            throws ParseException {
        Date dataD = parseToHHmm(dataTime);
        Date systemD = parseToHHmm(systemTime);
        if (dataD.after(systemD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较2个时间大小 String类型格式为“yyyy-MM-dd HH:mm”
     * dataTime(小)在systemTime(大)之前返回false，否则返回true
     */
    public static boolean compareTime1(String dataTime, String systemTime)
            throws ParseException {
        Date dataD = parseToHourMinute(dataTime);
        Date systemD = parseToHourMinute(systemTime);
        if (dataD.after(systemD)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据系统时间获取当前是星期几 '星期日:0 --- 星期六：6'
     */
    public static int getWeek(String systemTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(systemTime));
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return week;
    }

    /**
     * 获取2个时间差，返回以分钟为单位的时间 注意参数的时间格式必须为:'yyyy-MM-dd HH:mm' dataTime > systemTime
     * 为'正数'，否则为'负数'
     *
     * @param dataTime
     * @param systemTime
     * @return
     * @throws ParseException
     */
    public static int getTimeDiff(String dataTime, String systemTime)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(systemTime));
        long systemL = calendar.getTimeInMillis();

        calendar.setTime(format.parse(dataTime));
        long dataL = calendar.getTimeInMillis();

        long TimeMinut = ((dataL - systemL) / 60000);
        return (int) TimeMinut;
    }

    /**
     * 获取2个时间差，返回以分钟为单位的时间 注意参数的时间格式必须为:'yyyy-MM-dd HH:mm:ss' dataTime > systemTime
     * 为'正数'，否则为'负数'
     *
     * @param dataTime
     * @param systemTime
     * @return
     * @throws ParseException
     */
    public static int getSecondDiff(Date dataTime, Date systemTime)
            throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(systemTime);
        long systemL = calendar.getTimeInMillis();

        calendar.setTime(dataTime);
        long dataL = calendar.getTimeInMillis();

        int TimeMinut = (int) ((dataL - systemL) / (1000));
        return TimeMinut;
    }

    /**
     * 比较'systemTime'时间是否在'startTime'至'endTime' 2个时间之内<br>
     *
     * @param systemTime , startTime, endTime
     * @return boolean
     * @throws ParseException
     */
    public static boolean isWithin(String systemTime, String startTime,
                                   String endTime) {
        try {
            Date systemDate = parseToHHmm(systemTime);
            Date startDate = parseToHHmm(startTime);
            Date endDate = parseToHHmm(endTime);
            if (systemDate.after(startDate) && systemDate.before(endDate)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Description:'dbTime'时间 加/减 'day天' 后的时间<br>
     * dbTime格式:'yyyy-MM-dd' 、day格式:'+/- **' 天<br>
     * eg: 2009-10-01 12:00 加20天 等于 2009-10-21 12:00<br>
     *
     * @param dbTime
     * @param day
     * @return
     * @throws ParseException
     */
    public static String updateTime(String dbTime, String day)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat(
                "yyyy-MM-dd").parse(dbTime));
        c.add(Calendar.DAY_OF_WEEK, Integer.parseInt(day)); // dbTime增加day天
        return format.format(c.getTime());
    }

    public static String updateTime(Date dbTime, int day) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(dbTime);
        c.add(Calendar.DAY_OF_WEEK, day); // dbTime增加day天
        return new SimpleDateFormat(
                "yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 获得当前月的第一天
     */
    public static Date getCurrentMonthFirstDate() {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.DATE, 1);
        time.set(Calendar.HOUR, -12);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        return time.getTime();
    }

    /**
     * 获得指定月的第一天
     */
    public static Date getMonthFirstDate(String month) {
        Calendar time = Calendar.getInstance();
        int year = Integer.valueOf(month.substring(0, 4));
        int tempMonth = Integer.valueOf(month.substring(5, 7));
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, tempMonth - 1);
        time.set(Calendar.DATE, 1);
        time.set(Calendar.HOUR, -12);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        return time.getTime();
    }

    /**
     * 获得当前月的最后一天
     */
    public static Date getCurrentMonthLastDate() {
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MONTH, 1);
        time.set(Calendar.DATE, 1);
        time.set(Calendar.HOUR, 12);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        time.add(Calendar.DATE, -1);
        return time.getTime();
    }

    /**
     * 获得指定月的最后一天
     */
    public static Date getMonthLastDate(String month) {
        Calendar time = Calendar.getInstance();
        int year = Integer.valueOf(month.substring(0, 4));
        int tempMonth = Integer.valueOf(month.substring(5, 7));
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, tempMonth);
        time.set(Calendar.DATE, 1);
        time.set(Calendar.HOUR, 12);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        time.add(Calendar.MILLISECOND, -1);
        time.add(Calendar.DATE, -1);
        return time.getTime();
    }

    public static Date updateMonth(String dbTime, int month)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(dbTime));
        c.add(Calendar.MONTH, month); // dbTime增加day天
        return c.getTime();
    }

    public static int getDaysOfCurrentMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        cal.set(Calendar.MONTH,
                Integer.parseInt(date.substring(date.indexOf("-") + 1,
                        date.length())) - 1);// 7月
        int maxDate = cal.getActualMaximum(Calendar.DATE);// 当前月最大天数
        return maxDate;
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getStartDate(String year, String month, String day) {
        Calendar time = Calendar.getInstance();
        if (year != null && !"".equals(year)) {
            time.set(Calendar.YEAR, Integer.valueOf(year));
        }
        if (month == null || "".equals(month)) {
            month = "1";
        }
        time.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        if (day == null || "".equals(day)) {
            day = "1";
        }
        time.set(Calendar.DATE, Integer.valueOf(day));
        time.set(Calendar.HOUR, 0);
        time.set(Calendar.HOUR, -12);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        return time.getTime();
    }

    public static Date getEndDate(String year, String month, String day) {
        Calendar time = Calendar.getInstance();
        if (year != null && !"".equals(year)) {
            time.set(Calendar.YEAR, Integer.valueOf(year));
        }
        if (month == null || "".equals(month)) {
            month = "1";
        }
        time.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        if (day == null || "".equals(day)) {
            day = "1";
        }
        time.set(Calendar.DATE, Integer.valueOf(day));
        time.add(Calendar.DATE, 1);
        time.set(Calendar.HOUR, 0);
        time.set(Calendar.HOUR, -12);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        time.add(Calendar.MILLISECOND, -1);
        return time.getTime();
    }

    // 获取当期日期前一天
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        return CustomTimeUtils.formateToDay(calendar.getTime());
    }

    // 获取当期日期前30天
    public static String getLastThirtyDay(String date) {
        Date tempDate = null;
        try {
            tempDate = new SimpleDateFormat(
                    "yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tempDate);
        calendar.add(Calendar.DATE, -30);
        return CustomTimeUtils.formateToDay(calendar.getTime());
    }

    /**
     * 获取当期日期前一天 格式：yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getYesterday(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(CustomTimeUtils.parseToDay(date));
            calendar.add(Calendar.DATE, -1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return CustomTimeUtils.formateToDay(calendar.getTime());
    }

    /**
     * 获得当前月的第一天,例如 2010-11-01 00:00:00.000
     */
    public static Date getCurrentMonthFirstTime() {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.DATE, 1);
        time.set(Calendar.HOUR, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        return time.getTime();
    }

    /**
     * 得到UTC时间，类型为字符串，格式为"yyyy-MM-dd HH:mm"<br />
     * 如果获取失败，返回null
     *
     * @return
     */
    public static String getUTCTimeStr() {
        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        String year = Integer.toString(cal.get(Calendar.YEAR));
        String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(cal.get(Calendar.MINUTE));
        String second = Integer.toString(cal.get(Calendar.SECOND));
        year = year.substring(2);
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        if (minute.length() < 2) {
            minute = "0" + minute;
        }
        if (second.length() < 2) {
            second = "0" + second;
        }
        if (hour.length() < 2) {
            hour = "0" + hour;
        }

        UTCTimeBuffer.append(hour).append(minute).append(second).append(day)
                .append(month).append(year);
        try {
            System.out.println(UTCTimeBuffer.toString());
            System.out.println(Format.parse(UTCTimeBuffer.toString()));
            Format.parse(UTCTimeBuffer.toString());
            return UTCTimeBuffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将UTC时间转换为东八区时间
     *
     * @param UTCTime
     * @return
     */
    public static String getLocalTimeFromUTC(String UTCTime) {
        Date UTCDate = null;
        String localTimeStr = null;
        try {
            UTCDate = Format.parse(UTCTime);
            Format.setTimeZone(TimeZone.getTimeZone("GMT-8"));
            localTimeStr = Format.format(UTCDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return localTimeStr;
    }

    public static void main(String[] args) {
        String UTCTimeStr = getUTCTimeStr();
        System.out.println(UTCTimeStr);
    }

    /**
     * 获得当前月的最后一天,例如 2010-11-30 23:59:59.000
     */
    public static Date getCurrentMonthLastTime() {
        Calendar time = Calendar.getInstance();
        time.add(Calendar.MONTH, 1);
        time.set(Calendar.DATE, 1);
        time.set(Calendar.HOUR, 23);
        time.set(Calendar.MINUTE, 59);
        time.set(Calendar.SECOND, 59);
        time.set(Calendar.MILLISECOND, 0);
        time.add(Calendar.DATE, -1);
        return time.getTime();
    }


    public static String getNumberDate(Date date) {
        return new SimpleDateFormat(
                "yyyyMMddHHmmssSSS").format(date);
    }

    // public static void main(String[] args) {
    // System.out.println(TimeUtils.formateToMinute(TimeUtils.getCurrentMonthFirstTime())
    // );
    // System.out.println(TimeUtils.formateToMinute(TimeUtils.getCurrentMonthLastTime())
    // );
    // }
    public static Date formatDateTime(String dateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(dateTime);
    }

    public static String formatMill(Date dateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(dateTime);
    }

    public static String parseDateTime(String dateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(formatDateTime(dateTime));
    }

    public static Date parseGPSTime(String dateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        return sdf.parse(dateTime);
    }

    public static Date parseTime(String dateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.parse(dateTime);
    }

    public static Date parsemptTime(String dateTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");
        return sdf.parse(dateTime);
    }

    public static String parseString(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }

    /**
     * 取字符串中所有数字 18-07-09-11-20-20 ---> 180709112020
     *
     * @param str
     * @return
     */
    public static String getNum(String str) {
        return Pattern.compile("[^0-9]").matcher(str).replaceAll("").toString();
    }

    /**
     * Date类型转String类型
     *
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return secondFormat.format(date);
    }

    public static String date2String1(Date date) {
        SimpleDateFormat secondFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return secondFormat.format(date);
    }

    public static String date2String2(Date date) {
        SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd");
        return secondFormat.format(date);
    }

    public static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT2 = "yyyy-MM-dd";
    public static final String FORMAT3 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static Date string2Date(String date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException arg3) {
            arg3.printStackTrace();
            return null;
        } catch (NullPointerException arg4) {
            return null;
        }
    }

    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }

        return formatDate;
    }

    /**
     * 获得调整之后的时间
     *
     * @param date    需要调整的时间
     * @param seconds 调整的秒 +：向后调整 -：向前调整
     * @return
     */
    public static Date getAdjustDate(Date date, int seconds) {
        return new Date(date.getTime() + seconds * 1000);
    }

    /**
     * 目标时间是否在两个时间之间
     *
     * @param targetDate
     * @param startTime
     * @param endTime
     * @return
     */
    public static Boolean isBetween(Date targetDate, Date startTime, Date endTime) {
        return targetDate.before(startTime) && targetDate.after(endTime);
    }

    /**
     * 根据毫秒数获取对应的时间跨度
     *
     * @param ms
     * @return
     */
    public static String getTimeLength(Long ms) {
        StringBuilder stringBuffer = new StringBuilder();
        int days = Math.toIntExact(ms / (1000 * 60 * 60 * 24));
        stringBuffer.append(days == 0 ? "" : days + "天");
        int hours = Math.toIntExact((ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        stringBuffer.append(hours == 0 ? "" : hours + "时");
        int minutes = Math.toIntExact((ms % (1000 * 60 * 60)) / (1000 * 60));
        stringBuffer.append(minutes == 0 ? "" : minutes + "分");
        int seconds = Math.toIntExact((ms % (1000 * 60)) / 1000);
        stringBuffer.append(seconds == 0 ? "" : seconds + "秒");
        return stringBuffer.toString();
    }
}
