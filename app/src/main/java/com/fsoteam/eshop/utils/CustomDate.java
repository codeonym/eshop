package com.fsoteam.eshop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDate {
   private long timestamp;

   public CustomDate() {
   }
   public CustomDate(long timestamp) {
       this.timestamp = timestamp;
   }
    public long getTimestamp() {
         return timestamp;
    }
    public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
    }
    public String getFormattedDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(timestamp));
    }
    public String getFormattedTime() {
        return new SimpleDateFormat("HH:mm").format(new Date(timestamp));
    }
    public String getFormattedDateTime() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(timestamp));
    }
    public String getFormattedDate(long timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(timestamp));
    }
    public String getFormattedTime(long timestamp) {
        return new SimpleDateFormat("HH:mm").format(new Date(timestamp));
    }
    public String getFormattedDateTime(long timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(timestamp));
    }
    public String getFormattedDateTime(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(timestamp));
    }
    public String getFormattedDateTime(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(timestamp));
    }
    public String getDay() {
        return new SimpleDateFormat("dd").format(new Date(timestamp));
    }
    public String getMonth() {
        return new SimpleDateFormat("MM").format(new Date(timestamp));
    }
    public String getYear() {
        return new SimpleDateFormat("yyyy").format(new Date(timestamp));
    }
    public String getHour() {
        return new SimpleDateFormat("HH").format(new Date(timestamp));
    }
    public String getMinute() {
        return new SimpleDateFormat("mm").format(new Date(timestamp));
    }
    public String getSecond() {
        return new SimpleDateFormat("ss").format(new Date(timestamp));
    }
    public String getDayName() {
        return new SimpleDateFormat("EEEE").format(new Date(timestamp));
    }
    public String getMonthName() {
        return new SimpleDateFormat("MMMM").format(new Date(timestamp));
    }
    public String getMonthShortName() {
        return new SimpleDateFormat("MMM").format(new Date(timestamp));
    }
    public String getAmPm() {
        return new SimpleDateFormat("a").format(new Date(timestamp));
    }
    public String getCountdownByDifference(long difference) {
        long seconds = difference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        hours = hours % 24;
        minutes = minutes % 60;
        seconds = seconds % 60;
        return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
    }
}