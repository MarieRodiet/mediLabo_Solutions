package com.clientui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public static Date parseDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        try {
            // Parse the date string to create a Date object
            Date date = dateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();
        }
        return null;
    }
}
