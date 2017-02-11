package com.zappos.consumer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ramya on 9/2/17.
 */
public class AppUtils {

    static SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-mm-dd");
    static SimpleDateFormat writeFormat = new SimpleDateFormat("MMM dd, yyyy");

    public static String formateDate(String dateFormat){
        Date date = null;
        try {
            date = readFormat.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return writeFormat.format(date);
    }
}
