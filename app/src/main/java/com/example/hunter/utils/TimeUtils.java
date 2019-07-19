package com.example.hunter.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*Helper untuk membantu konversi tanggal dan waktu sesuai kebutuhan*/
public class TimeUtils {

    public static String formatTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(millis);
    }

//    public static String formatLastSeenAt(Context context, long millis) {
//        if (DateUtils.isToday(millis)) {
//            SimpleDateFormat time = new SimpleDateFormat("hh:mm a", Locale.getDefault());
//            return String.format(context.getString(R.string.status_last_seen_today_at), time.format(millis));
//        }
//
//        SimpleDateFormat time = new SimpleDateFormat("hh:mm a", Locale.getDefault());
//        return String.format(context.getString(R.string.status_last_seen_date_at), formatDate(millis), time.format(millis));
//    }

    public static String formatLastMessageAt(long millis) {
        if (DateUtils.isToday(millis)) {
            SimpleDateFormat time = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            return time.format(millis);
        }

        return formatDate(millis);
    }

    public static String formatPageMessageAt(long millis) {
        if (DateUtils.isToday(millis)) {
            return "Today";
        }
        return formatDate(millis);
    }

    public static String formatDuration(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return sdf.format(millis);
    }

    public static String formatDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(millis);
    }

    public static String getDateUntilSecond(){
        SimpleDateFormat srcDf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String lastDate = srcDf.format(new Date());

        return lastDate;
    }

    public static String getNowDate(){
        SimpleDateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDate = srcDf.format(new Date());

        return lastDate;
    }

    public static String getMonthYear(){
        SimpleDateFormat srcDf = new SimpleDateFormat("MM-yyyy");
        String lastDate = srcDf.format(new Date());

        return lastDate;
    }

    public static String deadlineDate(Date date){

        SimpleDateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormat = srcDf.format(date);
        return dateFormat;
    }

    public static String getDateNow(Date date){
        Locale id = new Locale("in", "ID");
        SimpleDateFormat srcDf = new SimpleDateFormat("dd-MMM-yyyy",id);
        String dateFormat = srcDf.format(date);
        return dateFormat;
    }

    public static String deadlineDateForTextview(Date date){

        SimpleDateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormat = srcDf.format(date);
        return dateFormat;
    }


    public static String getDay(String input){
        SimpleDateFormat inFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = inFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE",Locale.US);
        String goal = outFormat.format(date);
        return goal;
    }

    public static String getDateUlasan(String input){
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.US);
        String goal = outFormat.format(date);
        return goal;
    }

    public static String getDateCoupon(String input){
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("dd MMM yyyy",Locale.US);
        String goal = outFormat.format(date);
        return goal;
    }

    public static String getTimeFormat(String input){
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Locale id = new Locale("in", "ID");
        SimpleDateFormat outFormat = new SimpleDateFormat("dd MMMM yyyy",id);
        String goal = outFormat.format(date);
        return goal;
    }


    public static String getTime(String time){
        SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm:dd");
        Date date = null;
        try {
            date = inFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm",Locale.US);
        String goal = outFormat.format(date);
        return goal;
    }

    public static String theMonth(int month){
        String[] monthNames = {"Januari",
                "Februari",
                "Maret",
                "April",
                "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        return monthNames[month-1];
    }
}